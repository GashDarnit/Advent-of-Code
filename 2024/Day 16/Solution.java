import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
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
        int n = inputs.length;
        char[][] grid = getGrid(inputs, n);
        
        System.out.println("Part One: " + lowestScoreForReindeer(grid, n));
        System.out.println("Part Two: " + getBestPaths(grid, n));
    }
    
    public static long lowestScoreForReindeer(char[][] grid, int n) {
        int[] pos = getStartAndEndPositions(grid, n);
        int startRow = pos[0], startCol = pos[1];
        int endRow = pos[2], endCol = pos[3];
        
        Queue<Data> pq = new PriorityQueue<>();
        Set<Data> visited = new HashSet<>();
        
        Data startData = new Data(startRow, startCol, 0, 1, 0);
        pq.add(startData);
        visited.add(new Data(startRow, startCol, 0, 1));
        
        while(!pq.isEmpty()) {
            Data current = pq.poll();
            visited.add(new Data(current.a, current.b, current.dA, current.dB));
            
            if(current.a == endRow && current.b == endCol) return current.score;
            
            Data[] directions = new Data[] {new Data(current.a + current.dA, current.b + current.dB, current.dA, current.dB, current.score + 1), new Data(current.a, current.b, current.dB, current.dA * -1, current.score + 1000), new Data(current.a, current.b, current.dB * -1, current.dA, current.score + 1000)};
            for(Data data : directions) {
                Data alt = new Data(data.a, data.b, data.dA, data.dB);
                if(grid[data.a][data.b] == '#') continue;
                if(visited.contains(alt)) continue;
                pq.add(data);
            }
        }
        return -1;
    }
    
    public static long getBestPaths(char[][] grid, int n) {
        int[] pos = getStartAndEndPositions(grid, n);
        int startRow = pos[0], startCol = pos[1];
        int endRow = pos[2], endCol = pos[3];
        
        Data startData = new Data(startRow, startCol, 0, 1, 0);
        
        Queue<Data> pq = new PriorityQueue<>();
        Map<Data, Long> lowestScore = new HashMap<>();
        Map<Data, Set<Data>> backtrackPath = new HashMap<>();
        Set<Data> finalData = new HashSet<>();
        long bestScore = Long.MAX_VALUE;
        
        pq.add(startData);
        lowestScore.put(new Data(startRow, startCol, 0, 1), 0L);
        
        while(!pq.isEmpty()) {
            Data current = pq.poll();
            Data currentAlt = new Data(current.a, current.b, current.dA, current.dB);
            if(!lowestScore.containsKey(currentAlt) || current.score > lowestScore.get(currentAlt)) continue;
            
            if(current.a == endRow && current.b == endCol) {
                if(current.score > bestScore) break;
                bestScore = current.score;
                finalData.add(currentAlt);
            }
            
            Data[] directions = new Data[] {new Data(current.a + current.dA, current.b + current.dB, current.dA, current.dB, current.score + 1), new Data(current.a, current.b, current.dB, current.dA * -1, current.score + 1000), new Data(current.a, current.b, current.dA * -1, current.dA, current.score + 1000)};
            for(Data data : directions) {
                Data dataAlt = new Data(data.a, data.b, data.dA, data.dB);
                if(grid[data.a][data.b] == '#') continue;
                long lowest = lowestScore.containsKey(dataAlt) ? lowestScore.get(dataAlt) : Long.MAX_VALUE;
                
                if(data.score > lowest) continue;
                if(data.score < lowest) {
                    backtrackPath.put(dataAlt, new HashSet<>());
                    lowestScore.put(dataAlt, data.score);
                }
                backtrackPath.get(dataAlt).add(currentAlt);
                pq.add(data);
            }
        }
        
        Queue<Data> queue = new LinkedList<>();
        Set<Data> visited = new HashSet<>();
        
        for(Data i : finalData) {
            queue.add(i);
            visited.add(i);
        }
        
        while(!queue.isEmpty()) {
            Data current = queue.poll();
            if(backtrackPath.containsKey(current)) {
                for(Data i : backtrackPath.get(current)) {
                    if(visited.contains(i)) continue;
                    visited.add(i);
                    queue.add(i);
                }
            }
        }
        Set<Data> tiles = new HashSet<>();
        for(Data i : visited)
            tiles.add(new Data(i.a, i.b));
        
        return tiles.size();
    }
    
    private static int[] getStartAndEndPositions(char[][] grid, int n) {
        int[] pos = new int[4];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 'S') {
                    pos[0] = i;
                    pos[1] = j;
                } else if(grid[i][j] == 'E') {
                    pos[2] = i;
                    pos[3] = j;
                }
            }
        }
        return pos;
    }
    
    private static boolean inRange(int row, int col, int n) {
        return ((row >= 0 && row < n) && (col >= 0 && col < n));
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
            for(int j = 0; j < n; j++)
                System.out.print(grid[i][j]);
            System.out.println();
        }
    }
    
    private static class Data implements Comparable<Data> {
        int a, b, dA, dB;
        long score;

        public Data(int a, int b, int dA, int dB, long score) {
            this.a = a;
            this.b = b;
            this.dA = dA;
            this.dB = dB;
            this.score = score;
        }
        
        public Data(int a, int b, int dA, int dB) {
            this.a = a;
            this.b = b;
            this.dA = dA;
            this.dB = dB;
            this.score = Long.MAX_VALUE;
        }
        
        public Data(int a, int b) {
            this.a = a;
            this.b = b;
            this.dA = -1;
            this.dB = -1;
            this.score = Long.MAX_VALUE;
        }
        
        @Override
        public int compareTo(Data that) {
            return Long.compare(this.score, that.score);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data that = (Data) o;
            return a == that.a && b == that.b && dA == that.dA && dB == that.dB && score == that.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, dA, dB, score);
        }
        
        @Override
        public String toString() {
            return "[" + score + "]: (" + this.a + ", " + this.b + ")";
        }
    }
}