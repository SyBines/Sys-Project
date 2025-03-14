import java.util.Random;

public class Coin {
    private Random rand;

    public Coin() {
        this.rand = new Random();
    }

    public String flip() {
        int outcome = rand.nextInt(1000); // Generates a number between 0 and 999

        if (outcome == 0) {
            return "Side"; // 1 in 1000 chance
        } else if (outcome < 500) {
            return "Heads"; // 50% chance
        } else {
            return "Tails"; // 50% chance
        }
    }

    public void simulateFlips(int numFlips) {
        int headsCount = 0;
        int tailsCount = 0;
        int sideCount = 0;

        for (int i = 0; i < numFlips; i++) {
            String result = flip();
            if (result.equals("Heads")) {
                headsCount++;
            } else if (result.equals("Tails")) {
                tailsCount++;
            } else {
                sideCount++;
            }
        }

        System.out.println("Simulated " + numFlips + " coin flips:");
        System.out.println("Heads: " + headsCount);
        System.out.println("Tails: " + tailsCount);
        System.out.println("Side: " + sideCount);
    }
}