package com.amazonitrics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultado")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAlgoritmo;
    private double tempoMs;
    private int tamanhoDados;
    private String tipoDados;
    private LocalDateTime dataExecucao;

    public Resultado() {
    }

    public Resultado(String nomeAlgoritmo, double tempoMs, int tamanhoDados, String tipoDados) {
        this.nomeAlgoritmo = nomeAlgoritmo;
        this.tempoMs = tempoMs;
        this.tamanhoDados = tamanhoDados;
        this.tipoDados = tipoDados;
        this.dataExecucao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAlgoritmo() {
        return nomeAlgoritmo;
    }

    public void setNomeAlgoritmo(String nomeAlgoritmo) {
        this.nomeAlgoritmo = nomeAlgoritmo;
    }

    public double getTempoMs() {
        return tempoMs;
    }

    public void setTempoMs(long tempoMs) {
        this.tempoMs = tempoMs;
    }

    public int getTamanhoDados() {
        return tamanhoDados;
    }

    public void setTamanhoDados(int tamanhoDados) {
        this.tamanhoDados = tamanhoDados;
    }

    public String getTipoDados() {
        return tipoDados;
    }

    public void setTipoDados(String tipoDados) {
        this.tipoDados = tipoDados;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }
}
