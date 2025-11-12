package com.amazonitrics.model.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Service
public class GeradorDadosService {

    private final Random random = new Random();

    public Integer[] gerarDados(int tamanho, String tipoDados) {
        return switch (tipoDados) {
            case "ORDENADO" -> gerarOrdenado(tamanho);
            case "INVERTIDO" -> gerarInvertido(tamanho);
            case "SEMI_ORDENADO" -> gerarSemiOrdenado(tamanho);
            default -> gerarAleatorio(tamanho);
        };
    }

    private Integer[] gerarAleatorio(int tamanho) {
        Integer[] dados = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            dados[i] = random.nextInt(tamanho * 10);
        }
        return dados;
    }

    private Integer[] gerarOrdenado(int tamanho) {
        Integer[] dados = gerarAleatorio(tamanho);
        Arrays.sort(dados);
        return dados;
    }

    private Integer[] gerarInvertido(int tamanho) {
        Integer[] dados = gerarOrdenado(tamanho);
        Arrays.sort(dados, Collections.reverseOrder());
        return dados;
    }

    private Integer[] gerarSemiOrdenado(int tamanho) {
        Integer[] dados = gerarOrdenado(tamanho);
        int trocas = tamanho / 10;
        for (int i = 0; i < trocas; i++) {
            int idx1 = random.nextInt(tamanho);
            int idx2 = random.nextInt(tamanho);
            Integer temp = dados[idx1];
            dados[idx1] = dados[idx2];
            dados[idx2] = temp;
        }
        return dados;
    }
}