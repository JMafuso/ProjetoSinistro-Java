package br.com.fiap.ja.odontoprev.controller;

import br.com.fiap.ja.odontoprev.dto.ProfissionalDTO;
import br.com.fiap.ja.odontoprev.service.ProfissionalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/profissional")
@AllArgsConstructor
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    @GetMapping
    public String listar(Model model) {
        List<ProfissionalDTO> lista = profissionalService.findAll();
        model.addAttribute("profissional", lista);
        return "profissional/lista";
    }

    @GetMapping("/novo")
    public String novoProfissional(Model model) {
        model.addAttribute("profissional", new ProfissionalDTO());
        return "profissional/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("profissional") ProfissionalDTO profissional,
                         BindingResult bindingResults, Model model) {
        if (bindingResults.hasErrors()) {
            return "profissional/formulario";
        }
        profissionalService.salvar(profissional);
        return "redirect:/profissional";
    }

    @GetMapping("/editar/{uuid}")
    public String editar(@PathVariable UUID uuid, Model model) {
        ProfissionalDTO profissional = profissionalService.findById(uuid);
        model.addAttribute("profissional", profissional);
        return "profissional/formulario";
    }

    @GetMapping("/deletar/{uuid}")
    public String deletar(@PathVariable UUID uuid) {
        profissionalService.deletarPorId(uuid);
        return "redirect:/profissional";
    }
}
