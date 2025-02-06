package com.uvg.sorting.utils;

import java.util.ArrayList;
import java.util.List;

public class Regression {
    public static List<Point> calculateRegression(List<Point> data, String type) {
        switch (type) {
            case "quadratic":
                return calculateQuadraticRegression(data);
            case "linear":
                return calculateLinearRegression(data);
            case "nlogn":
                return calculateNLogNRegression(data);
            default:
                throw new IllegalArgumentException("Tipo de regresión no soportado");
        }
    }

    private static List<Point> calculateQuadraticRegression(List<Point> data) {
        int n = data.size();
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0, sumXY = 0, sumX2Y = 0;

        for (Point p : data) {
            double x = p.x;
            double y = p.y;
            double x2 = x * x;
            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumX3 += x2 * x;
            sumX4 += x2 * x2;
            sumXY += x * y;
            sumX2Y += x2 * y;
        }

        double[][] matrix = {
            {n, sumX, sumX2, sumY},
            {sumX, sumX2, sumX3, sumXY},
            {sumX2, sumX3, sumX4, sumX2Y}
        };

        // Gauss elimination
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k <= 3; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        double c = matrix[0][3] / matrix[0][0];
        double b = (matrix[1][3] - matrix[1][0] * c) / matrix[1][1];
        double a = (matrix[2][3] - matrix[2][0] * c - matrix[2][1] * b) / matrix[2][2];

        List<Point> regressionPoints = new ArrayList<>();
        double minX = data.stream().mapToDouble(p -> p.x).min().getAsDouble();
        double maxX = data.stream().mapToDouble(p -> p.x).max().getAsDouble();

        for (int i = 0; i <= 100; i++) {
            double x = minX * Math.pow(maxX/minX, i/100.0);
            double y = a * x * x + b * x + c;
            if (y >= 0) {
                regressionPoints.add(new Point(x, y));
            }
        }

        return regressionPoints;
    }

    private static List<Point> calculateLinearRegression(List<Point> data) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = data.size();

        for (Point p : data) {
            sumX += p.x;
            sumY += p.y;
            sumXY += p.x * p.y;
            sumX2 += p.x * p.x;
        }

        double m = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double b = (sumY - m * sumX) / n;

        List<Point> regressionPoints = new ArrayList<>();
        double minX = data.stream().mapToDouble(p -> p.x).min().getAsDouble();
        double maxX = data.stream().mapToDouble(p -> p.x).max().getAsDouble();

        for (int i = 0; i <= 100; i++) {
            double x = minX * Math.pow(maxX/minX, i/100.0);
            double y = m * x + b;
            if (y >= 0) {
                regressionPoints.add(new Point(x, y));
            }
        }

        return regressionPoints;
    }

    private static List<Point> calculateNLogNRegression(List<Point> data) {
        int n = data.size();
        double sumY = 0, sumXLogX = 0, sumXLogX2 = 0, sumYXLogX = 0;
        
        for (Point p : data) {
            double x = p.x;
            double xLogX = x * Math.log(x);
            sumY += p.y;
            sumXLogX += xLogX;
            sumXLogX2 += xLogX * xLogX;
            sumYXLogX += p.y * xLogX;
        }
        
        // y = ax*log(x) + b
        double a = (n * sumYXLogX - sumY * sumXLogX) / (n * sumXLogX2 - sumXLogX * sumXLogX);
        double b = (sumY - a * sumXLogX) / n;
        
        List<Point> regressionPoints = new ArrayList<>();
        double minX = data.stream().mapToDouble(p -> p.x).min().getAsDouble();
        double maxX = data.stream().mapToDouble(p -> p.x).max().getAsDouble();
        
        for (int i = 0; i <= 100; i++) {
            double x = minX * Math.pow(maxX/minX, i/100.0);
            double y = a * x * Math.log(x) + b;
            if (y >= 0) {
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