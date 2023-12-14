import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
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
        
        System.out.println("Part One: " + tiltNorthLoad(inputs));
        System.out.println("Part Two: " + tiltCyclesLoad(inputs));
        
    }
    
    private static long tiltNorthLoad(String[] inputs) {
        long totalLoad = 0;
        int m = inputs.length, n = inputs[0].length();
        char[][] grid = convertToTwoDimArray(inputs);
        Queue<Integer> queue = new LinkedList<>();
        
        
        tiltNorth(grid);
        
        int iteration = 0;
        for(char[] row : grid) {
            int count = 0;
            for(char cur : row)
                if(cur == 'O') count++;
            
            totalLoad += (count * (m - iteration));
            iteration++;
        }
        
        return totalLoad;
    }
    
    private static long tiltCyclesLoad(String[] inputs) {
        long totalLoad = 0;
        int m = inputs.length, n = inputs[0].length();
        char[][] grid = convertToTwoDimArray(inputs);
        Set<State> exists = new HashSet<>();
        List<State> states = new ArrayList<>();
        
        long iterations = 0;
        State curState = new State(grid);
        
        exists.add(curState);
        states.add(curState);
        
        while(iterations < 1000000000) {
            iterations++;
            cycle(grid);
            
            curState = new State(grid);
            
            if(exists.contains(curState)) break;
            
            exists.add(curState);
            states.add(curState);
        }
        long firstOccurence = getFirstOccurence(curState, states);
        
        long positionInCycle = (1000000000 - firstOccurence) % (iterations - firstOccurence) + firstOccurence;
        State targetState = states.get((int) positionInCycle);
        char[][] targetGrid = targetState.getGrid();
        
        int iteration = 0;
        for(char[] row : targetGrid) {
            int count = 0;
            for(char cur : row)
                if(cur == 'O') count++;
            
            totalLoad += (count * (m - iteration));
            iteration++;
        }
        
        return totalLoad;
    }
    
    private static void cycle(char[][] grid) {
        tiltNorth(grid);
        tiltWest(grid);
        tiltSouth(grid);
        tiltEast(grid);
    }
    
    private static void tiltNorth(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        
        for(int i = 0; i < m; i++) {
            queue.clear();
            for(int j = 0; j < n; j++) {
                char cur = grid[j][i];
                
                if(cur == '.') {
                    queue.add(j);
                } else if(cur == '#') {
                    queue.clear();
                } else if(cur == 'O') {
                    if(!queue.isEmpty()) {
                        int target = queue.poll();
                        queue.add(j);
                        grid[j][i] = '.';
                        grid[target][i] = 'O';
                        
                    }
                }
            }
        }
    }
    
    private static void tiltSouth(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < m; i++) {
            queue.clear();
            for (int j = n - 1; j >= 0; j--) {
                char cur = grid[j][i];

                if (cur == '.') {
                    queue.add(j);
                } else if (cur == '#') {
                    queue.clear();
                } else if (cur == 'O') {
                    if (!queue.isEmpty()) {
                        int target = queue.poll();
                        queue.add(j);
                        grid[j][i] = '.';
                        grid[target][i] = 'O';
                    }
                }
            }
        }
    }
    
    private static void tiltWest(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        
        for(int i = 0; i < m; i++) {
            queue.clear();
            for(int j = 0; j < n; j++) {
                char cur = grid[i][j];
                
                if(cur == '.') {
                    queue.add(j);
                    
                } else if(cur == '#') {
                    queue.clear();
                    
                } else if(cur == 'O') {
                    if(!queue.isEmpty()) {
                        int target = queue.poll();
                        queue.add(j);
                        grid[i][j] = '.';
                        grid[i][target] = 'O';
                        
                    }
                }
            }
        }
    }
    
    private static void tiltEast(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        
        for(int i = 0; i < m; i++) {
            queue.clear();
            for(int j = n - 1; j >= 0; j--) {
                char cur = grid[i][j];
                
                if(cur == '.') {
                    queue.add(j);
                    
                } else if(cur == '#') {
                    queue.clear();
                    
                } else if(cur == 'O') {
                    if(!queue.isEmpty()) {
                        int target = queue.poll();
                        queue.add(j);
                        grid[i][j] = '.';
                        grid[i][target] = 'O';
                        
                    }
                }
            }
        }
    }
    
    private static long getFirstOccurence(State curState, List<State> states) {
        for(int i = 0; i < states.size(); i++) {
            if(curState.hashCode() == states.get(i).hashCode())
                return i;
        }
        return -1;
    }
    
    private static char[][] convertToTwoDimArray(String[] inputs) {
        int m = inputs.length, n = inputs[0].length();
        char[][] newArr = new char[m][n];
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                newArr[i][j] =  inputs[i].charAt(j);
            }
        }
        
        return newArr;
    }
    
    static class State {
        private char[][] grid;

        public State(char[][] original) {
            grid = new char[original.length][original[0].length];
            
            for(int i = 0; i < original.length; i++) {
                for(int j = 0; j < original[0].length; j++) {
                    grid[i][j] = original[i][j];
                }
            }
        }
        
        public char[][] getGrid() {
            return this.grid;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State that = (State) obj;

            return Arrays.deepEquals(this.grid, that.grid);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(this.grid);
        }
    }
}