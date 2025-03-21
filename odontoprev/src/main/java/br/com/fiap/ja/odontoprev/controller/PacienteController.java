package br.com.fiap.ja.odontoprev.controller;

import br.com.fiap.ja.odontoprev.dto.PacienteDTO;
import br.com.fiap.ja.odontoprev.service.AIService;
import br.com.fiap.ja.odontoprev.service.PacienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/pacientes")
@AllArgsConstructor
public class PacienteController {
    private final PacienteService pacienteService;
    private final AIService aiService;

    @GetMapping
    public String listar(Model model) {
        List<PacienteDTO> lista = pacienteService.findAll();
        model.addAttribute("pacientes", lista);
        return "pacientes/lista";
    }

    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("paciente", new PacienteDTO());
        return "pacientes/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("paciente") PacienteDTO paciente,
                         BindingResult bindingResults, Model model) {
        if (bindingResults.hasErrors()) {
            return "pacientes/formulario";
        }
        pacienteService.salvar(paciente);
        String diagnostico = aiService.sugerirDiagnostico(
                paciente.getSintoma(),
                paciente.getDoenca(),
                paciente.getGravidade()
        );
        model.addAttribute("diagnostico", diagnostico);
        return "pacientes/resultado";
    }

    @GetMapping("/editar/{uuid}")
    public String editar(@PathVariable UUID uuid, Model model) {
        PacienteDTO paciente = pacienteService.findById(uuid);
        model.addAttribute("paciente", paciente);
        return "pacientes/formulario";
    }

    @GetMapping("/deletar/{uuid}")
    public String deletar(@PathVariable UUID uuid) {
        pacienteService.deletarPorId(uuid);
        return "redirect:/pacientes";
    }
}