import java.util.Random;

public class Cards {
    private String[][] deck;
    private boolean[][] drawn;
    private String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private Random rand;

    public Cards() {
        deck = new String[suits.length][values.length];
        drawn = new boolean[suits.length][values.length];
        rand = new Random();
        initializeDeck();
    }

    private void initializeDeck() {
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < values.length; j++) {
                deck[i][j] = values[j] + " of " + suits[i];
                drawn[i][j] = false;
            }
        }
    }

    private String drawCard() {
        int suit, value;
        do {
            suit = rand.nextInt(suits.length);
            value = rand.nextInt(values.length);
        } while (drawn[suit][value]);

        drawn[suit][value] = true;
        return deck[suit][value];
    }

    public void playBlackjack() {
        System.out.println("Blackjack hand: " + drawCard() + " and " + drawCard());
    }

    public void playWar() {
        String playerCard = drawCard();
        String computerCard = drawCard();

        System.out.println("You drew: " + playerCard);
        System.out.println("Computer drew: " + computerCard);

        int playerValue = getValue(playerCard);
        int computerValue = getValue(computerCard);

        if (playerValue > computerValue) {
            System.out.println("You win this round!");
        } else if (computerValue > playerValue) {
            System.out.println("Computer wins this round!");
        } else {
            int playerSuit = getSuitRank(playerCard);
            int computerSuit = getSuitRank(computerCard);

            if (playerSuit > computerSuit) {
                System.out.println("You win this round by suit!");
            } else {
                System.out.println("Computer wins this round by suit!");
            }
        }
    }

    private int getValue(String card) {
        for (int i = 0; i < values.length; i++) {
            if (card.startsWith(values[i])) {
                return i;
            }
        }
        return -1;
    }

    private int getSuitRank(String card) {
        for (int i = 0; i < suits.length; i++) {
            if (card.contains(suits[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Cards myDeck = new Cards();
        myDeck.playWar();
    }
}
