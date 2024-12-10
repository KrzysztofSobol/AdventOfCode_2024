package Day_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_7/data.txt";
        List<Integer> results = new ArrayList<>();
        List<List<Integer>> equations = new ArrayList<>();
        int sum = 0;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                int result = Integer.parseInt(parts[0].trim());
                results.add(result);

                String[] equationParts = parts[1].trim().split("\\s+");
                List<Integer> equation = new ArrayList<>();
                for (String number : equationParts) {
                    equation.add(Integer.parseInt(number));
                }
                equations.add(equation);
            }
        }
        br.close();

        System.out.println("Results: " + results);
        System.out.println("Equations: " + equations);

        for(int i = 0; i<results.size(); i++){
            int expectedResult = results.get(i);
            List<Integer> equation = equations.get(i);
            int combinationsTotal = (int) Math.pow(2, equation.size() - 1);

            System.out.println("Decimal: " + i + "  Binary: " + Integer.toBinaryString(i));
            

            for(int j = 0; j < combinationsTotal; j++){
                int result = 0;
                if(expectedResult == result){
                    sum += result;
                }
            }
        }
    }
}