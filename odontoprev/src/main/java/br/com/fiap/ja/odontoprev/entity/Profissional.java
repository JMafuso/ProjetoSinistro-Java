package br.com.fiap.ja.odontoprev.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
@Table(name = "tb_profissional")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_profissional;

    @NotBlank(message = "A especialidade é obrigatório")
    private String especialista;

    @NotBlank(message = "O nome do profissional é obrigatório")
    private String nm_completo;
}
