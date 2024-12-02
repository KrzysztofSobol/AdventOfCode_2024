package Day_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // load the data
        String filePath = "src/Day_1/data.txt";
        int numberOfRows = 1000;

        int[] leftList = new int[numberOfRows];
        int[] rightList = new int[numberOfRows];

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rowIdx = 0;

        try {
            while((line = br.readLine()) != null){
                String[] parts = line.trim().split("\\s+");
                leftList[rowIdx] = Integer.parseInt(parts[0]);
                rightList[rowIdx] = Integer.parseInt(parts[1]);
                rowIdx++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        Arrays.sort(leftList);
        Arrays.sort(rightList);

        int diff = 0;
        for(int i = 0; i < numberOfRows; i++){
            diff += Math.abs(leftList[i] - rightList[i]);
        }

        System.out.println("The total distance between the lists is: " + diff);

        int biggestNumber = Math.max(leftList[numberOfRows - 1], rightList[numberOfRows - 1]);

        int[] appearLookUpTable = new int[biggestNumber];
        for(int i = 0; i < numberOfRows; i++){
            appearLookUpTable[rightList[i] - 1]++;
        }

        int similarityScore = 0;
        for(int i = 0; i < numberOfRows; i++){
            int leftIndex = leftList[i];
            similarityScore += leftIndex * appearLookUpTable[leftIndex-1];
        }

        System.out.println("The similarity score is : " + similarityScore);
    }
}