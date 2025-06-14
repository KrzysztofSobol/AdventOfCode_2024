package Day_9;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int blockLength(List<Integer> disk, int index){
        int num = disk.get(index);
        for(int i=index-1; i>=0; i--){
            if(disk.get(i) != num || i==0){
                return index - i;
            }
        }
        return -1;
    }

    public static void insertBlock(List<Integer> disk, int blockLength, int blockIndex){
        int count = 0;
        int blockId = disk.get(blockIndex);
        for(int i=0; i < blockIndex; i++){
            if(disk.get(i) == -1){
                count++;
            } else {
                count = 0;
            }

            if(count == blockLength){
                int startIndex = i-count+1;
                for(int j = 0; j<count; j++){
                    disk.set(startIndex+j, blockId);
                    disk.set(j+blockIndex, -1);
                }
                break;
            }
        }
    }

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

        // move the file blocks
        int right = decodedDisk.size()-1;
        int blockLength;

        while (right > 0){
            blockLength = blockLength(decodedDisk, right);
            insertBlock(decodedDisk, blockLength, right-blockLength+1);
            right -= blockLength;

            while (decodedDisk.get(right) == -1){
                right--;
            }
        }

        // filesystem checksum
        BigInteger checksum = BigInteger.ZERO;
        for(int i=0; i<decodedDisk.size(); i++){
            if(decodedDisk.get(i) != -1){
                BigInteger block = BigInteger.valueOf(decodedDisk.get(i));
                BigInteger fileId = BigInteger.valueOf(i);
                BigInteger outcome = block.multiply(fileId);

                checksum = checksum.add(outcome);
            }
        }

        System.out.println(checksum);
    }
}