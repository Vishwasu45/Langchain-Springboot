package com.example.langchain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "langchain4j.ollama.chat-model.base-url=http://localhost:11434",
        "langchain4j.ollama.chat-model.model-name=llama3.1"
})
class LangchainSpringbootApplicationTests {

    @Test
    void contextLoads() {
    }
}
