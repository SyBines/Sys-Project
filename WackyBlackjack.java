import java.util.*;
import java.util.prefs.Preferences;
import javax.sound.sampled.*;
import java.io.File;

public class WackyBlackjack {

    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();
    static Preferences prefs = Preferences.userRoot().node("wacky_blackjack");

    static List<String> deck = new ArrayList<>();
    static List<String> playerHand = new ArrayList<>();
    static List<String> dealerHand = new ArrayList<>();

    static String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    static String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public static void main(String[] args) {
        try {
            System.out.println("🎲 Welcome to WACKY BLACKJACK 🎲");
            playSound("lets_play.wav"); // Intro music

            initializeDeck();
            shuffleDeck();

            playerHand.clear();
            dealerHand.clear();

            // Initial Deal
            playerHand.add(drawCard());
            dealerHand.add(drawCard());
            playerHand.add(drawCard());
            dealerHand.add(drawCard());

            System.out.println("Your hand: " + playerHand + " | Total: " + calculateHandValue(playerHand));
            System.out.println("Dealer shows: " + dealerHand.get(0));

            // Player turn
            while (true) {
                System.out.print("Hit or Stand? ");
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("hit")) {
                    playerHand.add(drawCard());
                    int total = calculateHandValue(playerHand);
                    System.out.println("Your hand: " + playerHand + " | Total: " + total);
                    if (total > 21) {
                        System.out.println("💥 You busted! Dealer wins.");
                        updateWinCounts("Dealer wins!");
                        playSound("idiot.wav");
                        return;
                    }
                } else if (choice.equals("stand")) {
                    break;
                } else {
                    System.out.println("Type 'hit' or 'stand'!");
                }
            }

            // Dealer turn
            System.out.println("\nDealer's turn...");
            System.out.println("Dealer hand: " + dealerHand + " | Total: " + calculateHandValue(dealerHand));

            while (calculateHandValue(dealerHand) < 17) {
                String card = drawCard();
                dealerHand.add(card);
                System.out.println("Dealer draws: " + card);
                System.out.println("Dealer hand: " + dealerHand + " | Total: " + calculateHandValue(dealerHand));
            }

            int playerTotal = calculateHandValue(playerHand);
            int dealerTotal = calculateHandValue(dealerHand);

            System.out.println("\nFinal Results:");
            System.out.println("Your hand: " + playerHand + " | Total: " + playerTotal);
            System.out.println("Dealer hand: " + dealerHand + " | Total: " + dealerTotal);

            String result;
            if (dealerTotal > 21 || playerTotal > dealerTotal) {
                result = "You win!";
                playSound("applause2_x.wav");
            } else if (dealerTotal > playerTotal) {
                result = "Dealer wins!";
                playSound("idiot.wav");
            } else {
                result = "It's a tie!";
                playSound("snore_x.wav");
            }

            System.out.println("🃏 Result: " + result);
            playSound("island_music_x.wav");
            updateWinCounts(result);
            displayScore();

        } catch (Exception e) {
            System.out.println("💥 Something went wrong: " + e.getMessage());
        }
    }

    public static void initializeDeck() {
        deck.clear();
        for (String suit : suits) {
            for (String value : values) {
                deck.add(value + " of " + suit);
            }
        }
    }

    public static void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public static String drawCard() {
        if (deck.isEmpty()) {
            initializeDeck();
            shuffleDeck();
        }
        return deck.remove(0);
    }

    public static int calculateHandValue(List<String> hand) {
        int total = 0;
        int aces = 0;
        for (String card : hand) {
            String value = card.split(" ")[0];
            if (value.equals("A")) {
                total += 11;
                aces++;
            } else if (value.equals("K") || value.equals("Q") || value.equals("J")) {
                total += 10;
            } else {
                total += Integer.parseInt(value);
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public static void playSound(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("🔇 Sound file not found: " + filename);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            while (!clip.isRunning()) Thread.sleep(10);
            while (clip.isRunning()) Thread.sleep(10);

            clip.close();
        } catch (Exception e) {
            System.out.println("🔇 Failed to play sound: " + e.getMessage());
        }
    }

    public static void updateWinCounts(String result) {
        int playerWins = prefs.getInt("playerWins", 0);
        int dealerWins = prefs.getInt("dealerWins", 0);

        if (result.contains("You win")) {
            prefs.putInt("playerWins", ++playerWins);
        } else if (result.contains("Dealer wins")) {
            prefs.putInt("dealerWins", ++dealerWins);
        }
    }

    public static void displayScore() {
        int playerWins = prefs.getInt("playerWins", 0);
        int dealerWins = prefs.getInt("dealerWins", 0);
        System.out.println("\n🏆 Total Wins:");
        System.out.println("You: " + playerWins);
        System.out.println("Dealer: " + dealerWins);
    }
}