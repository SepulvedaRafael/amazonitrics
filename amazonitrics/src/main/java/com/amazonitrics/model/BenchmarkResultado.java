package com.amazonitrics.model;

import java.util.List;

public class BenchmarkResultado {
    
    private final List<Resultado> resultados;
    private final String amostraAntes;
    private final String amostraDepois;

    public BenchmarkResultado(List<Resultado> resultados, String amostraAntes, String amostraDepois) {
        this.resultados = resultados;
        this.amostraAntes = amostraAntes;
        this.amostraDepois = amostraDepois;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public String getAmostraAntes() {
        return amostraAntes;
    }

    public String getAmostraDepois() {
        return amostraDepois; 
    }
}
