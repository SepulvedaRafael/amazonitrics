package com.amazonitrics.model.algoritms;

import org.springframework.stereotype.Component;

@Component
public class QuickSort implements AlgoritmoOrdenacao {

    @Override
    public String getNome() {
        return "QuickSort";
    }

    @Override
    public <T extends Comparable<T>> void ordenar(T[] dados) {
        quickSort(dados, 0, dados.length - 1);
    }

    private <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        T temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}