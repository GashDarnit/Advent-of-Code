import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.lang.Math;

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
        char[][] grid = getInputGrid(inputs, inputs.length);
        int n = inputs.length;
        
        System.out.println("Part One: " + uniqueLocations(grid, n));
        System.out.println("Part Two: " + uniqueLocationsExtended(grid, n));
    }
    
    private static int uniqueLocations(char[][] grid, int n) {
        Set<Pair> visited = new HashSet<>();
        Map<Character, List<Pair>> towers = new HashMap<>();
        getTowers(grid, n, towers);
        
        for(List<Pair> towerList : towers.values()) {
            for(int i = 0; i < towerList.size(); i++) {
                for(int j = i + 1; j < towerList.size(); j++) {
                    Pair source = towerList.get(i);
                    Pair destination = towerList.get(j);
                    Pair newPairA = new Pair((2 * source.a) - destination.a, (2 * source.b) - destination.b);
                    Pair newPairB = new Pair((2 * destination.a) - source.a, (2 * destination.b) - source.b);
                    
                    if((newPairA.a >= 0 && newPairA.a < n) && (newPairA.b >= 0 && newPairA.b < n)) visited.add(newPairA);
                    if((newPairB.a >= 0 && newPairB.a < n) && (newPairB.b >= 0 && newPairB.b < n)) visited.add(newPairB);
                }
            }
        }
        
        return visited.size();
    }
    
    private static int uniqueLocationsExtended(char[][] grid, int n) {
        Set<Pair> visited = new HashSet<>();
        Map<Character, List<Pair>> towers = new HashMap<>();
        getTowers(grid, n, towers);
        
        for(List<Pair> towerList : towers.values()) {
            for(int i = 0; i < towerList.size(); i++) {
                for(int j = 0; j < towerList.size(); j++) {
                    if(i == j) continue;
                    Pair source = towerList.get(i);
                    Pair destination = towerList.get(j);
                    int dRow = destination.a - source.a;
                    int dCol = destination.b - source.b;
                    int row = source.a, col = source.b;
                    
                    while((row >= 0 && row < n) && (col >= 0 && col < n)) {
                        visited.add(new Pair(row, col));
                        row += dRow;
                        col += dCol;
                    }
                    
                }
            }
        }
        
        return visited.size();
    }
    
    private static Pair getDistance(Pair source, Pair destination) {
        return new Pair(Math.abs(source.a - destination.a), Math.abs(source.b - destination.b));
    }
    
    private static void getTowers(char[][] grid, int n, Map<Character, List<Pair>> towers) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] != '.') {
                    if(!towers.containsKey(grid[i][j])) towers.put(grid[i][j], new ArrayList<>());
                    towers.get(grid[i][j]).add(new Pair(i, j));
                }
            }
        }
    }
    
    private static char[][] getInputGrid(String[] inputs, int n) {
        char[][] grid = new char[n][n];
        for(int i = 0; i < n; i++) {
            char temp[] = inputs[i].toCharArray();
            for(int j = 0; j < n; j++) {
                grid[i][j] = temp[j];
            }
        }
        
        return grid;
    }
    
    private static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public void setA(int a) {
            this.a = a;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getA() {
            return this.a;
        }

        public int getB() {
            return this.b;
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