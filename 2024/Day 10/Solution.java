import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
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
        int[][] grid = new int[n][n];
        
        for(int i = 0; i < n; i++) {
            char[] temp = inputs[i].toCharArray();
            for(int j = 0; j < n; j++)
                grid[i][j] = Character.getNumericValue(temp[j]);
        }
        
        System.out.println("Part One: " + sumOfTrailheadScores(grid, n));
        System.out.println("Part Two: " + sumOfUniqueTrailheads(grid, n));
    }
    
    private static long sumOfTrailheadScores(int[][] grid, int n) {
        long total = 0;
        List<Pair> trailheads = new ArrayList<>();
        getTrailheads(trailheads, grid, n);
        
        for(Pair trailhead : trailheads) {
            Set<Pair> validSummit = bfs(trailhead, 9, grid, n);
            total += validSummit.size();
        }
        
        return total;
    }
    
    private static long sumOfUniqueTrailheads(int[][] grid, int n) {
        long total = 0;
        List<Pair> trailheads = new ArrayList<>();
        getTrailheads(trailheads, grid, n);
        
        for(Pair trailhead : trailheads)
            total += bfsModified(trailhead, 9, grid, n);
        
        return total;
    }
    
    private static Set<Pair> bfs(Pair source, int target, int[][] grid, int n) {
        Queue<Pair> queue = new LinkedList<>();
        Set<Pair> validSummit = new HashSet<>();
        Set<Pair> visited = new HashSet<>();
        queue.add(source);
        
        while(!queue.isEmpty()) {
            Pair current = queue.poll();
            visited.add(current);
            
            if(grid[current.a][current.b] == target) {
                validSummit.add(current);
                continue;
            }
            
            Pair north = new Pair(current.a - 1, current.b);
            if(inRange(north, n) && !visited.contains(north) && (grid[north.a][north.b] == grid[current.a][current.b] + 1)) queue.add(north);
            
            Pair south = new Pair(current.a + 1, current.b);
            if(inRange(south, n) && !visited.contains(south) && (grid[south.a][south.b] == grid[current.a][current.b] + 1)) queue.add(south);
            
            Pair west = new Pair(current.a, current.b - 1);
            if(inRange(west, n) && !visited.contains(west) && (grid[west.a][west.b] == grid[current.a][current.b] + 1)) queue.add(west);
            
            Pair east = new Pair(current.a, current.b + 1);
            if(inRange(east, n) && !visited.contains(east) && (grid[east.a][east.b] == grid[current.a][current.b] + 1)) queue.add(east);
        }
        return validSummit;
    }
    
    private static int bfsModified(Pair source, int target, int[][] grid, int n) {
        Queue<Pair> queue = new LinkedList<>();
        Map<Pair, Integer> cache = new HashMap<>();
        int total = 0;
        queue.add(source);
        cache.put(source, 1);
        
        while(!queue.isEmpty()) {
            Pair current = queue.poll();
            if(grid[current.a][current.b] == target) total += cache.get(current);
            
            for(Pair direction : getDirections(current)) {
                if(cache.containsKey(direction)) {
                    cache.put(direction, cache.get(direction) + cache.get(current));
                    continue;
                }
                
                if(inRange(direction, n) && (grid[direction.a][direction.b] == grid[current.a][current.b] + 1)) {
                    cache.put(direction, cache.get(current));
                    queue.add(direction);
                }
            }
        }
        
        return total;
    }
    
    private static Pair[] getDirections(Pair current) {
        return new Pair[] {new Pair(current.a - 1, current.b), new Pair(current.a + 1, current.b), new Pair(current.a, current.b - 1), new Pair(current.a, current.b + 1)};
    }
    
    private static boolean inRange(Pair pair, int n) {
        return ((pair.a >= 0 && pair.a < n) && (pair.b >= 0 && pair.b < n));
    }
    
    private static void getTrailheads(List<Pair> trailheads, int[][] grid, int n) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
                if(grid[i][j] == 0) trailheads.add(new Pair(i, j));
        }
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
}