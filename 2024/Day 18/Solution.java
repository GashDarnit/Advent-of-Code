import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;
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
        int n = 71;
        List<Pair> walls = parseWallCoordinates(inputs);
        
        System.out.println("Part One: " + minimumStepsToReachExit(getInput(inputs, n, 1024), n));
        System.out.println("Part Two: " + firstByteCoordinatesToBlockExit(walls, n));
    }
    
    private static long minimumStepsToReachExit(char[][] grid, int n) {
        return bfs(grid, n);
    }
    
    private static String firstByteCoordinatesToBlockExit(List<Pair> walls, int n) {
        int left = 0, right = walls.size() - 1;
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = '.';

        while (left <= right) {
            int mid = (left + right) / 2;

            applyWalls(grid, walls, mid);
            long result = bfs(grid, n);

            if (result == -1) right = mid - 1;
            else left = mid + 1;
            
            resetGrid(grid, n);
        }
        
        Pair answer = walls.get(left);
        return answer.b + "," + answer.a;
    }
    
    private static long bfs(char[][] grid, int n) {
        long steps = 0;
        int endRow = n - 1, endCol = n - 1;
        Queue<Pair> queue = new LinkedList<>();
        Set<Pair> visited = new HashSet<>();
        Pair startPoint = new Pair(0, 0, 0);
        queue.add(startPoint);
        
        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            
            if (visited.contains(current)) continue;
            visited.add(current);
            
            if (current.a == endRow && current.b == endCol)
                return current.steps;
            
            Pair[] directions = new Pair[] {
                new Pair(current.a - 1, current.b, current.steps + 1),
                new Pair(current.a + 1, current.b, current.steps + 1),
                new Pair(current.a, current.b - 1, current.steps + 1),
                new Pair(current.a, current.b + 1, current.steps + 1)
            };
            
            for (Pair direction : directions) {
                if (!inRange(direction, n)) continue;
                if (grid[direction.a][direction.b] == '#') continue;
                if (visited.contains(direction)) continue;
                
                queue.add(direction);
            }
        }
        
        return -1;
    }
    
    private static void applyWalls(char[][] grid, List<Pair> walls, int limit) {
        for (int i = 0; i <= limit; i++) {
            Pair wall = walls.get(i);
            grid[wall.a][wall.b] = '#';
        }
    }
    
    private static void resetGrid(char[][] grid, int n) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == '#')
                    grid[i][j] = '.';
    }
    
    private static List<Pair> parseWallCoordinates(String[] inputs) {
    List<Pair> walls = new ArrayList<>();
    for (String line : inputs) {
        String[] temp = line.trim().split(",");
        walls.add(new Pair(Integer.parseInt(temp[1].trim()), Integer.parseInt(temp[0].trim()), 0));
    }
    return walls;
}
    
    private static boolean inRange(Pair current, int n) {
        return (current.a >= 0 && current.a < n) && (current.b >= 0 && current.b < n);
    }
    
    private static char[][] getInput(String[] inputs, int n, int limit) {
        char[][] grid = new char[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                grid[i][j] = '.';
        
        int count = 1;
        for(String i : inputs) {
            String[] temp = i.trim().split(",");
            grid[Integer.parseInt(temp[1].trim())][Integer.parseInt(temp[0].trim())] = '#';
            
            if(count == limit) break;
            count++;
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
    
    private static class Pair {
        int a;
        int b;
        int steps;

        public Pair(int a, int b, int steps) {
            this.a = a;
            this.b = b;
            this.steps = steps;
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
            return "(" + this.a + ", " + this.b + ")";
        }
    }
}