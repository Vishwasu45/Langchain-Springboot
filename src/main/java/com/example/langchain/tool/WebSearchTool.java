package com.example.langchain.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Web Search Tool — provides simulated web search results.
 * The AI agent can invoke this when users ask to search for information.
 */
@Slf4j
@Component
public class WebSearchTool {

    private static final List<Map<String, String>> KNOWLEDGE_BASE = List.of(
            Map.of(
                    "topic", "java",
                    "title", "Java 21 New Features — Virtual Threads, Pattern Matching & More",
                    "url", "https://dev.java/learn/new-features/",
                    "snippet",
                    "Java 21 introduces virtual threads (Project Loom), pattern matching for switch, record patterns, sequenced collections, and string templates (preview). These features significantly improve concurrency, code readability, and developer productivity."),
            Map.of(
                    "topic", "spring",
                    "title", "Spring Boot 3.4 Release — What's New",
                    "url", "https://spring.io/blog/2024/spring-boot-3-4",
                    "snippet",
                    "Spring Boot 3.4 brings improved observability, enhanced Docker Compose support, SSL bundle reloading, structured logging, and better GraalVM native image support. It also includes updated dependency versions and performance improvements."),
            Map.of(
                    "topic", "ai",
                    "title", "LangChain4j — Building AI-Powered Java Applications",
                    "url", "https://docs.langchain4j.dev/",
                    "snippet",
                    "LangChain4j provides a Java-native framework for building applications powered by LLMs. It supports tool calling, RAG, chat memory, and integrates with OpenAI, Ollama, Gemini, and many other providers."),
            Map.of(
                    "topic", "kubernetes",
                    "title", "Kubernetes Best Practices for Spring Boot Apps",
                    "url", "https://kubernetes.io/docs/tutorials/",
                    "snippet",
                    "Deploy Spring Boot applications on Kubernetes with health checks, ConfigMaps, Secrets management, horizontal pod autoscaling, and resource limits. Use Helm charts for repeatable deployments."),
            Map.of(
                    "topic", "database",
                    "title", "PostgreSQL vs MongoDB — Choosing the Right Database",
                    "url", "https://db-comparison.dev/",
                    "snippet",
                    "PostgreSQL excels in relational data with ACID compliance and complex queries. MongoDB shines with flexible schemas and horizontal scaling. Choose based on data structure, query patterns, and scaling needs."),
            Map.of(
                    "topic", "security",
                    "title", "Spring Security 6 — Modern Authentication & Authorization",
                    "url", "https://spring.io/projects/spring-security",
                    "snippet",
                    "Spring Security 6 features a lambda DSL for configuration, OAuth 2.0/OIDC support, method-level security, and improved password encoding. It integrates seamlessly with Spring Boot 3.x."),
            Map.of(
                    "topic", "general",
                    "title", "Top Programming Trends in 2025",
                    "url", "https://techtrends.dev/2025",
                    "snippet",
                    "The top programming trends include AI-augmented development, WebAssembly adoption, edge computing, serverless architectures, and the rise of Rust for systems programming alongside established languages like Java and Python."));

    @Tool("Search the web for information on a given query. Returns relevant search results with titles, URLs, and snippets.")
    public String searchWeb(@P("The search query string") String query) {
        log.info("🔍 WebSearchTool invoked with query: {}", query);

        String queryLower = query.toLowerCase();
        StringBuilder results = new StringBuilder();
        results.append(String.format("Web Search Results for: \"%s\"\n", query));
        results.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

        int count = 0;
        for (Map<String, String> entry : KNOWLEDGE_BASE) {
            if (queryLower.contains(entry.get("topic"))
                    || entry.get("title").toLowerCase().contains(queryLower)
                    || entry.get("snippet").toLowerCase().contains(queryLower)
                    || containsAnyWord(queryLower, entry.get("snippet").toLowerCase())) {
                count++;
                results.append(String.format("📄 Result %d:\n", count));
                results.append(String.format("   Title: %s\n", entry.get("title")));
                results.append(String.format("   URL: %s\n", entry.get("url")));
                results.append(String.format("   %s\n\n", entry.get("snippet")));
            }
            if (count >= 3)
                break;
        }

        // Always return at least one general result
        if (count == 0) {
            Random rng = new Random(query.hashCode());
            int idx = rng.nextInt(KNOWLEDGE_BASE.size());
            Map<String, String> fallback = KNOWLEDGE_BASE.get(idx);
            results.append("📄 Result 1:\n");
            results.append(String.format("   Title: %s\n", fallback.get("title")));
            results.append(String.format("   URL: %s\n", fallback.get("url")));
            results.append(String.format("   %s\n\n", fallback.get("snippet")));
            count = 1;
        }

        results.append(String.format("Found %d result(s) — Search performed at %s\n",
                count,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        String resultStr = results.toString();
        log.info("WebSearchTool returned {} results", count);
        return resultStr;
    }

    private boolean containsAnyWord(String query, String text) {
        String[] words = query.split("\\s+");
        int matchCount = 0;
        for (String word : words) {
            if (word.length() > 3 && text.contains(word)) {
                matchCount++;
            }
        }
        return matchCount >= 2;
    }
}
