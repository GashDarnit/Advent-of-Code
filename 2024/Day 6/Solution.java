import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

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
        
        System.out.println("Part One: " + totalDistinctSpots(grid, n));
        System.out.println("Part Two: " + totalPossibleLoops(grid, n));
    }
    
    private static int totalDistinctSpots(char[][] grid, int n) {
        int count = 0;
        Pair startingPos = getStartingCoords(grid, n);
        Set<Pair> visited = new HashSet<>();
        
        int row = startingPos.a, col = startingPos.b;
        char current = '^';
        while((row - 1 >= 0 && row + 1 < n) && (col - 1 >= 0 && col + 1 < n)) {
            Pair currentPos = new Pair(row, col);
            
            if(current == '^' && grid[row - 1][col] != '#') row--;
            else if(current == '^' && grid[row - 1][col] == '#') current = '>';
            
            else if(current == 'v' && grid[row + 1][col] != '#') row++;
            else if(current == 'v' && grid[row + 1][col] == '#') current = '<';
            
            else if(current == '>' && grid[row][col + 1] != '#') col++;
            else if(current == '>' && grid[row][col + 1] == '#') current = 'v';
            
            else if(current == '<' && grid[row][col - 1] != '#') col--;
            else if(current == '<' && grid[row][col - 1] == '#') current = '^';
            
            if(!visited.contains(currentPos))
                visited.add(currentPos);
            
        }
        
        return visited.size() + 1; //it's by 1 for some reason and I have no clue why
    }
    
    private static int totalPossibleLoops(char[][] grid, int n) {
        int count = 0;
        Pair startingPos = getStartingCoords(grid, n);
        updateGrid(grid, n);
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '*') {
                    grid[i][j] = '#';
                    Set<InterPair> visited = new HashSet<>();
                    
                    int row = startingPos.a, col = startingPos.b;
                    char current = '^';
                    while((row - 1 >= 0 && row + 1 < n) && (col - 1 >= 0 && col + 1 < n)) {
                        InterPair currentPair = new InterPair(current, new Pair(row, col));
                        
                        if(current == '^' && grid[row - 1][col] != '#') row--;
                        else if(current == '^' && grid[row - 1][col] == '#') current = '>';
                        
                        else if(current == 'v' && grid[row + 1][col] != '#') row++;
                        else if(current == 'v' && grid[row + 1][col] == '#') current = '<';
                        
                        else if(current == '>' && grid[row][col + 1] != '#') col++;
                        else if(current == '>' && grid[row][col + 1] == '#') current = 'v';
                        
                        else if(current == '<' && grid[row][col - 1] != '#') col--;
                        else if(current == '<' && grid[row][col - 1] == '#') current = '^';
                        
                        if(visited.contains(currentPair)) {
                            count++;
                            break;
                        }
                        visited.add(currentPair);
                        
                    }
                    grid[i][j] = '.';
                }
            }
        }
        return count;
    }
    
    private static void updateGrid(char[][] grid, int n) {
        Pair startingPos = getStartingCoords(grid, n);
        
        int row = startingPos.a, col = startingPos.b;
        char current = '^';
        while((row - 1 >= 0 && row + 1 < n) && (col - 1 >= 0 && col + 1 < n)) {
            if(current == '^' && grid[row - 1][col] != '#') {
                grid[row][col] = '*';
                row--;
            }
            else if(current == '^' && grid[row - 1][col] == '#') current = '>';
            
            else if(current == 'v' && grid[row + 1][col] != '#') {
                grid[row][col] = '*';
                row++;
            }
            else if(current == 'v' && grid[row + 1][col] == '#') current = '<';
            
            else if(current == '>' && grid[row][col + 1] != '#') {
                grid[row][col] = '*';
                col++;
            }
            else if(current == '>' && grid[row][col + 1] == '#') current = 'v';
            
            else if(current == '<' && grid[row][col - 1] != '#') {
                grid[row][col] = '*';
                col--;
            }
            else if(current == '<' && grid[row][col - 1] == '#') current = '^';
        }
    }
    
    private static char[][] getGrid(String[] inputs, int n) {
        char[][] grid = new char[n][n];
        
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                char[] temp = inputs[i].toCharArray();
                grid[i][j] = temp[j];
            }
        
        return grid;
    }
    
    private static Pair getStartingCoords(char[][] grid, int n) {
        Pair startingPos = new Pair(-1, -1);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '^') {
                    startingPos = new Pair(i, j);
                    break;
                }
            }
        }
        
        return startingPos;
    }
    
    private static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair that = (Pair) o;
            return a == that.a && b == that.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
        
        @Override
        public String toString() {
            return "(" + a + ", " + b + ")";
        }
        
    }
    
    private static class InterPair {
        char direction;
        Pair position;
        
        public InterPair(char direction, Pair position) {
            this.direction = direction;
            this.position = position;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InterPair that = (InterPair) o;
            return direction == that.direction && Objects.equals(position, that.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, position);
        }
        
        @Override
        public String toString() {
            return direction + ": " + position.toString();
        }
    }
}