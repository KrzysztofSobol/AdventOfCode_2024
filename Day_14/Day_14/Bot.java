package Day_14;

import java.nio.file.Paths;

public class Bot {
    public int posX, posY, dx, dy;

    public Bot(int posX, int posY, int dx, int dy) {
        this.posX = posX;
        this.posY = posY;
        this.dx = dx;
        this.dy = dy;
    }

    public void tick(int ticks, int rows, int cols){
        int totalX = ticks * dx;
        int totalY = ticks * dy;

        int posXSum = posX + totalX;
        int posYSum = posY + totalY;

        if(posXSum < 0){
            int times = posXSum / rows;
            int remainder = posXSum - (rows*times);
            if(remainder == 0){
                posX = 0;
            } else{
                posX = rows + remainder;
            }
        } else if (posXSum >= rows){
            int times = posXSum / rows;
            posX = posXSum - (rows * times);
        } else {
            posX = posXSum;
        }

        if(posYSum < 0){
            int times = posYSum / cols;
            int remainder = posYSum - (cols*times);
            if(remainder == 0){
                posX = 0;
            } else{
                posY = cols + remainder;
            }
        } else if (posYSum >= cols){
            int times = posYSum / cols;
            posY = posYSum - (cols * times);
        } else {
            posY = posYSum;
        }
    }

    @Override
    public String toString() {
        return "Bot{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", dx=" + dx +
                ", dy=" + dy +
                '}';
    }
}
