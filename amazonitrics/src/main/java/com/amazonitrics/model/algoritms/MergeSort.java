package com.amazonitrics.model.algoritms;

import org.springframework.stereotype.Component;
import java.lang.reflect.Array;

@Component
public class MergeSort implements AlgoritmoOrdenacao {

    @Override
    public String getNome() {
        return "MergeSort";
    }

    @Override
    public <T extends Comparable<T>> void ordenar(T[] dados) {
        mergeSort(dados, 0, dados.length - 1);
    }

    private <T extends Comparable<T>> void mergeSort(T[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private <T extends Comparable<T>> void merge(T[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        T[] L = (T[]) Array.newInstance(arr.getClass().getComponentType(), n1);
        T[] R = (T[]) Array.newInstance(arr.getClass().getComponentType(), n2);

        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}