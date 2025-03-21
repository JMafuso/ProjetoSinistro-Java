package br.com.fiap.ja.odontoprev.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalDTO {

    private UUID id_profissional;

    @NotBlank(message = "A especialidade é obrigatório")
    private String especialista;

    @NotBlank(message = "O nome do profissional é obrigatório")
    private String nm_completo;
}
