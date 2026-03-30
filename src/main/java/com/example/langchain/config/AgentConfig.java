package com.example.langchain.config;

import com.example.langchain.agent.AssistantAgent;
import com.example.langchain.tool.CalculatorTool;
import com.example.langchain.tool.WeatherTool;
import com.example.langchain.tool.WebSearchTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that wires together the AI agent with tools and memory.
 * Builds the AssistantAgent proxy using LangChain4j's AiServices builder.
 */
@Slf4j
@Configuration
public class AgentConfig {

    /**
     * Provides per-session chat memory using a sliding window of the last 20
     * messages.
     * Each unique memoryId (sessionId) gets its own isolated memory.
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        log.info("🧠 Initializing ChatMemoryProvider with 20-message window");
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .build();
    }

    /**
     * Builds the AssistantAgent using AiServices, wiring in:
     * - The ChatLanguageModel (auto-configured by
     * langchain4j-ollama-spring-boot-starter)
     * - All three tools (Weather, Calculator, WebSearch)
     * - Chat memory provider for multi-turn conversations
     */
    @Bean
    public AssistantAgent assistantAgent(
            ChatLanguageModel chatLanguageModel,
            ChatMemoryProvider chatMemoryProvider,
            WeatherTool weatherTool,
            CalculatorTool calculatorTool,
            WebSearchTool webSearchTool) {

        log.info("🤖 Building AssistantAgent with tools: WeatherTool, CalculatorTool, WebSearchTool");
        log.info("📦 Using ChatLanguageModel: {}", chatLanguageModel.getClass().getSimpleName());

        return AiServices.builder(AssistantAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(weatherTool, calculatorTool, webSearchTool)
                .build();
    }
}
