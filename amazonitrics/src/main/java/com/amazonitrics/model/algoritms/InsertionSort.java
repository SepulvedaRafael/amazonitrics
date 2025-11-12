package com.amazonitrics.model.algoritms;

import org.springframework.stereotype.Component;

@Component
public class InsertionSort implements AlgoritmoOrdenacao {

    @Override
    public String getNome() {
        return "InsertionSort";
    }

    @Override
    public <T extends Comparable<T>> void ordenar(T[] dados) {
        int n = dados.length;
        for (int i = 1; i < n; ++i) {
            T key = dados[i];
            int j = i - 1;

            while (j >= 0 && dados[j].compareTo(key) > 0) {
                dados[j + 1] = dados[j];
                j = j - 1;
            }
            dados[j + 1] = key;
        }
    }
}