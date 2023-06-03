package com.example.ttt;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Parcelable {
    public static final int BOARD_SIZE = 9;
    public static final int DIFFICULTY_WEAK = 1;
    public static final int DIFFICULTY_ADVANCED = 2;

    private char[] board;
    private boolean playerTurn;
    private boolean gameOver;
    private int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
    };
    private Random random;
    private int[] winningLine;
    private char selectedOption;
    private int playerScore;
    private int computerScore;
    private int difficultyLevel;

    public Game() {
        board = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = ' ';
        }
        playerTurn = true;
        gameOver = false;
        random = new Random();
        playerScore = 0;
        computerScore = 0;
        difficultyLevel = DIFFICULTY_WEAK;
    }

    public void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = ' ';
        }
        playerTurn = true;
        gameOver = false;
        winningLine = null;
    }

    public boolean isCellEmpty(int position) {
        return board[position] == ' ';
    }

    public void makeMove(int position) {
        if (!isCellEmpty(position) || gameOver) {
            return;
        }
        char symbol;
        if (playerTurn) {
            symbol = selectedOption;
        } else {
            symbol = getComputerSymbol();
        }

        board[position] = symbol;

        playerTurn = !playerTurn;
        checkForWin();
        checkForDraw();
    }

    private char getComputerSymbol() {
        if (selectedOption == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    private void checkForWin() {
        for (int[] condition : winConditions) {
            if (board[condition[0]] != ' ' &&
                    board[condition[0]] == board[condition[1]] &&
                    board[condition[0]] == board[condition[2]]) {
                gameOver = true;
                winningLine = condition;
                return;
            }
        }
    }

    private void checkForDraw() {
        if (!gameOver) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i] == ' ') {
                    return;
                }
            }
            gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int[] getWinningLine() {
        return winningLine;
    }

    public int getComputerMoveAdvancedLevel() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == ' ') {
                emptyCells.add(i);
            }
        }

        // Check if there is a winning move for the computer
        for (int[] condition : winConditions) {
            int emptyCount = 0;
            int emptyIndex = -1;
            int computerSymbolCount = 0;

            for (int i = 0; i < condition.length; i++) {
                if (board[condition[i]] == ' ') {
                    emptyCount++;
                    emptyIndex = condition[i];
                } else if (board[condition[i]] == selectedOption) {
                    computerSymbolCount++;
                }
            }

            if (computerSymbolCount == 2 && emptyCount == 1) {
                return emptyIndex; // Make the winning move
            }
        }

        // Check if there is a winning move for the player
        for (int[] condition : winConditions) {
            int emptyCount = 0;
            int emptyIndex = -1;
            int playerSymbolCount = 0;

            for (int i = 0; i < condition.length; i++) {
                if (board[condition[i]] == ' ') {
                    emptyCount++;
                    emptyIndex = condition[i];
                } else if (board[condition[i]] != selectedOption) {
                    playerSymbolCount++;
                }
            }

            if (playerSymbolCount == 2 && emptyCount == 1) {
                return emptyIndex; // Block the player's winning move
            }
        }

        if (!emptyCells.isEmpty()) {
            int randomIndex = random.nextInt(emptyCells.size());
            return emptyCells.get(randomIndex);
        }

        return -1;
    }

    public int getComputerMoveWeakLevel() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == ' ') {
                emptyCells.add(i);
            }
        }

        // Check if there is a winning move
        for (int[] condition : winConditions) {
            int emptyCount = 0;
            int emptyIndex = -1;

            for (int i = 0; i < condition.length; i++) {
                if (board[condition[i]] == ' ') {
                    emptyCount++;
                    emptyIndex = condition[i];
                }
            }

            if (emptyCount == 1) {
                return emptyIndex; // Make the winning move
            }
        }

        if (!emptyCells.isEmpty()) {
            int randomIndex = random.nextInt(emptyCells.size());
            return emptyCells.get(randomIndex);
        }
        return -1;
    }

    public char getSymbolAtPosition(int position) {
        return board[position];
    }

    public void setSelectedOption(char option) {
        selectedOption = option;
    }

    public char getSelectedOption() {
        return selectedOption;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void incrementPlayerScore() {
        playerScore++;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public void incrementComputerScore() {
        computerScore++;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int level) {
        difficultyLevel = level;
    }

    protected Game(Parcel in) {
        board = in.createCharArray();
        playerTurn = in.readByte() != 0;
        gameOver = in.readByte() != 0;
        int numSubarrays = in.readInt(); // Read the number of subarrays
        winConditions = new int[numSubarrays][3]; // Since each subarray has a length of 3

        for (int i = 0; i < numSubarrays; i++) {
            winConditions[i] = in.createIntArray(); // Read each subarray
        }

        random = new Random();
        winningLine = in.createIntArray();
        selectedOption = (char) in.readInt();
        playerScore = in.readInt();
        computerScore = in.readInt();
        difficultyLevel = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeCharArray(board);
        dest.writeByte((byte) (playerTurn ? 1 : 0));
        dest.writeByte((byte) (gameOver ? 1 : 0));

        dest.writeInt(winConditions.length); // Write the number of subarrays
        for (int[] condition : winConditions) {
            dest.writeIntArray(condition); // Write each subarray
        }

        dest.writeIntArray(winningLine);
        dest.writeInt(selectedOption);
        dest.writeInt(playerScore);
        dest.writeInt(computerScore);
        dest.writeInt(difficultyLevel);
    }




    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

   /* @Override
    public void writeToParcel(Parcel dest, int flags) {
        int[] boardInt = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            boardInt[i] = (int) board[i];
        }
        dest.writeIntArray(boardInt);
        // Write the winConditions array
        dest.writeInt(winConditions.length); // Write the number of subarrays
        for (int[] condition : winConditions) {
            dest.writeInt(condition.length); // Write the length of each subarray
            for (int position : condition) {
                dest.writeInt(position); // Write each position in the subarray
            }
        }

        // Write the remaining fields of the Game object
        dest.writeByte((byte) (playerTurn ? 1 : 0));
        dest.writeByte((byte) (gameOver ? 1 : 0));
        dest.writeInt(winningLine != null ? winningLine.length : -1);
        if (winningLine != null) {
            dest.writeIntArray(winningLine);
        }
        dest.writeInt(selectedOption);
        dest.writeInt(playerScore);
        dest.writeInt(computerScore);
        dest.writeInt(difficultyLevel);
    }
*/
}
