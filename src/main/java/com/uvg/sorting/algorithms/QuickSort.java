package com.uvg.sorting.algorithms;

/*
 * Universidad del Valle de Guatemala
 * Autora: Tiffany Salazar Suarez
 * Carnet: 24630
 * Fecha de inicio: 31/01/2025
 * Fecha de finalización: 5/02/2025
 * Descripción: La clase QuickSort se encarga de ordenar listas de números
*/

public class QuickSort{

    //Método que parte el arreglo
    public int partition(int[] arrayL, int peq, int alt){
        int posPivvot = arrayL [alt];
        int i = peq - 1;

        //Reorganización de los elementos
        for (int j = peq; j < alt; j++) {
            if (arrayL[j] <= posPivvot) {
                i++;
                int temp = arrayL[i];
                arrayL[i] = arrayL[j];
                arrayL[j] = temp;
            }
        }

        int temp = arrayL[i + 1];
        arrayL[i + 1] = arrayL [alt];
        arrayL [alt] = temp;

        //Return con la posición del pivote
        return i + 1;
    }

    //Método de QuickSort
    public void quick_sort(int[] arrayL, int peq, int alt) {
        if  (peq < alt) {
            int posPiv = partition(arrayL, peq, alt);

            quick_sort(arrayL, peq, posPiv - 1); //Subarreglo de la izquierda
            quick_sort(arrayL, posPiv + 1, alt); //Subarreglo de la derecha
        }
    }
}
