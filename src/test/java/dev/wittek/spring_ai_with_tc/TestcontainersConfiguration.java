package dev.wittek.spring_ai_with_tc;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.DockerModelRunnerContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    DockerModelRunnerContainer dmr() {
        return new DockerModelRunnerContainer("alpine/socat:1.8.0.1");
//                .withModel("ai/gemma3:4B-Q4_0"); // In next tc-java release 1.21.1
    }

    @Bean
    DynamicPropertyRegistrar properties(DockerModelRunnerContainer dmr) {
        return (registrar) -> {
            registrar.add("spring.ai.openai.base-url", dmr::getOpenAIEndpoint);
            registrar.add("spring.ai.openai.api-key", () -> "test-api-key");
            registrar.add("spring.ai.openai.chat.options.model", () -> "ai/qwen2.5:7B-F16");
        };
    }

}
