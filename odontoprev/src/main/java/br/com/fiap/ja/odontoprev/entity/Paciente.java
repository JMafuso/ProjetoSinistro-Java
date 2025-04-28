package br.com.fiap.ja.odontoprev.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O sintoma é obrigatório")
    private String sintoma;

    @NotBlank(message = "A doença é obrigatória")
    private String doenca;

    @NotNull(message = "A gravidade é obrigatória")
    @Min(value = 1, message = "A gravidade deve ser entre 1 e 5")
    @Max(value = 5, message = "A gravidade deve ser entre 1 e 5")
    private Integer gravidade;
}