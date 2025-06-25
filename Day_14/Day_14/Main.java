package Day_14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final String path = "Day_14/data.txt";
        final int ROWS = 7;
        final int COLS = 11;

        List<String> data = Files.readAllLines(Paths.get(path));
        ArrayList<Bot> bots = new ArrayList<>();
        int[][] space = new int[ROWS][COLS];

        for(String p : data){
            String[] values = p.replaceAll("[p=v]", "").split("[ ,]");
            int posY = Integer.parseInt(values[0]);
            int posX = Integer.parseInt(values[1]);
            int dy = Integer.parseInt(values[2]);
            int dx = Integer.parseInt(values[3]);

            Bot bot = new Bot(posX, posY, dx, dy);
            bots.add(bot);
        }

        for(Bot bot : bots){
            bot.tick(100, 7, 11);
            space[bot.posX][bot.posY]++;
            System.out.println(bot);
        }

        for (int i=0; i<ROWS; i++){
            for (int j=0; j<COLS; j++){
                if(space[i][j] == 0){
                    System.out.print(".");
                } else {
                    System.out.print(space[i][j]);
                }
            }
            System.out.println();
        }
    }
}