/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ecoo3;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Chris
 */
public class ECOO3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner fin = new Scanner(new File("DATA31.txt"));
        for (int i = 0; i < 5; ++i) {
            int height = fin.nextInt();
            int width = fin.nextInt();
            fin.nextLine();
            String[] crossword = new String[height];
            for (int j = 0; j < height; ++j) {
                crossword[j] = fin.nextLine();
            }
            int numWords = fin.nextInt();
            fin.nextLine();
            String[] words = new String[numWords];
            for (int j = 0; j < numWords; ++j) {
                String protoWord = fin.nextLine();
                words[j] = "";
                for (int k = 0; k < protoWord.length(); ++k) {
                    if (protoWord.charAt(k) >= 'A' && protoWord.charAt(k) <= 'Z') {
                        words[j] = words[j] + protoWord.charAt(k);
                    }
                }
            }
            System.out.println(solve(crossword, words));
            
        }
    }
    
    public static String solve(String[] crossword, String[] words) {
        boolean[][] allowed = new boolean[crossword[0].length()][crossword.length];
        for (int i = 0; i < allowed.length; ++i) {
            for (int j = 0; j < allowed[0].length; ++j) {
                allowed[i][j] = true;
            }
        }
        for (String word : words) {
            handleWord(crossword, allowed, word);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < crossword.length; ++i) {
            for (int j = 0; j < crossword[0].length(); ++j) {
                if (allowed[j][i]) {
                    result.append(crossword[i].charAt(j));
                }
            }
        }
        return result.toString();
    }
    
    public static boolean handleWord(String[] crossword, boolean[][] allowed, String word) {
        for (int i = 0; i < crossword[0].length(); ++i) {
            for (int j = 0; j < crossword.length; ++j) {
                for (int xdir = -1; xdir <= 1; ++xdir) {
                    for (int ydir = -1; ydir <= 1; ++ydir) {
                        if (xdir == 0 && ydir == 0) {
                            continue;
                        }
                        if (findWord(crossword, word, i, j, xdir, ydir)) {
                            eraseWord(allowed, word, i, j, xdir, ydir);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean findWord(String[] crossword, String word, int x, int y, int xdir, int ydir) {
        if (word.length() == 0) {
            return true;
        }
        if (x >= crossword[0].length() || x < 0 || y >= crossword.length || y < 0) {
            return false;
        }
        char first = word.charAt(0);
        if (crossword[y].charAt(x) == first) {
            return findWord(crossword, word.substring(1), x + xdir, y + ydir, xdir, ydir);
        }
        else {
            return false;
        }
    }
    
    public static void eraseWord(boolean[][] allowed, String word, int x, int y, int xdir, int ydir) {
        if (word.length() == 0) {
        }
        else {
            allowed[x][y] = false;
            eraseWord(allowed, word.substring(1), x + xdir, y + ydir, xdir, ydir);
        }
    }
}
