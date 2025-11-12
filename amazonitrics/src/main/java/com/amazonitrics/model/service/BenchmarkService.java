package com.amazonitrics.model.service;

import com.amazonitrics.model.BenchmarkResultado;
import com.amazonitrics.model.Cenario;
import com.amazonitrics.model.Resultado;
import com.amazonitrics.model.algoritms.AlgoritmoOrdenacao;
import com.amazonitrics.model.external.DesmatamentoDado;
import com.amazonitrics.repository.benchmark.ResultadoRepository;
import com.amazonitrics.repository.external.DesmatamentoDadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BenchmarkService {

    private final GeradorDadosService geradorDadosService;
    private final ResultadoRepository resultadoRepository;
    private final List<AlgoritmoOrdenacao> algoritmos;
    private final DesmatamentoDadoRepository desmatamentoDadoRepository;

    @Autowired
    public BenchmarkService(GeradorDadosService geradorDadosService,
            ResultadoRepository resultadoRepository,
            List<AlgoritmoOrdenacao> algoritmos,
            DesmatamentoDadoRepository desmatamentoDadoRepository) {
        this.geradorDadosService = geradorDadosService;
        this.resultadoRepository = resultadoRepository;
        this.algoritmos = algoritmos;
        this.desmatamentoDadoRepository = desmatamentoDadoRepository;
    }

    public BenchmarkResultado executarBenchmark(Cenario cenario) {
        Comparable[] dadosOriginais;
        String tipoDados = cenario.getTipoDados();

        if (tipoDados.startsWith("DADOS_EXTERNOS")) {
            List<DesmatamentoDado> dadosExternos = desmatamentoDadoRepository.findAll();
            cenario.setTamanho(dadosExternos.size());

            switch (tipoDados) {
                case "DADOS_EXTERNOS_NUMPOL":
                    dadosOriginais = new Integer[dadosExternos.size()];
                    for (int i = 0; i < dadosExternos.size(); i++) {
                        dadosOriginais[i] = dadosExternos.get(i).getNumPol();
                    }
                    break;

                case "DADOS_EXTERNOS_AREA":
                    dadosOriginais = new Double[dadosExternos.size()];
                    for (int i = 0; i < dadosExternos.size(); i++) {
                        dadosOriginais[i] = dadosExternos.get(i).getArea();
                    }
                    break;

                case "DADOS_EXTERNOS_CLASSE":
                    dadosOriginais = new String[dadosExternos.size()];
                    for (int i = 0; i < dadosExternos.size(); i++) {
                        dadosOriginais[i] = dadosExternos.get(i).getClassName();
                    }
                    break;

                default:
                    dadosOriginais = new Integer[0];
            }
        } else {
            dadosOriginais = geradorDadosService.gerarDados(cenario.getTamanho(), tipoDados);
        }

        if (dadosOriginais == null || dadosOriginais.length == 0) {
            return new BenchmarkResultado(new ArrayList<>(), "Nenhum dado para processar.", "");
        }

        String amostraAntes = getAmostra(dadosOriginais);
        List<Resultado> resultadosDaExecucao = new ArrayList<>();
        Comparable[] dadosDepois = null;

        for (AlgoritmoOrdenacao algoritmo : algoritmos) {
            Comparable[] dadosParaOrdenar = dadosOriginais.clone();

            long startTime = System.nanoTime();
            algoritmo.ordenar(dadosParaOrdenar);
            long endTime = System.nanoTime();

            long duracaoNanos = endTime - startTime;
            double duracaoMs = (double) duracaoNanos / 1_000_000.0;

            Resultado res = new Resultado(
                    algoritmo.getNome(),
                    duracaoMs,
                    cenario.getTamanho(),
                    cenario.getTipoDados());

            resultadoRepository.save(res);
            resultadosDaExecucao.add(res);
            dadosDepois = dadosParaOrdenar;
        }

        String amostraDepois = getAmostra(dadosDepois);
        return new BenchmarkResultado(resultadosDaExecucao, amostraAntes, amostraDepois);
    }

    private String getAmostra(Comparable[] dados) {
        if (dados == null)
            return "";
        int tamanhoAmostra = Math.min(dados.length, 100);

        Comparable[] amostra = (Comparable[]) Array.newInstance(
                dados.getClass().getComponentType(),
                tamanhoAmostra);
        System.arraycopy(dados, 0, amostra, 0, tamanhoAmostra);

        String amostraStr = Arrays.toString(amostra);
        if (dados.length > 100) {
            amostraStr = amostraStr.substring(0, amostraStr.length() - 1) + ", ...]";
        }
        return amostraStr;
    }
}