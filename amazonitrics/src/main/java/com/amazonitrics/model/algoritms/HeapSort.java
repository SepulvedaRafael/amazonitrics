package com.amazonitrics.model.algoritms;

import org.springframework.stereotype.Component;

@Component
public class HeapSort implements AlgoritmoOrdenacao {

    @Override
    public String getNome() {
        return "HeapSort";
    }

    @Override
    public <T extends Comparable<T>> void ordenar(T[] dados) {
        int n = dados.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(dados, n, i);

        for (int i = n - 1; i > 0; i--) {
            T temp = dados[0];
            dados[0] = dados[i];
            dados[i] = temp;
            heapify(dados, i, 0);
        }
    }

    private <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l].compareTo(arr[largest]) > 0)
            largest = l;

        if (r < n && arr[r].compareTo(arr[largest]) > 0)
            largest = r;

        if (largest != i) {
            T swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }
}