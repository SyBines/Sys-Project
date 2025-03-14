import java.util.Random;
import java.util.ArrayList;

public class Dice {
    private Random rand;

    public Dice() {
        this.rand = new Random();
    }

    // Roll a single die (1-6)
    public int rollOne() {
        return rand.nextInt(6) + 1;
    }

    // Roll two dice and return their sum
    public int rollTwo() {
        return rollOne() + rollOne();
    }

    // Roll five dice and check for Yahtzee-related results
    public String rollFive() {
        ArrayList<Integer> rolls = new ArrayList<>();
        int firstValue = 0;
        boolean allSame = true;
        int threeCount = 0;
        int twoCount = 0;

        for (int i = 0; i < 5; i++) {
            int roll = rollOne();
            rolls.add(roll);

            if (i == 0) {
                firstValue = roll;
            } else if (roll != firstValue) {
                allSame = false;
            }
        }

        // Check for duplicates manually
        for (int i = 1; i <= 6; i++) {
            int count = 0;
            for (int num : rolls) {
                if (num == i) {
                    count++;
                }
            }
            if (count == 5) return "Yahtzee! " + rolls;
            if (count == 4) return "Four of a Kind! " + rolls;
            if (count == 3) threeCount++;
            if (count == 2) twoCount++;
        }

        if (threeCount == 1 && twoCount == 1) return "Full House! " + rolls;
        if (threeCount == 1) return "Three of a Kind! " + rolls;

        return "Rolled: " + rolls;
    }

    public static void main(String[] args) {
        Dice dice = new Dice();
        System.out.println("Rolling one die: " + dice.rollOne());
        System.out.println("Rolling two dice: " + dice.rollTwo());
        System.out.println("Rolling five dice: " + dice.rollFive());
    }
}