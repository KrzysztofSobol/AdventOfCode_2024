package Day_13;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    static BigInteger solveMachinery(BigInteger xA, BigInteger yA, BigInteger xB, BigInteger yB, BigInteger Zx, BigInteger Zy) {
        // crazy math stuff to solve an:
        // xa*X + xb*Y = Zx
        // ya*X + yb*Y = Zy
        BigInteger ZERO = BigInteger.ZERO;
        BigInteger D = (xA.multiply(yB)).subtract((xB.multiply(yA))); // if thats 0 then we got either none or infinite number of solutions
        BigInteger numX = (Zx.multiply(yB)).subtract((xB.multiply(Zy)));
        BigInteger numY = (xA.multiply(Zy)).subtract((Zx.multiply(yA)));
        if (D.equals(BigInteger.ZERO)) { // no unique solution
            return BigInteger.valueOf(-1);
        }

        BigInteger absD = D.abs();

        if(!numX.mod(absD).equals(ZERO) || !numY.mod(absD).equals(ZERO)){ // must divide evenly cus we need ints
            return BigInteger.valueOf(-1);
        }

        BigInteger X = numX.divide(D);
        BigInteger Y = numY.divide(D);

        if (X.compareTo(BigInteger.ZERO) < 0 || Y.compareTo(BigInteger.ZERO) < 0) {
            return BigInteger.valueOf(-1);
        }

        return X.multiply(BigInteger.valueOf(3)).add(Y);
    }


    public static void main(String[] args) throws IOException {
        final String path = "Day_13/data.txt";
        List<String> data = Files.readAllLines(Paths.get(path))
                .stream().filter(line -> !line.trim().isEmpty()).toList();

        Pattern pattern = Pattern.compile("[XY][+=](\\d+)");
        BigInteger minusOne = BigInteger.valueOf(-1);

        BigInteger tokensUsed = BigInteger.ZERO;
        for(int i=0; i<data.size(); i=i+3){
            String lineA = data.get(i);
            String lineB = data.get(i+1);
            String lineP = data.get(i+2);

            List<BigInteger> v = new LinkedList<>();
            for(String line : List.of(lineA, lineB)){
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    v.add(BigInteger.valueOf(Long.parseLong(matcher.group(1))));
                }
            }

            Matcher matcher = pattern.matcher(lineP);
            while (matcher.find()){
                v.add(BigInteger.valueOf(Long.parseLong(matcher.group(1))).add(BigInteger.valueOf(10000000000000L)));
            }

            BigInteger result = solveMachinery(v.get(0), v.get(1), v.get(2), v.get(3), v.get(4), v.get(5));

            if(!result.equals(minusOne)){
                tokensUsed = tokensUsed.add(result);
            }
        }

        System.out.println(tokensUsed);
    }
}
