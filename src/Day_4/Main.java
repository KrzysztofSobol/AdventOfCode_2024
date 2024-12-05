package Day_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<String> diagonalArrayLR(char[][] data, int cols){
        ArrayList<String> diagonalStrings = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(data[j][i - j]);
            }
            diagonalStrings.add(diagonal.toString());
        }

        int xyz = cols;
        for(int i = 0; i<cols; i++){
            StringBuilder diagonal = new StringBuilder();
            for(int j = 0; j<i; j++){
                diagonal.append(data[xyz + j][cols - j - 1]);
            }
            xyz--;
            if (diagonal.length() > 0) {
                diagonalStrings.add(diagonal.toString());
            }
        }
        return diagonalStrings;
    }

    public static ArrayList<String> diagonalArrayRL(char[][] data, int cols){
        ArrayList<String> diagonalStrings = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(data[j][cols-1-i+j]);
            }
            diagonalStrings.add(diagonal.toString());
        }

        for (int i = 0; i < cols; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(data[cols-1-i+j][j]);
            }
            diagonalStrings.add(diagonal.toString());
        }
        return diagonalStrings;
    }

    public static int countXMASOccurrences(String[] data) {
        int totalOccurrences = 0;

        for (String str : data) {
            totalOccurrences += countOccurrences(str, "XMAS");
            totalOccurrences += countOccurrences(str, "SAMX");
        }
        return totalOccurrences;
    }

    private static int countOccurrences(String str, String target) {
        int count = 0;
        int index = 0;

        while ((index = str.indexOf(target, index)) != -1) {
            count++;
            index++;
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_4/data.txt";
        int numberOfRows = 140;
        int numberOfColumns = 140;
        int totalXMAS = 0;

        char[][] originalDataArray = new char[numberOfRows][numberOfColumns];
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rowIdx = 0;

        while ((line = br.readLine()) != null && rowIdx < numberOfRows) {
            originalDataArray[rowIdx] = line.toCharArray();
            rowIdx++;
        }

        char[][] transposedData = new char[numberOfColumns][numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                transposedData[j][i] = originalDataArray[i][j];
            }
        }

        String[] transposedStringArray1 = new String[numberOfColumns];
        String[] transposedStringArray2 = new String[numberOfColumns];
        String[] diagonalArrayLR = diagonalArrayLR(originalDataArray, numberOfColumns).toArray(new String[0]);
        String[] diagonalArrayRL = diagonalArrayRL(originalDataArray, numberOfColumns).toArray(new String[0]);

        // original array
        for (int i = 0; i < numberOfColumns; i++) {
            transposedStringArray1[i] = new String(originalDataArray[i]);
        }

        // transposed array
        for (int i = 0; i < numberOfColumns; i++) {
            transposedStringArray2[i] = new String(transposedData[i]);
        }

        totalXMAS += countXMASOccurrences(transposedStringArray1);
        totalXMAS += countXMASOccurrences(transposedStringArray2);
        totalXMAS += countXMASOccurrences(diagonalArrayLR);
        totalXMAS += countXMASOccurrences(diagonalArrayRL);

        System.out.println("Count in Transposed1: " + countXMASOccurrences(transposedStringArray1));
        System.out.println("Count in Transposed2: " + countXMASOccurrences(transposedStringArray2));
        System.out.println("Count in Diagonal LR: " + countXMASOccurrences(diagonalArrayLR));
        System.out.println("Count in Diagonal RL: " + countXMASOccurrences(diagonalArrayRL));

        System.out.println("Total XMAS: " + totalXMAS);
    }
}
