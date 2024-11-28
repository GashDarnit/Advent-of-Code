import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
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
        char[][] grid = new char[inputs.length][inputs[0].length()];
        
        for(int i = 0; i < grid.length; i++) {
            char[] str = inputs[i].toCharArray();
            for(int j = 0; j < grid.length; j++) 
                grid[i][j] = str[j];
        }
        
        System.out.println("Part 1: " + sumOfAdjacent(grid));
    }
    
    private static long sumOfAdjacent(char[][] grid) {
        Set<Coordinates> visited = new HashSet<>();
        List<Integer> values = new ArrayList<>();
        int n = grid.length;
        long sum = 0;
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if((grid[i][j] != '.') && !(grid[i][j] >= '0' && grid[i][j] <= '9')) {
                    int[] directionsCheck = checkAdjacent(grid, n, i, j);
                    
                    if(directionsCheck[0] != 0) { //There is a value directly above
                        if(directionsCheck[0] == 1) { //No numbers diagonal of it
                            StringBuilder sb = new StringBuilder();
                            char cur = grid[i - 1][j];
                            sb.append(cur);
                            Coordinates coords = new Coordinates(i - 1, j);
                            if(!visited.contains(coords)) {
                                visited.add(coords);
                                values.add(Integer.valueOf(sb.toString()));
                            }
                        } else if(directionsCheck[0] == -1 && directionsCheck[4] == -1) { // Top left and top have values going towards left
                            String result = expandLeft(grid, visited, i, j,"prev");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                            
                        } else if(directionsCheck[0] == 2 && directionsCheck[5] == 2) {
                            String result = expandRight(grid, visited, n, i, j, "prev");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                            
                        } else if(directionsCheck[0] == 9 && directionsCheck[4] == 9 && directionsCheck[5] == 9) {
                            String result = expandBoth(grid, visited, n, i, j, "prev");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                        }
                    } 
                    
                    if(directionsCheck[1] != 0) { //There is a value directly below
                        if(directionsCheck[1] == 1) {
                            StringBuilder sb = new StringBuilder();
                            char cur = grid[i + 1][j];
                            sb.append(cur);
                            Coordinates coords = new Coordinates(i + 1, j);
                            if(!visited.contains(coords)) {
                                visited.add(coords);
                                values.add(Integer.valueOf(sb.toString()));
                            }
                        } else if(directionsCheck[1] == -1 && directionsCheck[6] == -1) { // Bottom left and bottom have values
                            String result = expandLeft(grid, visited, i, j, "next");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                            
                        } else if(directionsCheck[1] == 2 && directionsCheck[7] == 2) {
                            String result = expandRight(grid, visited, n, i, j, "next");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                            
                        } else if(directionsCheck[1] == 9 && directionsCheck[6] == 9 && directionsCheck[7] == 9) {
                            String result = expandBoth(grid, visited, n, i, j, "next");
                            if(result.length() > 0) values.add(Integer.parseInt(result));
                        }
                    } 
                    
                    if(directionsCheck[2] == 1) { // Value to the left
                        String result = expandLeft(grid, visited, i, j - 1, "current");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    } 
                    
                    if(directionsCheck[3] == 1) { // Value to the right
                        String result = expandRight(grid, visited, n, i, j + 1, "current");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    } 
                    
                    if(directionsCheck[4] == 1) { // Value to the top left
                        String result = expandLeft(grid, visited, i, j - 1, "prev");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    } 
                    
                    if(directionsCheck[5] == 1) { // Value to the top right
                        String result = expandRight(grid, visited, n, i, j + 1, "prev");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    } 
                    
                    if(directionsCheck[6] == 1) { // Value to the bottom left
                        String result = expandLeft(grid, visited, i, j - 1, "next");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    } 
                    
                    if(directionsCheck[7] == 1) { // Value to the bottom right
                        String result = expandRight(grid, visited, n, i, j + 1, "next");
                        if(result.length() > 0) values.add(Integer.parseInt(result));
                    }   
                }
            }
        }
        Collections.sort(values);
        for(int i : values) sum += i;
        
        return sum;
    }
    
    private static int[] checkAdjacent(char[][] grid, int n, int posX, int posY) {
        int[] directions = new int[8]; //up, down, left, right, top left, top right, bot left, bot right
        
        //Check cardinal directions
        if((posX - 1 >= 0) && (grid[posX - 1][posY] >= '0' && grid[posX - 1][posY] <= '9')) directions[0] = 1; // Up
        if((posX + 1 < n) && (grid[posX + 1][posY] >= '0' && grid[posX + 1][posY] <= '9')) directions[1] = 1; // Down
        if((posY - 1 >= 0) && (grid[posX][posY - 1] >= '0' && grid[posX][posY - 1] <= '9')) directions[2] = 1; // Left
        if((posY + 1 < n) && (grid[posX][posY + 1] >= '0' && grid[posX][posY + 1] <= '9')) directions[3] = 1; // Right
        
        //Check diagonals
        if((posX - 1 >= 0) && (posY - 1 >= 0) && (grid[posX - 1][posY - 1] >= '0' && grid[posX - 1][posY - 1] <= '9')) directions[4] = 1; // Top left
        if((posX - 1 >= 0) && (posY + 1 < n) && (grid[posX - 1][posY + 1] >= '0' && grid[posX - 1][posY + 1] <= '9')) directions[5] = 1; // Top right
        if((posX + 1 < n) && (posY - 1 >= 0) && (grid[posX + 1][posY - 1] >= '0' && grid[posX + 1][posY - 1] <= '9')) directions[6] = 1; // Bottom left
        if((posX + 1 < n) && (posY + 1 < n) && (grid[posX + 1][posY + 1] >= '0' && grid[posX + 1][posY + 1] <= '9')) directions[7] = 1; // Bottom right
        
        //Check if diagonals are connected or not e.g: [.12], [12.], [123]
        //Numbers diagonals can only be connected if one of the cardinal directions has a value
        //9 = all 3 are connected, so read both sides
        //-1 = only top left has value, so read towards left
        //2 = only top right has value, so read towards right
        if(directions[0] == 1) { //There is a value directly above
            if(directions[4] == 1 && directions[5] == 1) { //Both top left and top right have values
                directions[0] = 9;
                directions[4] = 9;
                directions[5] = 9;
            } else if(directions[4] == 1 && directions[5] == 0) { //Only top left has value
                directions[0] = -1;
                directions[4] = -1;
            } else if(directions[4] == 0 && directions[5] == 1) { //Only top right has value
                directions[0] = 2;
                directions[5] = 2;
            }
        }
        
        //Same diagonal check but with bottom left and right
        if(directions[1] == 1) { //There is a value directly below
            if(directions[6] == 1 && directions[7] == 1) { //Both bottom left and bottom right have values
                directions[1] = 9;
                directions[6] = 9;
                directions[7] = 9;
            } else if(directions[6] == 1 && directions[7] == 0) { //Only bottom left has value
                directions[1] = -1;
                directions[6] = -1;
            } else if(directions[6] == 0 && directions[7] == 1) { //Only bottom right has value
                directions[1] = 2;
                directions[7] = 2;
            }
        }
        
        return directions;
    }
    
    private static String expandLeft(char[][] grid, Set<Coordinates> visited, int i, int j, String expansionDirection) {
        //keep reading left until the coords is no longer a number
        StringBuilder sb = new StringBuilder();
        int internalRow;
        int internalCol = j;
        
        switch(expansionDirection) {
            case "prev": internalRow = i - 1; break;
            case "next": internalRow = i + 1; break;
            case "current": internalRow = i; break;
            default: return "";
        }
        
        char cur = grid[internalRow][internalCol];
        
        while((cur >= '0' && cur <= '9') && (internalCol >= 0)) {
            Coordinates coords = new Coordinates(internalRow, internalCol);
            
            if(!visited.contains(coords)) visited.add(coords);
            else return "";
            
            sb.insert(0, cur);
            internalCol--;
            
            if(internalCol < 0) break;
            cur = grid[internalRow][internalCol];
        }
        
        return sb.toString();
        //if(sb.length() > 0) values.add(Integer.parseInt(sb.toString()));
    }
    
    private static String expandRight(char[][] grid, Set<Coordinates> visited, int n, int i, int j, String expansionDirection) {
        //keep reading right until the coords is no longer a number
        StringBuilder sb = new StringBuilder();
        int internalRow;
        int internalCol = j;
        
        switch(expansionDirection) {
            case "prev": internalRow = i - 1; break;
            case "next": internalRow = i + 1; break;
            case "current": internalRow = i; break;
            default: return "";
        }
        char cur = grid[internalRow][internalCol];
        
        while((cur >= '0' && cur <= '9') && (internalCol < n)) {
            Coordinates coords = new Coordinates(internalRow, internalCol);
            
            if(!visited.contains(coords)) visited.add(coords);
            else return "";
            
            sb.append(cur);
            internalCol++;
            
            if(internalCol >= n) break;
            cur = grid[internalRow][internalCol];
        }
        
        return sb.toString();
    }
    
    private static String expandBoth(char[][] grid, Set<Coordinates> visited, int n, int i, int j, String expansionDirection) {
        StringBuilder sb = new StringBuilder();
        int internalRow;
        int internalCol = j - 1;
        
        switch(expansionDirection) {
            case "prev": internalRow = i - 1; break;
            case "next": internalRow = i + 1; break;
            case "current": internalRow = i; break;
            default: return "";
        }
        char cur = grid[internalRow][internalCol];
        
        while((cur >= '0' && cur <= '9') && (internalCol >= 0)) {
            Coordinates coords = new Coordinates(internalRow, internalCol);
            
            if(!visited.contains(coords)) visited.add(coords);
            else return "";
            
            sb.insert(0, cur);
            internalCol--;
            
            if(internalCol < 0) break;
            cur = grid[internalRow][internalCol];
        }
        
        switch(expansionDirection) {
            case "prev": internalRow = i - 1; break;
            case "next": internalRow = i + 1; break;
            case "current": internalRow = i; break;
            default: return "";
        }
        
        internalCol = j;
        cur = grid[internalRow][internalCol];
        
        while((cur >= '0' && cur <= '9') && (internalCol < n)) {
            Coordinates coords = new Coordinates(internalRow, internalCol);
            
            if(!visited.contains(coords)) visited.add(coords);
            else return "";
            
            sb.append(cur);
            internalCol++;
            
            if(internalCol >= n) break;
            cur = grid[internalRow][internalCol];
        }
        
        return sb.toString();
    }
    
    private static class Coordinates {
        int x;
        int y;
        
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Coordinates otherCoords = (Coordinates) obj;
            return x == otherCoords.x && y == otherCoords.y;
        }
    }
}