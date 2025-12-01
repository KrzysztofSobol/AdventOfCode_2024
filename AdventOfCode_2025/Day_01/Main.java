import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

class Coutner {
    public int pos = 50;

    public int add(int x){
        int startPos = pos;
        int endPos = (pos + x)%100;
        pos = endPos;

        int loops = x/100;
        int remain = x%100;
        if(startPos + remain > 99){
            loops++;
        }

        return loops;
    }

    public int sub(int x){
        int startPos = pos;
        int endPos = ((pos - x) % 100 + 100) % 100;
        pos = endPos;

        int loops = x/100;
        int remain = x%100;
        if(startPos - remain < 0){
            loops++;
        }

        return loops;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        final String path = "AdventOfCode_2025/Day_01/data.txt";
        List<String> data = Files.readAllLines(Paths.get(path));

        record Move(char direction, int steps) {};
        List<Move> moves = new LinkedList<Move>();

        for(String s : data){
            moves.add(new Move(s.charAt(0), Integer.parseInt(s.substring(1))));
        }

        Coutner coutner = new Coutner();
        int zeroCount = 0;
        for(Move m : moves){
            switch (m.direction){
                case 'L': zeroCount += coutner.sub(m.steps); break;
                case 'R': zeroCount += coutner.add(m.steps); break;
            }
        }

        System.out.println(zeroCount);
    }
}