package Day_11;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
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

    private static void blink(List<String> list, int times) {
        record Split (String left, String right) {}
        HashMap<String, String> cache = new HashMap<>();
        HashMap<String, Split> cacheSplit = new HashMap<>();

        for(int i=0; i<times; i++){
            ListIterator<String> iter = list.listIterator();

            while(iter.hasNext()){
                String current = iter.next();

                if(Objects.equals(current, "0")) {   // 0 swap rule
                    iter.set("1");
                } else if(current.length()%2 == 0){      // split rule
                    if(cacheSplit.containsKey(current)){
                        System.out.println("SplitCache used for: " + current);
                        Split split = cacheSplit.get(current);
                        iter.set(split.left);
                        iter.add(split.right);
                    } else {
                        int mid = current.length() / 2;
                        String firstHalf = current.substring(0, mid);
                        String secondHalfPre = current.substring(mid);
                        String secondHalf = reduceString(secondHalfPre);

                        iter.set(firstHalf);
                        iter.add(secondHalf);

                        cacheSplit.put(current, new Split(firstHalf, secondHalf));
                    }
                } else {                                  // times 2024 rule
                    if(cache.containsKey(current)){ // cache check
                        System.out.println("Cache used for: " + current);
                        iter.set(cache.get(current));
                    } else {
                        BigInteger number = BigInteger.valueOf(Integer.parseInt(current));
                        BigInteger multipliedNumber = number.multiply(BigInteger.valueOf(2024L));
                        String result = multipliedNumber.toString();

                        cache.put(current, result);
                        iter.set(result);
                    }
                }
            }
            System.out.println("Blink number: " + (1+i) + " finished! List size is: " + list.size());
        }
    }
    

   public static void main(String[] args) throws IOException {
        final String filePath = "Day_11/data.txt";
        final int NUMBER_OF_ITERATIONS = 75;

        // load data in
       String data = Files.readString(Paths.get(filePath));
       String[] numbers = data.split(" ");
       List<String> rocks = new LinkedList<>(Arrays.asList(numbers));

       blink(rocks, NUMBER_OF_ITERATIONS);

       System.out.println("Number of stones: " + rocks.size() + " after " + NUMBER_OF_ITERATIONS + " iterations");
    }
}