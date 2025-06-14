package Day_9;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final String filePath = "Day_9/data.txt";
        String diskMap = Files.readString(Paths.get(filePath));

        List<Integer> decodedDisk = new ArrayList<>();

        // decode the diskMap
        for(int i=0; i<diskMap.length(); i++){
            int id = i/2;
            int times = Character.getNumericValue(diskMap.charAt(i));

            if(i%2 == 0) {
                for(int j = 0; j < times; j++) {
                    decodedDisk.add(id);
                }
            } else {
                for(int j = 0; j < times; j++) {
                    decodedDisk.add(-1);
                }
            }
        }

        int left = 0, right = decodedDisk.size()-1;
        while(decodedDisk.get(left) != -1){ left++; }
        while(decodedDisk.get(right) == -1){ right--; }

        // move the file blocks
        while(left <= right){
            decodedDisk.set(left, decodedDisk.get(right));
            decodedDisk.set(right, -1);

            while(decodedDisk.get(left) != -1){ left++; }
            while(decodedDisk.get(right) == -1){ right--; }
        }

        // filesystem checksum
        BigInteger checksum = BigInteger.ZERO;
        for(int i=0; i<decodedDisk.size(); i++){
            if(decodedDisk.get(i) == -1){
                break;
            }

            BigInteger block = BigInteger.valueOf(decodedDisk.get(i));
            BigInteger fileId = BigInteger.valueOf(i);
            BigInteger outcome = block.multiply(fileId);

            checksum = checksum.add(outcome);
        }

        System.out.println(checksum);
    }
}