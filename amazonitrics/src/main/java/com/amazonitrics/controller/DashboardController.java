package com.amazonitrics.controller;

import com.amazonitrics.model.BenchmarkResultado;
import com.amazonitrics.model.Cenario;
import com.amazonitrics.model.Resultado;
import com.amazonitrics.model.external.DesmatamentoDado;
import com.amazonitrics.model.service.BenchmarkService;
import com.amazonitrics.repository.benchmark.ResultadoRepository;
import com.amazonitrics.repository.external.DesmatamentoDadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final BenchmarkService benchmarkService;
    private final ResultadoRepository resultadoRepository;
    private final DesmatamentoDadoRepository desmatamentoDadoRepository;

    @Autowired
    public DashboardController(BenchmarkService benchmarkService,
                               ResultadoRepository resultadoRepository,
                               DesmatamentoDadoRepository desmatamentoDadoRepository) {
        this.benchmarkService = benchmarkService;
        this.resultadoRepository = resultadoRepository;
        this.desmatamentoDadoRepository = desmatamentoDadoRepository;
    }

    @GetMapping("/")
    public String getDashboard(Model model) {
        model.addAttribute("cenario", new Cenario());
        adicionarHistoricoAoModelo(model);

        try {
            List<DesmatamentoDado> dadosExternos = desmatamentoDadoRepository.findAll();
            model.addAttribute("dadosDesmatamento", dadosExternos);

        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados externo: " + e.getMessage());
            model.addAttribute("erroExterno", "Falha ao carregar dados externos.");
        }

        return "dashboard";
    }

    @PostMapping("/analisar")
    public String postAnalise(@Valid @ModelAttribute Cenario cenario,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            adicionarHistoricoAoModelo(model);
            return "dashboard";
        }

        BenchmarkResultado benchmark = benchmarkService.executarBenchmark(cenario);

        model.addAttribute("benchmarkResultado", benchmark);
        model.addAttribute("cenario", cenario);

        List<DesmatamentoDado> dadosExternos = desmatamentoDadoRepository.findAll();
        model.addAttribute("dadosDesmatamento", dadosExternos);
        return "dashboard";
    }

    private void adicionarHistoricoAoModelo(Model model) {
        List<Resultado> historico = resultadoRepository.findTop10ByOrderByDataExecucaoDesc();
        model.addAttribute("historico", historico);
    }
}