package com.amazonitrics.model.algoritms;

public interface AlgoritmoOrdenacao {
    <T extends Comparable<T>> void ordenar(T[] dados);
    String getNome();
}