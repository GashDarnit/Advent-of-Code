import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Objects;

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
        char[][] grid = getGrid(inputs);
        
        System.out.println("Part One: " + getEnergizedTiles(grid, new Info(0, -1, 0, 1)));
        System.out.println("Part Two: " + getEnergizedTilesPartTwo(grid));
    }
    
    private static long getEnergizedTiles(char[][] grid, Info curInfo) {
        long total = 0;
        Set<Info> visited = new HashSet<>();
        Queue<Info> queue = new LinkedList<>();
        
        queue.add(curInfo);
        
        while(!queue.isEmpty()) {
            Info current = queue.poll();
            int row = current.row;
            int col = current.col;
            
            row += current.dRow;
            col += current.dCol;
            
            if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
                continue;
            }
            
            char currentChar = grid[row][col];
            
            if(currentChar == '.' || (currentChar == '-' && current.dCol != 0) || (currentChar == '|' && current.dRow != 0)) {
                Info newInfo = new Info(row, col, current.dRow, current.dCol);
                
                if(!visited.contains(newInfo)) {
                    visited.add(newInfo);
                    queue.add(newInfo);
                }
            } else if(currentChar == '/') {
                int dRow = current.dCol * -1;
                int dCol = current.dRow * -1;
                Info newInfo = new Info(row, col, dRow, dCol);
                
                if(!visited.contains(newInfo)) {
                    visited.add(newInfo);
                    queue.add(newInfo);
                }
            } else if(currentChar == '\\') {
                int dRow = current.dCol;
                int dCol = current.dRow;
                Info newInfo = new Info(row, col, dRow, dCol);
                
                if(!visited.contains(newInfo)) {
                    visited.add(newInfo);
                    queue.add(newInfo);
                }
            } else {
                int[][] directions;
                if (currentChar == '|') directions = new int[][]{{1, 0}, {-1, 0}};
                else directions = new int[][]{{0, 1}, {0, -1}};
                
                for (int[] dir : directions) {
                    int dRow = dir[0];
                    int dCol = dir[1];
                    Info newInfo = new Info(row, col, dRow, dCol);
                    
                    if (!visited.contains(newInfo)) {
                        visited.add(newInfo);
                        queue.add(newInfo);
                    }
                }
            }
        }
        
        Set<Info> uniqueCoordinates = new HashSet<>();
        
        for(Info info : visited) {
            Info newInfo = new Info(info.row, info.col, 0, 0);
            
            if(!uniqueCoordinates.contains(newInfo))
                uniqueCoordinates.add(newInfo);
        }
        return uniqueCoordinates.size();
    }
    
    public static long getEnergizedTilesPartTwo(char[][] grid) {
        long highest = 0;
        
        for(int row = 0; row < grid.length; row++) {
            highest = Math.max(highest, getEnergizedTiles(grid, new Info(row, -1, 0, 1)));
            highest = Math.max(highest, getEnergizedTiles(grid, new Info(row, grid[row].length, 0, -1)));
        }
        
        for(int col = 0; col < grid.length; col++) {
            highest = Math.max(highest, getEnergizedTiles(grid, new Info(-1, col, 1, 0)));
            highest = Math.max(highest, getEnergizedTiles(grid, new Info(grid.length, col, 1, 0)));
        }
        
        return highest;
    }
    
    public static char[][] getGrid(String[] inputs) {
        char[][] arr = new char[inputs.length][inputs[0].length()];
        
        for(int i = 0; i < inputs.length; i++) {
            for(int j = 0; j < inputs[i].length(); j++) {
                arr[i][j] = inputs[i].charAt(j);
            }
        }
        
        return arr;
    }
    
    static class Info {
        public int row;
        public int col;
        public int dRow;
        public int dCol;

        public Info(int row, int col, int dRow, int dCol) {
            this.row = row;
            this.col = col;
            this.dRow = dRow;
            this.dCol = dCol;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + row;
            result = 31 * result + col;
            result = 31 * result + dRow;
            result = 31 * result + dCol;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Info otherInfo = (Info) obj;
            return row == otherInfo.row && col == otherInfo.col && dRow == otherInfo.dRow && dCol == otherInfo.dCol;
        }
    }

}