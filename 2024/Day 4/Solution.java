import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
        char[][] grid = new char[n][n];
        
        for(int i = 0; i < n; i++) {
            char[] temp = inputs[i].toCharArray();
            for(int j = 0; j < n; j++)
                grid[i][j] = temp[j];
        }
        
        System.out.println("Part One: " + totalAppearance(grid, n));
        System.out.println("Part Two: " + totalXMasAppearance(grid, n));
    }
    
    private static int totalAppearance(char[][] grid, int n) {
        int total = 0;
        
        // Check horizontal
        for(int i = 0; i < n; i++)
            total += straightCount(grid[i]);
        
        // Check vertical
        for(int i = 0; i < n; i++) {
            char[] vertical = new char[n];
            for(int j = 0; j < n; j++)
                vertical[j] = grid[j][i];
            total += straightCount(vertical);
        }
        
        // Diagonal: \ - lower half of grid
        for (int startRow = 0; startRow < n; startRow++) {
            char[] diagonal = getDummyArray(n - startRow);
            for (int i = 0; i < n - startRow; i++)
                diagonal[i] = grid[startRow + i][i];
            
            total += straightCount(diagonal);
        }
        
        // Diagonal: \ - upper half of grid
        for (int startCol = 1; startCol < n; startCol++) {
            char[] diagonal = getDummyArray(n - startCol);
            for (int i = 0; i < n - startCol; i++)
                diagonal[i] = grid[i][startCol + i];
            
            total += straightCount(diagonal);
        }
        
        // Diagonal: / - upper half of grid
        for (int startCol = n - 1; startCol >= 0; startCol--) {
            char[] diagonal = new char[Math.min(n, startCol + 1)];
            for (int i = 0; i < Math.min(n, startCol + 1); i++) 
                diagonal[i] = grid[i][startCol - i];
            
            total += straightCount(diagonal);
        }
        
        // Diagonal: / - lower half of grid
        for (int startRow = 1; startRow < n; startRow++) {
            char[] diagonal = new char[n - startRow];
            for (int i = 0; i < n - startRow; i++) 
                diagonal[i] = grid[startRow + i][n - 1 - i];
            
            total += straightCount(diagonal);
        }
        
        return total;
    }
    
    private static int totalXMasAppearance(char[][] grid, int n) {
        int total = 0;
        for(int i = 1; i < n - 1; i++) {
            for(int j = 1; j < n - 1; j++) {
                if(grid[i][j] == 'A' && verifyXMas(grid, n, i, j)) total++;
            }
        }
        return total;
    }
    
    private static int straightCount(char[] line) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(char i : line) {
            sb.append(i);
            if(sb.toString().contains("XMAS") || sb.toString().contains("SAMX")) {
                sb = new StringBuilder();
                sb.append(i);
                count++;
            }
        }
        return count;
    }
    
    private static char[] getDummyArray(int n) {
        char[] dummy = new char[n];
        for(int i = 0; i < n; i++) dummy[i] = '.';
        return dummy;
    }
    
    private static boolean verifyXMas(char[][] grid, int n, int row, int col) {
        boolean valid = false, validOne = false, validTwo = false;
        if((grid[row - 1][col - 1] == 'M' && grid[row + 1][col + 1] == 'S') || (grid[row - 1][col - 1] == 'S' && grid[row + 1][col + 1] == 'M')) validOne = true;
        if( (grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S') ||  (grid[row - 1][col + 1] == 'S' && grid[row + 1][col - 1] == 'M')) validTwo = true;
        if(validOne && validTwo) valid = true;
        
        return valid;
    }
}