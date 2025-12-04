import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

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

        BigInteger invalidIdSum = BigInteger.ZERO;
        for(Range r : ranges){
            invalidIdSum = invalidIdSum.add(getInvalidIds(r.start, r.end));
        }

        System.out.println(invalidIdSum);
    }
}