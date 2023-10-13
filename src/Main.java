import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Arraylist som lagrar data för spelarens position för checkwinner
    static ArrayList<Integer> playerPositions1 = new ArrayList<Integer>();
    static ArrayList<Integer> playerPositions2 = new ArrayList<Integer>();
    //Räknare för antal drag
    static int totalMoves = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true; // Boolean för att kontrollera om spelet ska spelas igen
        GameBoard gameBoard = new GameBoard();
        //Loop för hantering av spelläge och interface hantering
        while (playAgain) {
            int gameMode = 0;
            while (gameMode != 1 && gameMode != 2) {
                System.out.println("Choose gamemode:\n1. Multiplayer \n2. Play against Computer");
                if (scanner.hasNextInt()) {
                    gameMode = scanner.nextInt();
                    if (gameMode != 1 && gameMode != 2) {
                        System.out.println("Invalid input. Please enter 1 for multiplayer or 2 to play against the computer.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter 1 for multiplayer or 2 to play against the computer.");
                    scanner.next();
                }
            }

            if (gameMode == 1) {
                playGame(gameBoard); // Spela mot en annan spelare
            } else if (gameMode == 2) {
                playGameVsComputer(gameBoard); // Spela mot datorn
            }

            System.out.println("Do you want to play again? (yes/no)");
            String playAgainInput = scanner.next().toLowerCase();

            while (!playAgainInput.equals("yes") && !playAgainInput.equals("no")) {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                playAgainInput = scanner.next().toLowerCase();
            }

            if (playAgainInput.equals("no")) {
                playAgain = false;
            }
        }

        System.out.println("Thank you for playing! Bye.");
    }
