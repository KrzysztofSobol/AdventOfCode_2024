package Day_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static ArrayList<String> diagonalArrayLR(char[][] data, int cols){
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

    private static ArrayList<String> diagonalArrayRL(char[][] data, int cols){
        ArrayList<String> diagonalStrings = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(data[j][cols-1-i+j]);
            }
            diagonalStrings.add(diagonal.toString());
        }

        for (int i = 0; i < cols-1; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(data[cols-1-i+j][j]);
            }
            diagonalStrings.add(diagonal.toString());
        }
        return diagonalStrings;
    }

    private static int countXMASOccurrences(String[] data) {
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

    private static boolean checkX_MASPattern(char tl, char tr, char bl, char br){
        if(tl == 'M'){
            if(tr == 'M' && bl == 'S' && br == 'S'){
                return true;
            } else if(tr == 'S' && bl == 'M' && br == 'S'){
                return true;
            }
        } else if(tl == 'S'){
            if(tr == 'S' && bl == 'M' && br == 'M'){
                return true;
            } else if(tr == 'M' && bl == 'S' && br == 'M'){
                return true;
            }
        }
        return false;
    }

    private static int countX_MASOccurrences(char[][] data, int cols) {
        int count = 0;

        for (int i = 1; i < cols-1; i++) {
            for (int j = 1; j < cols-1; j++) {
                if(data[i][j] == 'A'){
                    if(checkX_MASPattern(data[i-1][j-1], data[i-1][j+1], data[i+1][j-1], data[i+1][j+1])){
                        count++;
                    }
                }
            }
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

        String[] originalStringArray = new String[numberOfColumns];
        String[] transposedStringArray = new String[numberOfColumns];
        String[] diagonalArrayLR = diagonalArrayLR(originalDataArray, numberOfColumns).toArray(new String[0]);
        String[] diagonalArrayRL = diagonalArrayRL(originalDataArray, numberOfColumns).toArray(new String[0]);

        // original array
        for (int i = 0; i < numberOfColumns; i++) {
            originalStringArray[i] = new String(originalDataArray[i]);
        }

        // transposed array
        for (int i = 0; i < numberOfColumns; i++) {
            transposedStringArray[i] = new String(transposedData[i]);
        }

        totalXMAS += countXMASOccurrences(originalStringArray);
        totalXMAS += countXMASOccurrences(transposedStringArray);
        totalXMAS += countXMASOccurrences(diagonalArrayLR);
        totalXMAS += countXMASOccurrences(diagonalArrayRL);

        System.out.println("Count in Transposed1: " + countXMASOccurrences(originalStringArray));
        System.out.println("Count in Transposed2: " + countXMASOccurrences(transposedStringArray));
        System.out.println("Count in Diagonal LR: " + countXMASOccurrences(diagonalArrayLR));
        System.out.println("Count in Diagonal RL: " + countXMASOccurrences(diagonalArrayRL));

        System.out.println("Total XMAS: " + totalXMAS);

        System.out.println("Total X-MAS patterns: " + countX_MASOccurrences(originalDataArray, numberOfColumns));
    }
}
