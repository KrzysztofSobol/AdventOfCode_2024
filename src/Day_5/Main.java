package Day_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/Day_5/data.txt";
        List<int[]> twoDArray = new ArrayList<>();
        List<List<Integer>> arrayOfLists = new ArrayList<>();

        try {
            twoDArray = loadTwoDArray(filePath);
            arrayOfLists = loadArrayOfLists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total = 0;
        // good old "bruteforce" solution cus its 2am
        for(List<Integer> list : arrayOfLists){
            boolean wasWrong = false;
            for(int i = 0; i < list.size(); i++){
                for(int[] row : twoDArray){
                    if(row[0] == list.get(i)){
                        if(!isInOrder(row[1], i, list)){
                            i=0;
                            wasWrong = true;
                        }
                    }
                }
            }
            if(wasWrong){
                int middleIndex = list.size() / 2;
                total += list.get(middleIndex);
            }
        }
        System.out.println(total);
    }

    private static boolean isInOrder(int lookFor, int index, List<Integer> list){
        for(int i = index-1; i>=0; i--){
            if(list.get(i) == lookFor){
                int temp;
                temp = list.get(index);
                list.set(index, list.get(i));
                list.set(i,temp);
                return false;
            }
        }
        return true;
    }

    private static List<int[]> loadTwoDArray(String filePath) throws IOException {
        List<int[]> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty() || line.contains(",")) {
                break;
            }

            String[] parts = line.split("\\|");
            int[] pair = new int[2];
            pair[0] = Integer.parseInt(parts[0]);
            pair[1] = Integer.parseInt(parts[1]);
            result.add(pair);
        }

        reader.close();
        return result;
    }

    private static List<List<Integer>> loadArrayOfLists(String filePath) throws IOException {
        List<List<Integer>> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        boolean startParsing = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains(",")) {
                startParsing = true;
            }

            if (startParsing) {
                List<Integer> currentList = new ArrayList<>();
                for (String numStr : line.split(",")) {
                    currentList.add(Integer.parseInt(numStr));
                }
                result.add(currentList);
            }
        }

        reader.close();
        return result;
    }
}
