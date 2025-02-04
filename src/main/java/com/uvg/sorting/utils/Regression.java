package com.uvg.sorting.utils;

import java.util.ArrayList;
import java.util.List;

public class Regression {
    public static List<Point> calculateRegression(List<Point> data, String type) {
        switch (type) {
            case "quadratic":
                return calculatePolynomialRegression(data, 2); // Para Selection e Insertion Sort
            case "linear":
                return calculatePolynomialRegression(data, 1); // Para Radix Sort
            default:
                throw new IllegalArgumentException("Tipo de regresión no soportado");
        }
    }

    private static List<Point> calculatePolynomialRegression(List<Point> data, int degree) {
        int n = data.size();
        int terms = degree + 1;
        double[][] matrix = new double[terms][terms + 1];

        // Construir matriz aumentada para el sistema de ecuaciones
        for (int i = 0; i < terms; i++) {
            for (int j = 0; j < terms; j++) {
                matrix[i][j] = 0;
                for (Point p : data) {
                    matrix[i][j] += Math.pow(p.x, i + j);
                }
            }
            
            matrix[i][terms] = 0;
            for (Point p : data) {
                matrix[i][terms] += p.y * Math.pow(p.x, i);
            }
        }

        // Resolver sistema usando eliminación gaussiana
        for (int i = 0; i < terms; i++) {
            // Encontrar pivote máximo
            int maxRow = i;
            for (int k = i + 1; k < terms; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Intercambiar filas
            double[] temp = matrix[i];
            matrix[i] = matrix[maxRow];
            matrix[maxRow] = temp;

            // Hacer el pivote 1
            double pivot = matrix[i][i];
            for (int j = i; j <= terms; j++) {
                matrix[i][j] /= pivot;
            }

            // Eliminar la variable de otras ecuaciones
            for (int k = 0; k < terms; k++) {
                if (k != i) {
                    double factor = matrix[k][i];
                    for (int j = i; j <= terms; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }
        }

        // Extraer coeficientes
        double[] coefficients = new double[terms];
        for (int i = 0; i < terms; i++) {
            coefficients[i] = matrix[i][terms];
        }

        // Generar puntos suavizados
        List<Point> regressionPoints = new ArrayList<>();
        double minX = data.stream().mapToDouble(p -> p.x).min().getAsDouble();
        double maxX = data.stream().mapToDouble(p -> p.x).max().getAsDouble();

        // Usar distribución logarítmica para los puntos X
        int numPoints = 100;
        for (int i = 0; i <= numPoints; i++) {
            double t = i / (double) numPoints;
            double x = minX * Math.pow(maxX/minX, t);
            
            double y = 0;
            for (int j = 0; j < terms; j++) {
                y += coefficients[j] * Math.pow(x, j);
            }
            
            if (y >= 0) { // Solo incluir puntos positivos
                regressionPoints.add(new Point(x, y));
            }
        }

        return regressionPoints;
    }

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}