package Day_11;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static Map<String, Long> cache = new HashMap<>();

    public static String reduceString(String s){
        int index = 0, i;

        char[] letters = s.toCharArray();
        for(i=0; i<letters.length; i++){
            if(letters[i] != '0'){
                index = i;
                break;
            }
        }

        if(letters.length > 1 && i == letters.length){
            return "0";
        }
        return s.substring(index);
    }

    private static long blink(String stone, int times) {
        if(times == 0){
            return 1;
        }

        String key = stone + "," + times;
        if(cache.containsKey(key)){
            return cache.get(key);
        }

        long result;

        if(stone.equals("0")) {
            result = blink("1", times-1);
        } else if(stone.length()%2 == 0) {
           int mid = stone.length() / 2;
           String left = stone.substring(0, mid);
           String right = reduceString(stone.substring(mid));

           result = blink(left, times-1) + blink(right, times+-1);
        } else {
            BigInteger number = new BigInteger(stone);
            BigInteger multiplied = number.multiply(BigInteger.valueOf(2024L));
            result = blink(multiplied.toString(), times-1);
        }

        cache.put(key, result);
        return result;
    }
    

   public static void main(String[] args) throws IOException {
        final String filePath = "Day_11/data.txt";
        final int NUMBER_OF_ITERATIONS = 75;

        // load data in
       String data = Files.readString(Paths.get(filePath));
       String[] stones = data.split(" ");

       long stoneTotal = 0;
       for(String stone : stones){
           stoneTotal += blink(stone, NUMBER_OF_ITERATIONS);
       }

       System.out.println("Number of stones: " + stoneTotal + " after " + NUMBER_OF_ITERATIONS + " iterations");
    }
}