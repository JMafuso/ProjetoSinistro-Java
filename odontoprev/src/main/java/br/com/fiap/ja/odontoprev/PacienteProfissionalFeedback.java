package br.com.fiap.ja.odontoprev;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.ai.document.Document;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class PacienteProfissionalFeedback {
    private final VectorStore vectors;

    public PacienteProfissionalFeedback(VectorStore vectors) {
        this.vectors = vectors;
    }

    @Bean
    @Description("Salvar Feedback Paciente-Profissional")
    Consumer<Feedback> salvarFeedback() {
        Consumer<Feedback> feedbackConsumer = feedback ->
                this.vectors.write(List.of(new Document(
                        feedback.comentario(),
                        Map.of(
                                "paciente_id", feedback.pacienteId(),
                                "profissional_id", feedback.profissionalId(),
                                "tipo_feedback", feedback.tipoFeedback(),
                                "data", feedback.dataCriacao()
                        )
                )));
        return feedbackConsumer;
    }

    private record Feedback(
            String comentario,
            String pacienteId,
            String profissionalId,
            TipoFeedback tipoFeedback,
            LocalDateTime dataCriacao
    ) {}

    private enum TipoFeedback {
        PACIENTE_PARA_PROFISSIONAL,
        PROFISSIONAL_PARA_PACIENTE
    }


}
