package dev.wittek.spring_ai_with_tc;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/chat-stream")
    public Flux<String> chatStream(@RequestParam String message) {
        return this.chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/f1")
    public String f1Results(@RequestParam String message) {
        return this.chatClient.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .system("You are a Formula 1 expert. " +
                        "You use the F1Tool to get results from the ergast API." +
                        "Use only a single tool call.")
                .user(message)
                .tools(new F1Tool())
                .call()
                .content();
    }


}
