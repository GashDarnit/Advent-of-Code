import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

class Solution {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] inputs = lines.toArray(new String[0]);
        int n = inputs.length;
        char[][] grid = getGrid(inputs, n);
        
        System.out.println("Part One: " + cheatsToSave100Picoseconds(grid, n));
        System.out.println("Part Two: " + cheatsToSave100PicosecondsPartTwo(grid, n));
    }
    
    private static long cheatsToSave100Picoseconds(char[][] grid, int n) {
        long count = 0;
        int[] startAndEnd = getStartPos(grid, n);
        int[][] steps = new int[n][n];
        int startRow = startAndEnd[0], startCol = startAndEnd[1];
        int row = startRow, col = startCol;

        for(int i = 0; i < n; i++) 
            for(int j = 0; j < n; j++) steps[i][j] = -1;
        
        steps[startRow][startCol] = 0;
        while(grid[row][col] != 'E') {
            for(int[] direction : new int[][] { {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} }) {
                int newRow = direction[0], newCol = direction[1];
                if(!inRange(newRow, newCol, n)) continue;
                if(grid[newRow][newCol] == '#') continue;
                if(steps[newRow][newCol] != -1) continue;
                
                steps[newRow][newCol] = steps[row][col] + 1;
                
                row = newRow;
                col = newCol;
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '#') continue;
                for(int[] direction : getDirections(i, j)) {
                    int newRow = direction[0], newCol = direction[1];
                    if(!inRange(newRow, newCol, n)) continue;
                    if(grid[newRow][newCol] == '#') continue;
                    if(steps[i][j] - steps[newRow][newCol] >= 102) count++;
                }
            }
        }

        return count;
    }
    
    private static long cheatsToSave100PicosecondsPartTwo(char[][] grid, int n) {
        long count = 0;
        int[] startAndEnd = getStartPos(grid, n);
        int[][] steps = new int[n][n];
        int startRow = startAndEnd[0], startCol = startAndEnd[1];
        int row = startRow, col = startCol;

        for(int i = 0; i < n; i++) 
            for(int j = 0; j < n; j++) steps[i][j] = -1;
        
        steps[startRow][startCol] = 0;
        while(grid[row][col] != 'E') {
            for(int[] direction : new int[][] { {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} }) {
                int newRow = direction[0], newCol = direction[1];
                if(!inRange(newRow, newCol, n)) continue;
                if(grid[newRow][newCol] == '#') continue;
                if(steps[newRow][newCol] != -1) continue;
                
                steps[newRow][newCol] = steps[row][col] + 1;
                
                row = newRow;
                col = newCol;
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '#') continue;
                for(int cheatSteps = 2; cheatSteps <= 20; cheatSteps++) {
                    for(int dRow = 0; dRow <= cheatSteps; dRow++) {
                        int dCol = cheatSteps - dRow;
                        for(List<Integer> direction : getDirectionsTwo(i, j, dRow, dCol)) {
                            int newRow = direction.get(0), newCol = direction.get(1);
                            if(!inRange(newRow, newCol, n)) continue;
                            if(grid[newRow][newCol] == '#') continue;
                            if(steps[i][j] - steps[newRow][newCol] >= 100 + cheatSteps) count++;
                        }
                    }
                }
            }
        }

        return count;
    }
    
    private static int[][] getDirections(int row, int col) {
        return new int[][] { {row + 2, col}, {row - 2, col}, {row, col + 2}, {row, col - 2}, {row - 1, col - 1}, {row - 1, col + 1}, {row + 1, col - 1}, {row + 1, col + 1} };
    }
    
    private static Set<List<Integer>> getDirectionsTwo(int row, int col, int dRow, int dCol) {
        Set<List<Integer>> set = new HashSet<>();
        for(int[] i : new int[][] {{row + dRow, col + dCol}, {row + dRow, col - dCol}, {row - dRow, col + dCol}, {row - dRow, col - dCol}} ) 
            set.add(Arrays.asList(i[0], i[1]));
        return set;
    }
    
    private static boolean inRange(int row, int col, int n) {
        return ((row >= 0 && row < n) && (col >= 0 && col < n));
    }
    
    private static int[] getStartPos(char[][] grid, int n) {
        int[] pos = new int[2];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 'S') {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        
        return pos;
    }
    
    private static char[][] getGrid(String[] inputs, int n) {
        char[][] grid = new char[n][n];
        for(int i = 0; i < n; i++) {
            char[] temp = inputs[i].toCharArray();
            for(int j = 0; j < n; j++)
                grid[i][j] = temp[j];
        } 
        return grid;
    }
    
    private static void printGrid(char[][] grid, int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) System.out.print(grid[i][j]);
            System.out.println();
        }
    }
}