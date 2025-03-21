package br.com.fiap.ja.odontoprev.service;

import br.com.fiap.ja.odontoprev.config.RabbitMQConfig;
import br.com.fiap.ja.odontoprev.dto.PacienteDTO;
import br.com.fiap.ja.odontoprev.entity.Paciente;
import br.com.fiap.ja.odontoprev.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PacienteService {
    private final PacienteRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public PacienteDTO salvar(PacienteDTO pacienteDTO) {
        Paciente paciente = toEntity(pacienteDTO);
        if (pacienteDTO.getUuid() == null) {
            paciente = repository.save(paciente);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pacienteDTO);
        } else {
            Optional<Paciente> existente = repository.findById(pacienteDTO.getUuid());
            if (existente.isPresent()) {
                existente.get().setNome(paciente.getNome());
                existente.get().setSintoma(paciente.getSintoma());
                existente.get().setDoenca(paciente.getDoenca());
                existente.get().setGravidade(paciente.getGravidade());
                paciente = repository.save(existente.get());
            }
        }
        return toDto(paciente);
    }

    public List<PacienteDTO> findAll() {
        List<Paciente> lista = repository.findAll();
        return lista.stream().map(this::toDto).toList();
    }

    public void deletarPorId(UUID id) {
        repository.deleteById(id);
    }

    public PacienteDTO findById(UUID id) {
        Optional<Paciente> optional = repository.findById(id);
        return optional.map(this::toDto).orElseThrow(() ->
                new RuntimeException("Registro não encontrado"));
    }

    private Paciente toEntity(PacienteDTO dto) {
        Paciente entity = new Paciente();
        entity.setUuid(dto.getUuid());
        entity.setNome(dto.getNome());
        entity.setSintoma(dto.getSintoma());
        entity.setDoenca(dto.getDoenca());
        entity.setGravidade(dto.getGravidade());
        return entity;
    }

    private PacienteDTO toDto(Paciente entity) {
        PacienteDTO dto = new PacienteDTO();
        dto.setUuid(entity.getUuid());
        dto.setNome(entity.getNome());
        dto.setSintoma(entity.getSintoma());
        dto.setDoenca(entity.getDoenca());
        dto.setGravidade(entity.getGravidade());
        return dto;
    }
}