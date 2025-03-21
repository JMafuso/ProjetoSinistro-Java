package br.com.fiap.ja.odontoprev;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class GuiaSaude {
    private final ChatClient chat;

    private final List<Paciente> pacientes = new ArrayList<>();

    public GuiaSaude(ChatClient.Builder builder, VectorStore vectors, ChatMemory memory) {
        this.chat = builder
                .defaultSystem("""
                    Por favor, aja como um profissional de saúde experiente que está auxiliando pacientes.
                    
                    Antes de qualquer interação, você deve:
                    1. Identificar se é um novo paciente ou um retorno
                    2. Se for novo paciente, solicitar informações básicas de identificação
                    3. Se for retorno, verificar histórico de consultas anteriores
                    
                    Ao receber uma consulta médica:
                    - Mantenha um tom profissional e acolhedor
                    - Peça esclarecimentos quando necessário
                    - Forneça orientações claras e precisas
                    - Considere sempre o histórico do paciente
                    - Use o sistema de previsão para planejar acompanhamentos futuros
                    - Registre todas as recomendações fornecidas
                    
                    Para cada interação:
                    - Verifique disponibilidade de horários para consultas
                    - Considere o histórico médico do paciente
                    - Mantenha registro das recomendações e tratamentos
                    - Seja claro sobre prazos de resposta e acompanhamento
                    
                    Hoje é {current_date}.
                    """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(memory),
                        new QuestionAnswerAdvisor(vectors))
                .defaultFunctions("verificarHorarios", "registrarConsulta", "obterHistoricoPaciente")
                .build();
    }

    public String chat(String pacienteId, String mensagem) {
        Response resposta = this.chat.prompt()
                .system(s -> s.param("current_date", LocalDateTime.now().toString()))
                .user(mensagem)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, pacienteId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call().entity(Response.class);

        if (resposta.consultas() != null) {
            this.pacientes.addAll(resposta.consultas());
        }
        return resposta.resposta;
    }

    @GetMapping("/consultas")
    public List<Paciente> historicoConsultas() {
        return this.pacientes;
    }

    private record Response(String resposta, List<Paciente> consultas) {}

    private record Paciente(
            String nomePaciente,
            String cpf,
            String especialidade,
            LocalDateTime dataConsulta,
            String sintomas,
            List<String> recomendacoes,
            Double valorConsulta
    ) {}
}
