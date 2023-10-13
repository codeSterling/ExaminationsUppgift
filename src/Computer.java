import java.util.List;
import java.util.Random;

public class Computer {
    //Randomiserad funktion med random läggninga av symbol computer
    public int makeMove(List<Integer> playerPositions, List<Integer> computerPositions) {
        int pos;
        Random rand = new Random();

        do {
            pos = rand.nextInt(9) + 1;
        } while (isPositionTaken(pos, playerPositions, computerPositions));

        computerPositions.add(pos);
        return pos;
    }
    // Ser till så att symbol ej placeras på redan tagen position
    private boolean isPositionTaken(int position, List<Integer> playerPositions, List<Integer> computerPositions) {
        return playerPositions.contains(position) || computerPositions.contains(position);
    }
}