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
        int newPosX = posX + (ticks * dx);
        int newPosY = posY + (ticks * dy);

        posX = wrapPosition(newPosX, rows);
        posY = wrapPosition(newPosY, cols);
    }

    private int wrapPosition(int position, int bound) {
        if (position >= 0) {
            return position % bound;
        } else {
            int remainder = position % bound;
            return remainder == 0 ? 0 : bound + remainder;
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
