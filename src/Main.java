import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Arraylist som lagrar data för spelarens position
    static ArrayList<Integer> playerPositions1 = new ArrayList<Integer>();
    static ArrayList<Integer> playerPositions2 = new ArrayList<Integer>();
    //Räknare för antal drag
    static int totalMoves = 0;

    public static void main(String[] args) {
        //Ange sitt namn
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true; // Variabel för att kontrollera om spelet ska spelas igen

        while (playAgain) {
            int gameMode;

            do {
                System.out.println("Choose gamemode:\n1. Play against other player\n2. play against computer");
                gameMode = scanner.nextInt();
            } while (gameMode != 1 && gameMode != 2);

            if (gameMode == 1) {
                playGame(); // Spela mot en annan spelare
            } else if (gameMode == 2) {
                playGameVsComputer(); // Spela mot datorn
            }

            System.out.println("Do you want to play again? (yes/no)");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("yes");
        }

        System.out.println("Thank you for playing! Bye.");
    }
//Spelläge mot datorn
    public static void playGameVsComputer() {
        Scanner scanner = new Scanner(System.in);

            // Rensa positionslistor och återställ antal drag
            playerPositions1.clear();
            playerPositions2.clear();
            totalMoves = 0;

            System.out.println("Player name: ");
            String playerName = scanner.nextLine();
            Player player1 = new Player(playerName);
            Computer computer = new Computer();

            System.out.println("Welcome, " + player1.getPlayerName() + " and Computer!");

            // Skapa spelplanen
            char[][] gameBoard = {
                    {' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '},
                    {'-', '+', '-', '+', '-'},
                    {' ', '|', ' ', '|', ' '}
            };

            // Skriv ut spelplanen
            printGameBoard(gameBoard);

            boolean gameOver = false;
            boolean player1Turn = true;

            while (!gameOver) {
                // Spelarens tur
                if (player1Turn) {
                    int playerPos1;
                    do {
                        System.out.print(player1.getPlayerName() + ", enter your placement (1-9): ");
                        playerPos1 = scanner.nextInt();

                        if (playerPos1 < 1 || playerPos1 > 9) {
                            System.out.println("Unavailable input. Pick a number between 1-9.");
                        } else if (isPositionTaken(playerPos1, playerPositions1, playerPositions2)) {
                            System.out.println("Position taken, pick another.");
                        }
                    } while (playerPos1 < 1 || playerPos1 > 9 || isPositionTaken(playerPos1, playerPositions1, playerPositions2));

                    placePiece(gameBoard, playerPos1, "Player 1");
                    playerPositions1.add(playerPos1);
                    totalMoves++;

                }
                // Datorns tur
                else {
                    System.out.println("Computer´s turn...");

                    int computerPos = computer.makeMove(playerPositions1, playerPositions2);
                    placePiece(gameBoard, computerPos, "Computer");
                    playerPositions2.add(computerPos);
                    totalMoves++;
                }

                printGameBoard(gameBoard);
                // Kontrollera om spelaren 1 har vunnit
                if (checkWinner(playerPositions1).equals("Congratulations Player1 won!")) {
                    System.out.println("Congratulation, " + playerName + "! You won!");
                    gameOver = true;
                    break;
                }

                // Kontrollera om datorn har vunnit
                if (checkWinner(playerPositions2).equals("Congratulations Player2 won!!")) {
                    System.out.println("Congratulation Computer, you won!");
                    gameOver = true;
                }

                // Kolla om det är oavgjort om ingen har vunnit
                if (!gameOver && isDraw(totalMoves)) {
                    System.out.println("Tied! No one won.");
                    gameOver = true;
                 }
                player1Turn = !player1Turn; // Växlar mellan true och false för att byta tur.
            }
    }


    public static void playGame() {
        Scanner scanner = new Scanner(System.in);

        // Rensa positionslistor och återställ antal drag
        playerPositions1.clear();
        playerPositions2.clear();
        totalMoves = 0;

        System.out.println("Player name 1: ");
        String playerName1 = scanner.nextLine();
        Player player1 = new Player(playerName1);

        System.out.println("Player name2: ");
        String playerName2 = scanner.nextLine();
        Player player2 = new Player(playerName2);

        System.out.println("Welcome, " + player1.getPlayerName() + " and " + player2.getPlayerName() + "!");

        //Skapar spelplanen
        char[][] gameBoard = {
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        //Skriver ut spelplanen
        printGameBoard(gameBoard);

        boolean gameOver = false;

        //While statement för att få spelet att gå runt
        while(!gameOver) {
            //Ber om placering till spelaren
            Scanner scan = new Scanner(System.in);

            //player 1 se till så att man lägger symbol vid ledig position
            int playerPos1;
            do {
                System.out.print(playerName1 + " enter your placement (1-9): ");
                playerPos1 = scan.nextInt();

                if (playerPos1 < 1 || playerPos1 > 9) {
                    System.out.println("Unavaible input. Picka number between 1-9.");
                } else if (isPositionTaken(playerPos1, playerPositions1, playerPositions2)) {
                    System.out.println("Position taken, pick another.");
                }
            } while (playerPos1 < 1 || playerPos1 > 9 || isPositionTaken(playerPos1, playerPositions1, playerPositions2));


            placePiece(gameBoard, playerPos1, "Player 1");
            playerPositions1.add(playerPos1);
            totalMoves++;

            printGameBoard(gameBoard);

            // Kontrollera om spelaren 1 har vunnit
            if (checkWinner(playerPositions1).equals("Congratulations Player1 won!")) {
                System.out.println("Congratulation, " + playerName1 + "! You won!");
                gameOver = true;
                break;
            }

            // Kolla om det är oavgjort
            if (isDraw(totalMoves)) {
                System.out.println("Tied! No one won.");
                gameOver = true;
                // Avsluta spelet om det är oavgjort
                break;
            }

            //player 2 se till så att man lägger symbol vid ledig position
            int playerPos2;
            do {
                System.out.print(playerName2 + " enter your placement (1-9): ");
                playerPos2 = scan.nextInt();

                if (playerPos2 < 1 || playerPos2 > 9) {
                    System.out.println("Unavaible input. Picka number between 1-9.");
                } else if (isPositionTaken(playerPos2, playerPositions2, playerPositions1)) {
                    System.out.println("Position taken, pick another.");
                }
            } while (playerPos2 < 1 || playerPos2 > 9 || isPositionTaken(playerPos2, playerPositions2, playerPositions1));


            placePiece(gameBoard, playerPos2, "Player 2");
            playerPositions2.add(playerPos2);
            totalMoves++;

            printGameBoard(gameBoard);

            // Kontrollera om spelaren 2 har vunnit
            if (checkWinner(playerPositions2).equals("Congratulations Player2 won!!")) {
                System.out.println("Congratulation, " + playerName2 + "! You won!");
                gameOver = true;
            }
            // Kolla om det är oavgjort
            if (isDraw(totalMoves)) {
                System.out.println("Tied! No one won.");
                gameOver = true;
                // Avsluta spelet om det är oavgjort
                break;
            }
        }
    }

    //deklarera placering och symbol
    public static void placePiece(char[][] gameBoard, int pos, String user) {
        char symbol = ' ';

        if (user.equals("Player 1")) {
            symbol = 'X';
            playerPositions1.add(pos);
        } else if (user.equals("Player 2") || user.equals("Computer")) {
            symbol = 'O';
            playerPositions2.add(pos);
        }

        //Switchfunktion för ge rätt position
        switch (pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }

    }

    //Metod för spelplanen
    private static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    //deklarera vinstdragen
    public static String checkWinner(List<Integer> playerPositions) {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        //Lista av en arraylist för vinster.
        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        //Forloop för vinnare. Går igenom listan för checkwinner
        for (List l : winning) {
            if (playerPositions1.containsAll(l)) {
                return "Congratulations Player1 won!";
            } else if (playerPositions2.containsAll(l)) {
                return "Congratulations Player2 won!!";
            } else if (playerPositions.size() + playerPositions2.size() == 9) {
                return "It´s a tie!";
            }
        }

        return "";
    }
    // Funktion för att kontrollera om en position är upptagen
    public static boolean isPositionTaken(int position, List<Integer> playerPositions1, List<Integer> playerPositions2) {
        return playerPositions1.contains(position) || playerPositions2.contains(position);
    }
    // Funktion för att kontrollera oavgjort
    public static boolean isDraw(int totalMoves) {
        return totalMoves == 9;
    }
}
