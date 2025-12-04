import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static boolean isInvalid(long number){
        int digits = (int) Math.floor(Math.log10(number)) + 1;

        if(digits % 2 == 1){
            return false;
        }

        int tenToPower = (int) Math.pow(10, digits/2);
        long part1 = number / tenToPower;
        long part2 = number % tenToPower;

        for(int i=0; i<digits/2; i++){
            if(part1 % tenToPower != part2 % tenToPower) {
                return  false;
            }
            tenToPower/=10;
        }

        return true;
    }

    public static BigInteger getInvalidIds(long start, long end){
        BigInteger rangeSum = BigInteger.ZERO;

        for(long i = start; i<=end; i++){
            if(isInvalid(i)){
                //System.out.println(i);
                rangeSum = rangeSum.add(BigInteger.valueOf(i));
            }
        }
        return rangeSum;
    }

    public static List<Integer> getDivisors(long number){
        List<Integer> divisors = new LinkedList<>();

        for(int i=1; i<=Math.sqrt(number); i++){
            if(number % i == 0){
                divisors.add(i);
                if(i != number/i){
                    divisors.add((int)(number/i));
                }
            }
        }

        return divisors;
    }

    public static String buildToCompare(int div, String original){
        long times = original.length() / div;
        String pattern = original.substring(0,div);

        return pattern.repeat((int)times);
    }

    public static BigInteger getInvalidIds2(long start, long end){
        BigInteger rangeSum = BigInteger.ZERO;

        for(long i = start; i<=end; i++){
            String s = String.valueOf(i);
            List<Integer> divisors = getDivisors(s.length());
            for(Integer d : divisors){
                if(d == s.length()) continue;

                String patternString = buildToCompare(d, s);

                if(Objects.equals(s, patternString)){
                    rangeSum = rangeSum.add(BigInteger.valueOf(i));
                    break;
                }
            }
        }
        return rangeSum;
    }

    public static void main(String[] args) throws IOException {
        final String path = "AdventOfCode_2025/Day_02/data.txt";
        String dataFromFile = Files.readString(Paths.get(path));
        String[] data = dataFromFile.split(",");

        record Range(long start, long end) {}
        List<Range> ranges = new LinkedList<>();

        for(String range : data){
            String[] rangeParts = range.split("-");
            long start = Long.parseLong(rangeParts[0]);
            long end   = Long.parseLong(rangeParts[1]);

            ranges.add(new Range(start, end));
        }

        // part 1
        BigInteger invalidIdSum = BigInteger.ZERO;
        for(Range r : ranges){
            invalidIdSum = invalidIdSum.add(getInvalidIds(r.start, r.end));
        }

        // part 2
        BigInteger invalidIdSum2 = BigInteger.ZERO;
        for(Range r : ranges){
            invalidIdSum2 = invalidIdSum2.add(getInvalidIds2(r.start, r.end));
        }



        System.out.println(invalidIdSum2);
    }
}