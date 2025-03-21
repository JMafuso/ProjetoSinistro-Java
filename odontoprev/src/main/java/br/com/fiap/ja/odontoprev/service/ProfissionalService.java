package br.com.fiap.ja.odontoprev.service;

import br.com.fiap.ja.odontoprev.dto.ProfissionalDTO;
import br.com.fiap.ja.odontoprev.entity.Profissional;
import br.com.fiap.ja.odontoprev.repository.ProfissionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfissionalService {
    private final ProfissionalRepository repository;

    public ProfissionalDTO salvar(ProfissionalDTO profissionalDTO) {
        Profissional profissional = toEntity(profissionalDTO);
        if (profissionalDTO.getId_profissional() == null) {
            profissional = repository.save(profissional);
        } else {
            Optional<Profissional> existente = repository.findById(profissionalDTO.getId_profissional());
            if (existente.isPresent()) {
                existente.get().setEspecialista(profissional.getEspecialista());
                existente.get().setNm_completo(profissional.getNm_completo());
                profissional = repository.save(existente.get());
            }
        }
        return toDto(profissional);
    }

    public List<ProfissionalDTO> findAll() {
        List<Profissional> lista = repository.findAll();
        return lista.stream().map(this::toDto).toList();
    }

    public void deletarPorId(UUID id) {
        repository.deleteById(id);
    }

    public ProfissionalDTO findById(UUID id) {
        Optional<Profissional> optional = repository.findById(id);
        return optional.map(this::toDto).orElseThrow(() ->
                new RuntimeException("Profissional não encontrado"));
    }

    private Profissional toEntity(ProfissionalDTO dto) {
        Profissional entity = new Profissional();
        entity.setId_profissional(dto.getId_profissional());
        entity.setEspecialista(dto.getEspecialista());
        entity.setNm_completo(dto.getNm_completo());

        return entity;
    }

    private ProfissionalDTO toDto(Profissional entity) {
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId_profissional(entity.getId_profissional());
        dto.setEspecialista(entity.getEspecialista());
        dto.setNm_completo(entity.getNm_completo());

        return dto;
    }
}
