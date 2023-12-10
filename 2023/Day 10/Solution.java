import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
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
        
        char[][] map = new char[inputs[0].length()][inputs[0].length()];
        int index = 0;
        for(String line : inputs) {
            int columnIndex = 0;
            for(char item : line.toCharArray()) {
                map[index][columnIndex] = item;
                columnIndex++;
            }
            index++;
        }
        
        Coordinates startingPos = new Coordinates();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map.length; j++) {
                if(map[i][j] == 'S') {
                    startingPos.row = i;
                    startingPos.column = j;
                }
            }
        }
        
        System.out.println("Part 1: " + stepsToReachEnd(map, startingPos));
        System.out.println("Part 2: " + tilesEnclosedByLoop(map, startingPos));
    }
    
    private static int stepsToReachEnd(char[][] map, Coordinates startingPos) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        int m = map.length, n = map[0].length;
        
        queue.add(startingPos);
        visited.add(startingPos);
        
        while(!queue.isEmpty()) {
            Coordinates current = queue.poll();
            String currentItem = Character.toString(map[current.row][current.column]);
            
            //go upwards
            if(current.row > 0 && "S|JL".contains(currentItem) && "|7F".contains(Character.toString(map[current.row - 1][current.column])) && !visited.contains(new Coordinates(current.row - 1, current.column))) {
                Coordinates newCoords = new Coordinates(current.row - 1, current.column);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go downwards
            else if(current.row < m - 1 && "S|7F".contains(currentItem) && "|JL".contains(Character.toString(map[current.row + 1][current.column])) && !visited.contains(new Coordinates(current.row + 1, current.column))) {
                Coordinates newCoords = new Coordinates(current.row + 1, current.column);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go left
            else if(current.column > 0 && "S-J7".contains(currentItem) && "-LF".contains(Character.toString(map[current.row][current.column - 1])) && !visited.contains(new Coordinates(current.row, current.column - 1))) {
                Coordinates newCoords = new Coordinates(current.row, current.column - 1);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go right
            else if(current.column < n - 1 && "S-LF".contains(currentItem) && "-J7".contains(Character.toString(map[current.row][current.column + 1])) && !visited.contains(new Coordinates(current.row, current.column + 1))) {
                Coordinates newCoords = new Coordinates(current.row, current.column + 1);
                visited.add(newCoords);
                queue.add(newCoords);
            }
        }
        
        return (visited.size() / 2);
    }
    
    private static int tilesEnclosedByLoop(char[][] map, Coordinates startingPos) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        int m = map.length, n = map[0].length;
        
        queue.add(startingPos);
        visited.add(startingPos);
        
        while(!queue.isEmpty()) {
            Coordinates current = queue.poll();
            String currentItem = Character.toString(map[current.row][current.column]);
            
            //go upwards
            if(current.row > 0 && "S|JL".contains(currentItem) && "|7F".contains(Character.toString(map[current.row - 1][current.column])) && !visited.contains(new Coordinates(current.row - 1, current.column))) {
                Coordinates newCoords = new Coordinates(current.row - 1, current.column);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go downwards
            if(current.row < m - 1 && "S|7F".contains(currentItem) && "|JL".contains(Character.toString(map[current.row + 1][current.column])) && !visited.contains(new Coordinates(current.row + 1, current.column))) {
                Coordinates newCoords = new Coordinates(current.row + 1, current.column);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go left
            if(current.column > 0 && "S-J7".contains(currentItem) && "-LF".contains(Character.toString(map[current.row][current.column - 1])) && !visited.contains(new Coordinates(current.row, current.column - 1))) {
                Coordinates newCoords = new Coordinates(current.row, current.column - 1);
                visited.add(newCoords);
                queue.add(newCoords);
            }
            //go right
            if(current.column < n - 1 && "S-LF".contains(currentItem) && "-J7".contains(Character.toString(map[current.row][current.column + 1])) && !visited.contains(new Coordinates(current.row, current.column + 1))) {
                Coordinates newCoords = new Coordinates(current.row, current.column + 1);
                visited.add(newCoords);
                queue.add(newCoords);
            }
        }
        
        String startingItem = Character.toString(getStartingPipe(map, startingPos));
        map[startingPos.row][startingPos.column] = startingItem.charAt(0);
        replaceNonLoopItems(map, visited);
        
        Set<Coordinates> outside = new HashSet<>();
        for (int i = 0; i < m; i++) {
            boolean inside = false;
            Boolean up = null;

            for (int j = 0; j < n; j++) {
                char currentItem = map[i][j];

                if (currentItem == '|') {
                    inside = !inside;
                    
                } else if (currentItem == 'L' || currentItem == 'F') {
                    up = currentItem == 'L';
                    
                } else if (currentItem == '7' || currentItem == 'J') {
                    if (currentItem != (up ? 'J' : '7'))
                        inside = !inside;
                    up = null;
                    
                }
                
                if (!inside) {
                    outside.add(new Coordinates(i, j));
                }
            }
        }
        
        Set<Coordinates> notIncluded = new HashSet<>();
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                Coordinates temp = new Coordinates(i, j);
                if(!outside.contains(temp) && !visited.contains(temp)) notIncluded.add(temp);
            }
        }
        
        return notIncluded.size();
    }
    
    private static void replaceNonLoopItems(char[][] map, Set<Coordinates> visited) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                Coordinates temp = new Coordinates(i, j);
                if(!visited.contains(temp)) {
                    map[i][j] = '.';
                }
            }
        }
    }
    
    private static char getStartingPipe(char[][] map, Coordinates startingPos) {
        boolean[] checks = new boolean[4];
        int row = startingPos.row, column = startingPos.column;
        for(int i = 0; i < checks.length; i++) checks[i] = false;
        
        //check above
        if("|7F".contains(Character.toString(map[row - 1][column]))) checks[0] = true;
        //check below
        if("|JL".contains(Character.toString(map[row + 1][column]))) checks[1] = true;
        //check left
        if("-LF".contains(Character.toString(map[row][column - 1]))) checks[2] = true;
        //check right
        if("-J7".contains(Character.toString(map[row][column + 1]))) checks[3] = true;
        
        //straight up and down
        if(checks[0] && checks[1]) return '|';
        //up and left
        else if(checks[0] && checks[2]) return 'J';
        //up and right
        else if(checks[0] && checks[3]) return 'L';
        //left and down
        else if(checks[2] && checks[1]) return '7';
        //straight left and right
        else if(checks[2] && checks[3]) return '-';
        //down and right
        else if(checks[1] && checks[3]) return 'F';
        
        return '.';
    }

    static class Coordinates {
        public int row;
        public int column;
        
        public Coordinates() {}
        public Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}