//Spelläge mot datorn
    public static void playGameVsComputer(GameBoard gameBoard) {
        Scanner scanner = new Scanner(System.in);

        String playerWinMessage = "Congratulations %s won!";
        String computerWinMessage = "Congratulations Computer won!";
        String drawMessage = "It's a tie! No one won.";

            // Rensa positionslistor och återställ antal drag
            playerPositions1.clear();
            playerPositions2.clear();
            totalMoves = 0;

            System.out.println("Player name: ");
            String playerName = scanner.nextLine();
            Player player1 = new Player(playerName);
            Computer computer = new Computer();

            System.out.println("Welcome, " + player1.getPlayerName() + " and Computer!");

            // Skriv ut spelplanen
            gameBoard.printBoard();

            boolean gameOver = false;
            boolean player1Turn = true;

            while (!gameOver) {
                // Spelarens tur
                if (player1Turn) {
                    int playerPos1 = 0;
                    boolean validInput = false;
                    do {
                        try {
                            System.out.print(player1.getPlayerName() + ", enter your placement (1-9): ");
                            playerPos1 = scanner.nextInt();

                            if (playerPos1 < 1 || playerPos1 > 9) {
                                System.out.println("Invalid input. Please pick a number between 1 and 9.");
                            } else if (isPositionTaken(playerPos1, playerPositions1, playerPositions2)) {
                                System.out.println("Position taken, pick another.");
                            } else {
                                validInput = true;
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();
                        }
                    } while (!validInput || playerPos1 < 1 || playerPos1 > 9 || isPositionTaken(playerPos1, playerPositions1, playerPositions2));

                    gameBoard.placePiece(playerPos1, "Player 1");
                    playerPositions1.add(playerPos1);
                    totalMoves++;
                }
                // Datorns tur
                else {
                    System.out.println("Computer´s turn...");

                    int computerPos = computer.makeMove(playerPositions1, playerPositions2);
                    gameBoard.placePiece(computerPos, "Computer");
                    playerPositions2.add(computerPos);
                    totalMoves++;
                }

                gameBoard.printBoard();
                // Kontrollera om spelaren 1 har vunnit
                if (checkWinner(playerPositions1).equals("Player1 won!")) {
                    System.out.println(String.format(playerWinMessage, player1.getPlayerName()));
                    gameOver = true;
                    gameBoard.clearBoard();
                    break;
                }

                // Kontrollera om datorn har vunnit
                if (checkWinner(playerPositions2).equals("Player2 won!")) {
                    System.out.println(computerWinMessage);
                    gameOver = true;
                    gameBoard.clearBoard();
                }

                // Kolla om det är oavgjort om ingen har vunnit
                if (!gameOver && isDraw(totalMoves)) {
                    System.out.println(drawMessage);
                    gameOver = true;
                    gameBoard.clearBoard();
                 }
                // Växlar mellan true och false för att byta tur.
                player1Turn = !player1Turn;
            }
    }

    //Spelläge multiplayer
    public static void playGame(GameBoard gameBoard) {
        Scanner scanner = new Scanner(System.in);

        String playerWinMessage = "Congratulations %s won!";
        String drawMessage = "It's a tie! No one won.";

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

        //Skriver ut spelplanen
        gameBoard.printBoard();

        boolean gameOver = false;

        //While statement för att få spelet att gå runt
        while(!gameOver) {
            //Ber om placering till spelaren
            Scanner scan = new Scanner(System.in);

            //player 1 se till så att man lägger symbol vid ledig position
            int playerPos1 = 0;
            boolean validInput = false;
            do {
                try {
                    System.out.print(player1.getPlayerName() + ", enter your placement (1-9): ");
                    playerPos1 = scanner.nextInt();

                    if (playerPos1 < 1 || playerPos1 > 9) {
                        System.out.println("Invalid input. Please pick a number between 1 and 9.");
                    } else if (isPositionTaken(playerPos1, playerPositions1, playerPositions2)) {
                        System.out.println("Position taken, pick another.");
                    } else {
                        validInput = true;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next();  // Clear the invalid input from the scanner
                }
            } while (!validInput || playerPos1 < 1 || playerPos1 > 9 || isPositionTaken(playerPos1, playerPositions1, playerPositions2));

            gameBoard.placePiece(playerPos1, "Player 1");
            playerPositions1.add(playerPos1);
            totalMoves++;

            gameBoard.printBoard();

            // Kontrollera om spelaren 1 har vunnit
            if (checkWinner(playerPositions1).equals("Player1 won!")) {
                System.out.println((String.format(playerWinMessage, player1.getPlayerName())));
                gameOver = true;
                gameBoard.clearBoard();
                break;
            }

            // Kolla om det är oavgjort
            if (!gameOver && isDraw(totalMoves)) {
                System.out.println(drawMessage);
                gameOver = true;
                gameBoard.clearBoard();
                // Avsluta spelet om det är oavgjort
                break;
            }

            //player 2 se till så att man lägger symbol, vid ledig position och valid input
            int playerPos2 = 0;
            boolean validInput2 = false;
            do {
                try {
                    System.out.print(playerName2 + ", enter your placement (1-9): ");
                    playerPos2 = scan.nextInt();

                    if (playerPos2 < 1 || playerPos2 > 9) {
                        System.out.println("Invalid input. Please pick a number between 1 and 9.");
                    } else if (isPositionTaken(playerPos2, playerPositions2, playerPositions1)) {
                        System.out.println("Position taken, pick another.");
                    } else {
                        validInput2 = true;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scan.next();  // Clear the invalid input from the scanner
                }
            } while (!validInput2 || playerPos2 < 1 || playerPos2 > 9 || isPositionTaken(playerPos2, playerPositions2, playerPositions1));

            gameBoard.placePiece(playerPos2, "Player 2");
            playerPositions2.add(playerPos2);
            totalMoves++;

            gameBoard.printBoard();

            // Kontrollera om spelaren 2 har vunnit
            if (checkWinner(playerPositions2).equals("Player2 won!")) {
                System.out.println((String.format(playerWinMessage, player2.getPlayerName())));
                gameOver = true;
                gameBoard.clearBoard();
            }
            // Kolla om det är oavgjort, men bara om det inte finns någon vinnare ännu
            if (!gameOver && isDraw(totalMoves)) {
                System.out.println(drawMessage);
                gameOver = true;
                gameBoard.clearBoard();
                // Avsluta spelet om det är oavgjort
                break;
            }
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
                return "Player1 won!";
            } else if (playerPositions2.containsAll(l)) {
                return "Player2 won!";
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
