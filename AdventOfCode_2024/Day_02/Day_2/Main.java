package Day_2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
// one of the worst and most unreadable code I have ever written (I didnt have time for a good one)
// ill fix that soon, ill just remove the items and check them again

    static private boolean isDifferenceValid(int diff) {
        return diff > 0 && diff < 4;
    }

    static private boolean isSafeReport(List<Integer> raport, boolean isAscending) {
        boolean mistake = false;

        for (int i = 1; i < raport.size() - 1; i++) {
            int num1 = raport.get(i);
            int num2 = raport.get(i + 1);
            int diff = Math.abs(num1 - num2);

            if (!isDifferenceValid(diff)) {
                if(mistake){ return false; }
                mistake = true;

                if(i+2 == raport.size()){
                    return true;
                }

                diff = Math.abs(num1 - raport.get(i + 2));
                if(!isDifferenceValid(diff)){
                    return false;
                } else {
                    i++;
                    continue;
                }
            }

            if ((isAscending && num1 >= num2) || (!isAscending && num1 <= num2)) {
                if(mistake){ return false; }
                mistake = true;

                if(i+2 == raport.size()){
                    return true;
                }

                if(isAscending && num1 >= raport.get(i + 2)){
                    return false;
                }

                if(!isAscending && num1 <= raport.get(i + 2)){
                    return false;
                }
                i++;
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // load the data
        String filePath = "src/Day_2/data.txt";
        int numberOfRows = 3;

        List<Integer>[] reports = new ArrayList[numberOfRows]; // Array of Lists

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int rowIdx = 0;

        try {
            while((line = br.readLine()) != null){
                String[] parts = line.trim().split("\\s+");
                reports[rowIdx] = new ArrayList<>();

                for(int i = 0; i < parts.length; i++){
                    reports[rowIdx].add(Integer.parseInt(parts[i]));
                }
                rowIdx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalSafeReports = 0;

        // 99 is the biggest number found in the dataset, so I set it as a fixed value
        for (List<Integer> raport : reports) {
            int first1 = raport.get(0);
            int first2 = raport.get(1);
            int diff = Math.abs(first1 - first2);

            if (isDifferenceValid(diff)) {
                boolean isAscending = first1 < first2;
                if (isSafeReport(raport, isAscending)) {
                    totalSafeReports++;
                    System.out.println("safe");
                } else {
                    System.out.println("unsafe");
                }
            } else {
                diff = Math.abs(raport.get(0) - raport.get(2));
                boolean isAscending = first1 < first2;
                if(isDifferenceValid(diff)){
                    raport.remove(1);
                    if (isSafeReport(raport, isAscending)) {
                        totalSafeReports++;
                        System.out.println("safe");
                    } else {
                        System.out.println("unsafe");
                    }
                } else {
                    diff = Math.abs(raport.get(1) - raport.get(2));
                    if(isDifferenceValid(diff)){
                        raport.removeFirst();
                        if (isSafeReport(raport, isAscending)) {
                            totalSafeReports++;
                            System.out.println("safe");
                        } else {
                            System.out.println("unsafe");
                        }
                    }
                }
            }
        }

        System.out.println(totalSafeReports);
    }
}