package br.com.fiap.ja.odontoprev.repository;

import br.com.fiap.ja.odontoprev.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
}
