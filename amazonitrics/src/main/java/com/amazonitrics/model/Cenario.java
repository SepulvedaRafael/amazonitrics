package com.amazonitrics.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Cenario {

    @Min(value = 10, message = "O tamanho deve ser no mínimo 10.")
    private int tamanho = 4000;

    @NotEmpty(message = "Por favor, selecione um tipo de dados.")
    private String tipoDados = "ALEATORIO";

    public Cenario() {
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTipoDados(String tipoDados) {
        this.tipoDados = tipoDados;
    }

    public String getTipoDados() {
        return tipoDados;
    }
}