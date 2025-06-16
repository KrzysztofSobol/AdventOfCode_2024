package Day_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> generateCombinations(int length) {
        List<String> combinations = new ArrayList<>();
        int totalCombinations = (int) Math.pow(3, length - 1);

        for (int i = 0; i < totalCombinations; i++) {
            String ternary = convertToTernary(i, length - 1);
            combinations.add(ternary);
        }

        return combinations;
    }

    private static String convertToTernary(int number, int padLength) {
        if (number == 0) {
            return String.format("%" + padLength + "s", "0").replace(' ', '0');
        }

        StringBuilder ternary = new StringBuilder();
        while (number > 0) {
            ternary.insert(0, number % 3);
            number /= 3;
        }

        return String.format("%" + padLength + "s", ternary).replace(' ', '0');
    }

    public static long solveEquation(String combination, List<Long> numbers){
        long result = numbers.getFirst();
        int startIndex = combination.length() - numbers.size() + 1;

        for(int i = 0; i < numbers.size() - 1; i++){
            if(combination.charAt(startIndex) == '0'){
                result += numbers.get(i + 1);
            } else if(combination.charAt(startIndex) == '1'){
                result *= numbers.get(i + 1);
            } else {
                String combined = result + String.valueOf(numbers.get(i + 1));
                result = Long.parseLong(combined);
            }
            startIndex++;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/Day_7/data.txt";
        List<Long> results = new ArrayList<>();
        List<List<Long>> equations = new ArrayList<>();
        List<String> combinations = generateCombinations(12);
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
            long result;
            List<Long> equation = equations.get(i);
            int combinationsTotal = (int) Math.pow(3, equation.size() - 1);

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