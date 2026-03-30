package com.example.langchain.controller;

import com.example.langchain.agent.AssistantAgent;
import com.example.langchain.dto.ChatRequest;
import com.example.langchain.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Chat Controller — handles the REST API for chat interactions
 * and serves the frontend chat UI page.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final AssistantAgent assistantAgent;

    /**
     * Serves the chat UI page.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Handles chat messages from the frontend.
     * Forwards the user message to the AI agent and returns the response.
     */
    @PostMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();
        }

        log.info("💬 Chat request — session: {}, message: {}", sessionId, request.getMessage());

        try {
            String agentResponse = assistantAgent.chat(sessionId, request.getMessage());
            log.info("🤖 Agent response for session {}: {}", sessionId, agentResponse);
            return ResponseEntity.ok(new ChatResponse(agentResponse, sessionId));
        } catch (Exception e) {
            log.error("❌ Error processing chat request", e);
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Sorry, I encountered an error: " + e.getMessage(), sessionId));
        }
    }
}
