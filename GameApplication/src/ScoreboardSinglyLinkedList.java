/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Modified by Patrick Kubiak - 2/10/2014
 */

import net.datastructures.SinglyLinkedList;

/**
 * Class for storing high scores in an array in nondecreasing order.
 */
public class ScoreboardSinglyLinkedList extends SinglyLinkedList {
    private static int numEntries = 0;              // number of actual entries
    private static int maxEntries = 10;             // max number of entries
    private static SinglyLinkedList<GameEntry> board;

    /**
     * Constructs an empty scoreboard with the given capacity for storing entries.
     */
    public ScoreboardSinglyLinkedList(int maxEntries) {
        this.board = new SinglyLinkedList<GameEntry>();
        this.maxEntries = maxEntries;
    }

    /**
     * Attempt to add a new score to the collection (if it is high enough)
     */
    public static void add(GameEntry e) {
        int newScore = e.getScore();
        // is the new entry e really a high score?
        if (board.size() > 0) {
            if (newScore > board.last().getScore()) {
                for (int i = 0; i < board.size(); i++) {
                    if (newScore > board.get2(i).getScore()) {
                        board.addNode(i, e); // score belongs here, insert
                        numEntries++;
                        sizeCheck();
                        return;
                    }
                }
            } else if (board.size() < maxEntries) {
                board.addLast(e); // board isn't full, add at tail
                numEntries++;
                return;
            }
        } else {
            board.addFirst(e);  // board is empty, add entry
            numEntries++;
            return;
        }
    }

    //maintain size after adding a score
    private static void sizeCheck()
    {
        if (board.size() > maxEntries) {
            board.removeLast(); // board has too many entries, remove the tail
            numEntries--;
        }
    }

    // Remove and return the high score at index i.
    public static void remove(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= numEntries)
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        board.removeNode(i);
        numEntries--;
    }

    // Returns a string representation of the high scores list.
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int j = 0; j < numEntries; j++) {
            if (j > 0)
                sb.append(", ");                   // separate entries by commas
            sb.append(board.get2(j));
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // The main method
        ScoreboardSinglyLinkedList highscores = new ScoreboardSinglyLinkedList(5);
        String[] names = {"Rob", "Mike", "Rose", "Jill", "Jack", "Anna", "Paul", "Bob"};
        int[] scores = {750, 1105, 590, 740, 510, 660, 720, 400};

        for (int i = 0; i < names.length; i++) {
            GameEntry gE = new GameEntry(names[i], scores[i]);
            System.out.println("Adding " + gE);
            highscores.add(gE);
            System.out.println(" Scoreboard: " + highscores);
        }

        System.out.println("Removing score at index " + 3);
        highscores.remove(3);
        System.out.println(highscores);
        System.out.println("Removing score at index " + 0);
        highscores.remove(0);
        System.out.println(highscores);
        System.out.println("Removing score at index " + 1);
        highscores.remove(1);
        System.out.println(highscores);
        System.out.println("Removing score at index " + 1);
        highscores.remove(1);
        System.out.println(highscores);
        System.out.println("Removing score at index " + 0);
        highscores.remove(0);
        System.out.println(highscores);
    }
}
