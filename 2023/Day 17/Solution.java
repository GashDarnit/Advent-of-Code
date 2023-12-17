import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
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
        char[][] grid = getGrid(inputs);
        
        System.out.println("Part One: " + getShortestPath(grid));
        System.out.println("Part Two: " + getShortestPathPartTwo(grid));
    }
    
    public static long getShortestPath(char[][] grid) {
        long shortestPath = 0;
        int m = grid.length, n = grid[0].length;
        Comparator<Info> heatLossComparator = Comparator.comparingInt(info -> info.heatLoss);
        PriorityQueue<Info> pq = new PriorityQueue<>(heatLossComparator);
        Set<Info> visited = new HashSet<>();
        
        pq.add(new Info(0, 0, 0, 0, 0, 0));
        
        while(!pq.isEmpty()) {
            Info current = pq.poll();
            
            if(current.row == m - 1 && current.col == n - 1) {
                shortestPath = current.heatLoss;
                break;
            }
            
            Info temp = new Info(0, current.row, current.col, current.dRow, current.dCol, current.steps);
            
            if(visited.contains(temp)) continue;
            
            visited.add(temp);
            
            if(current.steps < 3 && !(current.dRow == 0 && current.dCol == 0)) {
                int newRow = current.row + current.dRow;
                int newCol = current.col + current.dCol;
                
                if((newRow >= 0 && newRow < m) && (newCol >= 0 && newCol < n)) {
                    pq.add(new Info(current.heatLoss + Character.getNumericValue(grid[newRow][newCol]), newRow, newCol, current.dRow, current.dCol, current.steps + 1));
                }
            }
            
            
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] direction : directions) {
                int newDRow = direction[0];
                int newDCol = direction[1];
                
                if (!(newDRow == current.dRow && newDCol == current.dCol) && !(newDRow == current.dRow * -1 && newDCol == current.dCol * -1)) {
                    int newRow = current.row + newDRow;
                    int newCol = current.col + newDCol;
                    
                    if((newRow >= 0 && newRow < m) && (newCol >= 0 && newCol < n)) {
                        pq.add(new Info(current.heatLoss + Character.getNumericValue(grid[newRow][newCol]), newRow, newCol, newDRow, newDCol, 1));
                    }
                }
            }
        }
        
        return shortestPath;
    }
    
    public static long getShortestPathPartTwo(char[][] grid) {
        long shortestPath = 0;
        int m = grid.length, n = grid[0].length;
        Comparator<Info> heatLossComparator = Comparator.comparingInt(info -> info.heatLoss);
        PriorityQueue<Info> pq = new PriorityQueue<>(heatLossComparator);
        Set<Info> visited = new HashSet<>();
        
        pq.add(new Info(0, 0, 0, 0, 0, 0));
        
        while(!pq.isEmpty()) {
            Info current = pq.poll();
            
            if(current.row == m - 1 && current.col == n - 1 && current.steps >= 4) {
                shortestPath = current.heatLoss;
                break;
            }
            
            Info temp = new Info(0, current.row, current.col, current.dRow, current.dCol, current.steps);
            
            if(visited.contains(temp)) continue;
            
            visited.add(temp);
            
            if(current.steps < 10 && !(current.dRow == 0 && current.dCol == 0)) {
                int newRow = current.row + current.dRow;
                int newCol = current.col + current.dCol;
                
                if((newRow >= 0 && newRow < m) && (newCol >= 0 && newCol < n)) {
                    pq.add(new Info(current.heatLoss + Character.getNumericValue(grid[newRow][newCol]), newRow, newCol, current.dRow, current.dCol, current.steps + 1));
                }
            }
            
            if(current.steps >= 4 || (current.dRow == 0 && current.dCol == 0)) {
                int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
                for (int[] direction : directions) {
                    int newDRow = direction[0];
                    int newDCol = direction[1];
                    
                    if (!(newDRow == current.dRow && newDCol == current.dCol) && !(newDRow == current.dRow * -1 && newDCol == current.dCol * -1)) {
                        int newRow = current.row + newDRow;
                        int newCol = current.col + newDCol;
                        
                        if((newRow >= 0 && newRow < m) && (newCol >= 0 && newCol < n)) {
                            pq.add(new Info(current.heatLoss + Character.getNumericValue(grid[newRow][newCol]), newRow, newCol, newDRow, newDCol, 1));
                        }
                    }
                }
            }
        }
        
        return shortestPath;
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
        public int heatLoss;
        public int row;
        public int col;
        public int dRow;
        public int dCol;
        public int steps;
        
        public Info(int heatLoss, int row, int col, int dRow, int dCol, int steps) {
            this.heatLoss = heatLoss;
            this.row = row;
            this.col = col;
            this.dRow = dRow;
            this.dCol = dCol;
            this.steps = steps;
        }
        
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + heatLoss;
            result = 31 * result + row;
            result = 31 * result + col;
            result = 31 * result + dRow;
            result = 31 * result + dCol;
            result = 31 * result + steps;
            
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Info otherInfo = (Info) obj;
            return heatLoss == otherInfo.heatLoss && row == otherInfo.row && col == otherInfo.col && dRow == otherInfo.dRow && dCol == otherInfo.dCol && steps == otherInfo.steps;
        }
        
        @Override
        public String toString() {
            return "Heat Loss: " + heatLoss + "\tCoords: (" + row + ", " + col + ")\tDirections: (" + dRow + ", " + dCol + ")" + "\tSteps: " + steps;
        }
    }
}