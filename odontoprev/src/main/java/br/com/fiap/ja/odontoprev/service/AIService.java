package br.com.fiap.ja.odontoprev.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String sugerirDiagnostico(String sintoma, String doenca, Integer gravidade) {
        String prompt = String.format(
                "Sou um paciente com o sintoma '%s', diagnosticado com a doença '%s' e gravidade nível %d (de 1 a 5, onde 1 é leve e 5 é emergência). " +
                        "Com base nessas informações, sugira um diagnóstico ou recomendação médica em português. Responda de forma concisa e profissional.",
                sintoma, doenca, gravidade
        );
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
