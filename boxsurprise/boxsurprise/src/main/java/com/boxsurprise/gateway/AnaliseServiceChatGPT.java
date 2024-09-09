package com.boxsurprise.gateway;

import com.boxsurprise.dtos.request.ChatGPTRequestDto;
import com.boxsurprise.dtos.response.ChatGPTResponseDto;
import com.boxsurprise.dtos.response.MensagemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AnaliseServiceChatGPT {
    private final WebClient webClient;

    public AnaliseServiceChatGPT(WebClient.Builder builder, @Value("${openai.api.key}") String apiKey) {
        this.webClient = builder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", String.format("Bearer %s", apiKey))
                .build();
    }

    public Mono<ChatGPTResponseDto> criarAnalise(String perfil) {
        ChatGPTRequestDto request = criarAnaliseRequest(perfil);
        return webClient.post().bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGPTResponseDto.class);
    }

    private ChatGPTRequestDto criarAnaliseRequest(String perfil) {
        String question = "Quais são os melhores presentes para alguém com esse perfil ou tema: " + perfil;

        MensagemDto userMessage = new MensagemDto("user", question);

        return ChatGPTRequestDto.builder()
                .model("gpt-4o-mini")
                .messages(List.of(userMessage))
                .temperature(1.0)
                .maxTokens(2000)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .build();
    }

}