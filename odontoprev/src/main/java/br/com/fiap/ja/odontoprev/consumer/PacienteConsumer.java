package br.com.fiap.ja.odontoprev.consumer;

import br.com.fiap.ja.odontoprev.config.RabbitMQConfig;
import br.com.fiap.ja.odontoprev.dto.PacienteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PacienteConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PacienteConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(PacienteDTO pacienteDTO) {
        logger.info("Paciente cadastrado recebido: {}", pacienteDTO);

    }
}
