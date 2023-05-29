package com.example.ttt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static final int BOARD_SIZE = 9;

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

    public Game() {
        board = new char[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = ' ';
        }
        playerTurn = true;
        gameOver = false;
        random = new Random();
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

        char symbol = playerTurn ? 'X' : 'O';
        board[position] = symbol;

        playerTurn = !playerTurn;
        checkForWin();
        checkForDraw();
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

    public int getComputerMove() {
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
}
