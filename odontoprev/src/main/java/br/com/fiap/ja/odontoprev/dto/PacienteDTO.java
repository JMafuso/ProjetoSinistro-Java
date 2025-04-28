package br.com.fiap.ja.odontoprev.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private UUID uuid;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O sintoma é obrigatório")
    private String sintoma;

    @NotBlank(message = "A doença é obrigatória")
    private String doenca;

    @Min(value = 1, message = "A gravidade deve ser entre 1 e 5")
    @Max(value = 5, message = "A gravidade deve ser entre 1 e 5")
    private Integer gravidade;
}