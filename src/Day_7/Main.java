package Day_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> generateCombinations(int length) {
        List<String> combinations = new ArrayList<>();
        int totalCombinations = (int) Math.pow(2, length - 1);

        for (int i = 0; i < totalCombinations; i++) {
            String binary = String.format("%" + (length - 1) + "s", Integer.toBinaryString(i))
                    .replace(' ', '0');
            combinations.add(binary);
        }

        return combinations;
    }

    public static long solveEquation(String combination, List<Long> numbers){
        long result = numbers.getFirst();
        int startIndex = combination.length() - numbers.size() + 1;

        for(int i = 0; i < numbers.size() - 1; i++){
            if(combination.charAt(startIndex) == '0'){
                result += numbers.get(i + 1);
            } else {
                result *= numbers.get(i + 1);
            }
            startIndex++;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_7/data.txt";
        List<Long> results = new ArrayList<>();
        List<List<Long>> equations = new ArrayList<>();
        List<String> combinations = generateCombinations(12); // here we should input the longest list of numbers, didnt feel like doing it
        long sum = 0;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                long result = Long.parseLong(parts[0].trim());
                results.add(result);

                String[] equationParts = parts[1].trim().split("\\s+");
                List<Long> equation = new ArrayList<>();
                for (String number : equationParts) {
                    equation.add(Long.parseLong(number));
                }
                equations.add(equation);
            }
        }
        br.close();

        for(int i = 0; i<results.size(); i++){
            long expectedResult = results.get(i);
            long result = 0;
            List<Long> equation = equations.get(i);
            int combinationsTotal = (int) Math.pow(2, equation.size() - 1);

            for(int j = 0; j < combinationsTotal; j++){
                result = solveEquation(combinations.get(j), equation);
                if(expectedResult == result){
                    sum += result;
                    break;
                }
            }
        }

        System.out.println("Sum: " + sum);
    }
}