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
        int gridHeight = 0;
        for(int h = 0; h < inputs.length; h++) {
            if(inputs[h].length() == 0) {
                gridHeight = h;
                break;
            }
        }
        char[][] grid = new char[gridHeight][inputs[0].length()];
        List<Character> movements = getInput(inputs, grid, gridHeight);
        
        System.out.println("Part One: " + sumOfBoxesGPS(inputs, grid, movements));
        System.out.println("Part Two: " + sumOfSecondBoxesGPS(inputs, movements));
    }
    
    private static long sumOfBoxesGPS(String[] inputs, char[][] grid, List<Character> movements)  {
        long total = 0;
        Pair robotPosition = getRobotCoordinates(grid);
        
        for(int i = 0; i < movements.size(); i++) {
            char current = movements.get(i);
            if(current == '<') {
                if(grid[robotPosition.a][robotPosition.b - 1] == '.') {
                    grid[robotPosition.a][robotPosition.b - 1] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.b = robotPosition.b - 1;
                    
                } else moveObjects(robotPosition, grid, '<');
                
            } else if(current == '>') {
                if(grid[robotPosition.a][robotPosition.b + 1] == '.') {
                    grid[robotPosition.a][robotPosition.b + 1] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.b = robotPosition.b + 1;
                    
                } else moveObjects(robotPosition, grid, '>');
                
            } else if(current == 'v') {
                if(grid[robotPosition.a + 1][robotPosition.b] == '.') {
                    grid[robotPosition.a + 1][robotPosition.b] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.a = robotPosition.a + 1;
                    
                } else moveObjects(robotPosition, grid, 'v');
                
            } else if(current == '^') {
                if(grid[robotPosition.a - 1][robotPosition.b] == '.') {
                    grid[robotPosition.a - 1][robotPosition.b] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.a = robotPosition.a - 1;
                    
                } else moveObjects(robotPosition, grid, '^');   
            }
        }
        
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == 'O') total += (100 * i + j);
        
        return total;
    }
    
    private static long sumOfSecondBoxesGPS(String[] inputs, List<Character> movements) {
        long total = 0;
        char[][] grid = getPartTwoGrid(inputs);
        Pair robotPosition = getRobotCoordinates(grid);
        
        for(int i = 0; i < movements.size(); i++) {
            char current = movements.get(i);
            
            if(current == '<') {
                if(grid[robotPosition.a][robotPosition.b - 1] == '.') {
                    grid[robotPosition.a][robotPosition.b - 1] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.b = robotPosition.b - 1;
                    
                } else moveObjectsPartTwo(robotPosition, grid, '<');
                
            } else if(current == '>') {
                if(grid[robotPosition.a][robotPosition.b + 1] == '.') {
                    grid[robotPosition.a][robotPosition.b + 1] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.b = robotPosition.b + 1;
                    
                } else moveObjectsPartTwo(robotPosition, grid, '>');
                
            } else if(current == 'v') {
                if(grid[robotPosition.a + 1][robotPosition.b] == '.') {
                    grid[robotPosition.a + 1][robotPosition.b] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.a = robotPosition.a + 1;
                    
                } else moveObjectsPartTwo(robotPosition, grid, 'v');
                
            } else if(current == '^') {
                if(grid[robotPosition.a - 1][robotPosition.b] == '.') {
                    grid[robotPosition.a - 1][robotPosition.b] = '@';
                    grid[robotPosition.a][robotPosition.b] = '.';
                    robotPosition.a = robotPosition.a - 1;
                    
                } else moveObjectsPartTwo(robotPosition, grid, '^');
                
            }
            //printGrid(grid, current); // Doesn't work with actual input because the grid is waaaay too big to fit into the console reliably...
        }
        
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == '[') total += (100 * i + j);
        
        return total;
    }
    
    private static void moveObjects(Pair current, char grid[][], char direction) {
        if(direction == '<') {
            if(grid[current.a][current.b - 1] == '#') return;
            else {
                int row = current.a, col = current.b - 1;
                while(inRange(row, col, grid) && grid[row][col] == 'O') {
                    col--;
                }
                if(grid[row][col] == '#') return;
                
                grid[row][col] = 'O';
                grid[current.a][current.b - 1] = '@';
                grid[current.a][current.b] = '.';
                current.b = current.b - 1;
            }
        } else if(direction == '>') {
            if(grid[current.a][current.b + 1] == '#') return;
            else {
                int row = current.a, col = current.b + 1;
                while(inRange(row, col, grid) && grid[row][col] == 'O') {
                    col++;
                }
                if(grid[row][col] == '#') return;
                
                grid[row][col] = 'O';
                grid[current.a][current.b + 1] = '@';
                grid[current.a][current.b] = '.';
                current.b = current.b + 1;
            }
        } else if(direction == '^') {
            if(grid[current.a - 1][current.b] == '#') return;
            else {
                int row = current.a - 1, col = current.b;
                while(inRange(row, col, grid) && grid[row][col] == 'O') {
                    row--;
                }
                if(grid[row][col] == '#') return;
                
                grid[row][col] = 'O';
                grid[current.a - 1][current.b] = '@';
                grid[current.a][current.b] = '.';
                current.a = current.a - 1;
            }
        } else if(direction == 'v') {
            if(grid[current.a + 1][current.b] == '#') return;
            else {
                int row = current.a + 1, col = current.b;
                while(inRange(row, col, grid) && grid[row][col] == 'O') {
                    row++;
                }
                if(grid[row][col] == '#') return;
                
                grid[row][col] = 'O';
                grid[current.a + 1][current.b] = '@';
                grid[current.a][current.b] = '.';
                current.a = current.a + 1;
            }
        }
    }
    
    private static void moveObjectsPartTwo(Pair current, char grid[][], char direction) {
        if(direction == '<') {
            if(grid[current.a][current.b - 1] == '#') return;
            else {
                int row = current.a, col = current.b - 1;
                while(inRange(row, col, grid) && grid[row][col] == ']') {
                    col -= 2;
                }
                if(grid[row][col] == '#') return;
                
                for(int i = col; i < current.b - 1; i++) {
                    if(grid[row][i] == '.') grid[row][i] = '[';
                    else if(grid[row][i] == '[') grid[row][i] = ']';
                    else if(grid[row][i] == ']') grid[row][i] = '[';
                }
                
                grid[current.a][current.b - 1] = '@';
                grid[current.a][current.b] = '.';
                current.b = current.b - 1;
            }
        } else if(direction == '>') {
            if(grid[current.a][current.b + 1] == '#') return;
            else {
                int row = current.a, col = current.b + 1;
                while(inRange(row, col, grid) && grid[row][col] == '[') {
                    col += 2;
                }
                if(grid[row][col] == '#') return;
                
                for(int i = col; i > current.b + 1; i--) {
                    if(grid[row][i] == '.') grid[row][i] = ']';
                    else if(grid[row][i] == '[') grid[row][i] = ']';
                    else if(grid[row][i] == ']') grid[row][i] = '[';
                }
                
                grid[current.a][current.b + 1] = '@';
                grid[current.a][current.b] = '.';
                current.b = current.b + 1;
            }
        } else if(direction == '^') {
            if(grid[current.a - 1][current.b] == '#') return;
            else {
                InterPair interPair = new InterPair(new Pair(-1, -1), new Pair(-1, -1), '[', ']');
                if(grid[current.a - 1][current.b] == '[') interPair = new InterPair(new Pair(current.a - 1, current.b), new Pair(current.a - 1, current.b + 1), '[', ']');
                else if(grid[current.a - 1][current.b] == ']') interPair = new InterPair(new Pair(current.a - 1, current.b - 1), new Pair(current.a - 1, current.b), '[', ']');
                
                Set<InterPair> finalObjects = bfs(interPair, '^', grid);
                if(finalObjects.isEmpty()) return;
                
                List<InterPair> sortedObjects = new ArrayList<>();
                for(InterPair i : finalObjects) sortedObjects.add(i);
                sortList(sortedObjects);
                
                for(InterPair i : sortedObjects) {
                    grid[i.left.a][i.left.b] = '.';
                    grid[i.right.a][i.right.b] = '.';
                    
                    grid[i.left.a - 1][i.left.b] = i.leftObject;
                    grid[i.right.a - 1][i.right.b] = i.rightObject;
                }
                
                grid[current.a - 1][current.b] = '@';
                grid[current.a][current.b] = '.';
                current.a = current.a - 1;
            }
            
        } else if(direction == 'v') {
            if(grid[current.a + 1][current.b] == '#') return;
            else {
                InterPair interPair = new InterPair(new Pair(-1, -1), new Pair(-1, -1), '[', ']');
                if(grid[current.a + 1][current.b] == '[') interPair = new InterPair(new Pair(current.a + 1, current.b), new Pair(current.a + 1, current.b + 1), '[', ']');
                else if(grid[current.a + 1][current.b] == ']') interPair = new InterPair(new Pair(current.a + 1, current.b - 1), new Pair(current.a + 1, current.b), '[', ']');
                
                Set<InterPair> finalObjects = bfs(interPair, 'v', grid);
                if(finalObjects.isEmpty()) return;
                
                List<InterPair> sortedObjects = new ArrayList<>();
                for(InterPair i : finalObjects) sortedObjects.add(i);
                sortListReversed(sortedObjects);
                
                for(InterPair i : sortedObjects) {
                    grid[i.left.a][i.left.b] = '.';
                    grid[i.right.a][i.right.b] = '.';
                    
                    grid[i.left.a + 1][i.left.b] = i.leftObject;
                    grid[i.right.a + 1][i.right.b] = i.rightObject;
                }
                
                grid[current.a + 1][current.b] = '@';
                grid[current.a][current.b] = '.';
                current.a = current.a + 1;
            }
            
        }
    }
    
    private static void sortListReversed(List<InterPair> list) {
        int i, j;
        InterPair temp;
        boolean swapped;
        for (i = 0; i < list.size() - 1; i++) {
            swapped = false;
            for (j = 0; j < list.size() - i - 1; j++) {
                if(list.get(j).left.a < list.get(j + 1).left.a) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (swapped == false)
                break;
        }
    }
    
    private static void sortList(List<InterPair> list) {
        int i, j;
        InterPair temp;
        boolean swapped;
        for (i = 0; i < list.size() - 1; i++) {
            swapped = false;
            for (j = 0; j < list.size() - i - 1; j++) {
                if(list.get(j).left.a > list.get(j + 1).left.a) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (swapped == false)
                break;
        }
    }
    
    private static Set<InterPair> bfs(InterPair source, char direction, char[][] grid) {
        Queue<InterPair> queue = new LinkedList<>();
        Set<InterPair> visited = new HashSet<>();
        queue.add(source);
        
        while(!queue.isEmpty()) {
            InterPair current = queue.poll();
            visited.add(current);
            
            if(direction == '^') {
                Pair leftNorth = new Pair(current.left.a - 1, current.left.b);
                Pair rightNorth = new Pair(current.right.a - 1, current.right.b);
                
                if(grid[leftNorth.a][leftNorth.b] == '#' || grid[rightNorth.a][rightNorth.b] == '#')
                    return new HashSet<>();
                
                if(grid[leftNorth.a][leftNorth.b] == '.' && grid[rightNorth.a][rightNorth.b] == '.')
                    continue;
                
                if(inRange(leftNorth.a, leftNorth.b, grid) && !visitedContains(visited, leftNorth)) {
                    if(grid[leftNorth.a][leftNorth.b] == '[')
                        queue.add(new InterPair(leftNorth, new Pair(leftNorth.a, leftNorth.b + 1), '[', ']'));
                    else if(grid[leftNorth.a][leftNorth.b] == ']')
                        queue.add(new InterPair(new Pair(leftNorth.a, leftNorth.b - 1), leftNorth, '[', ']'));
                    
                } if(inRange(rightNorth.a, rightNorth.b, grid) && !visitedContains(visited, rightNorth)) {
                    if(grid[rightNorth.a][rightNorth.b] == '[')
                        queue.add(new InterPair(rightNorth, new Pair(rightNorth.a, rightNorth.b + 1), '[', ']'));
                    else if(grid[rightNorth.a][rightNorth.b] == ']')
                        queue.add(new InterPair(new Pair(rightNorth.a, rightNorth.b - 1), rightNorth, '[', ']'));
                }
            } else if(direction == 'v') {
                Pair leftSouth = new Pair(current.left.a + 1, current.left.b);
                Pair rightSouth = new Pair(current.right.a + 1, current.right.b);
                
                if(grid[leftSouth.a][leftSouth.b] == '#' || grid[rightSouth.a][rightSouth.b] == '#')
                    return new HashSet<>();
                
                if(grid[leftSouth.a][leftSouth.b] == '.' && grid[rightSouth.a][rightSouth.b] == '.')
                    continue;
                
                if(inRange(leftSouth.a, leftSouth.b, grid) && !visitedContains(visited, leftSouth)) {
                    if(grid[leftSouth.a][leftSouth.b] == '[')
                        queue.add(new InterPair(leftSouth, new Pair(leftSouth.a, leftSouth.b + 1), '[', ']'));
                    else if(grid[leftSouth.a][leftSouth.b] == ']')
                        queue.add(new InterPair(new Pair(leftSouth.a, leftSouth.b - 1), leftSouth, '[', ']'));
                    
                } if(inRange(rightSouth.a, rightSouth.b, grid) && !visitedContains(visited, rightSouth)) {
                    if(grid[rightSouth.a][rightSouth.b] == '[')
                        queue.add(new InterPair(rightSouth, new Pair(rightSouth.a, rightSouth.b + 1), '[', ']'));
                    else if(grid[rightSouth.a][rightSouth.b] == ']')
                        queue.add(new InterPair(new Pair(rightSouth.a, rightSouth.b - 1), rightSouth, '[', ']'));
                }
            
            }
        }
        return visited;
    }
    
    private static void printGrid(char[][] grid, char current) {
        System.out.print("\033[H");
        
        StringBuilder sb = new StringBuilder();
        System.out.println("Current Move: " + current);
        for (int r = 0; r < grid.length; r++) {
            for (int j = 0; j < grid[r].length; j++) {
                sb.append(grid[r][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean visitedContains(Set<InterPair> visited, Pair current) {
        for(InterPair i : visited) if(i.hasObject(current)) return true;
        return false;
    }
    
    private static boolean inRange(int row, int col, char[][] grid) {
        return ((row >= 0 && row < grid.length) && (col >= 0 && col < grid[row].length));
    }
    
    private static Pair getRobotCoordinates(char[][] grid) {
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == '@') return new Pair(i, j);
            
        return new Pair(-1, -1);
    }
    
    private static char[][] getPartTwoGrid(String[] inputs) {
        int i = 0;
        List<List<Character>> newGrid = new ArrayList<>();
        
        for(String input : inputs) {
            if(input.length() == 0) break;
            
            char[] temp = input.toCharArray();
            newGrid.add(new ArrayList<>());
            for(char j : temp) {
                if(j == '#') {
                    newGrid.get(i).add(j);
                    newGrid.get(i).add(j);
                } else if(j == 'O') {
                    newGrid.get(i).add('[');
                    newGrid.get(i).add(']');
                } else if(j == '@') {
                    newGrid.get(i).add('@');
                    newGrid.get(i).add('.');
                } else if(j == '.') {
                    newGrid.get(i).add(j);
                    newGrid.get(i).add(j);
                }
            }
            i++;
        }
        char[][] grid = new char[newGrid.size()][newGrid.get(0).size()];
        for(i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                grid[i][j] = newGrid.get(i).get(j);
        
        return grid;
    }
    
    private static List<Character> getInput(String[] inputs, char[][] grid, int gridHeight) {
        int i = 0;
        
        for(i = 0; i < gridHeight; i++) {
            char[] temp = inputs[i].toCharArray();
            for(int j = 0; j < grid[0].length; j++) {
                grid[i][j] = temp[j];
            }
        }
        
        StringBuilder sb = new StringBuilder();
        List<Character> movements = new ArrayList<>();
        for(i = i + 1; i < inputs.length; i++) {
            char[] temp = inputs[i].toCharArray();
            for(char j : temp) movements.add(j);
        }
        
        return movements;
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
            return "(" + this.a + ", " + this.b + ")";
        }
    }
    
    private static class InterPair {
        Pair left;
        Pair right;
        char leftObject;
        char rightObject;

        public InterPair(Pair a, Pair b, char l, char r) {
            this.left = a;
            this.right = b;
            this.leftObject = l;
            this.rightObject = r;
        }
        
        public boolean hasObject(Pair target) {
            return (target.equals(left) || target.equals(right));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InterPair that = (InterPair) o;
            return leftObject == that.leftObject && rightObject == that.rightObject && left.equals(that.left) && right.equals(that.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right, leftObject, rightObject);
        }
        
        @Override
        public String toString() {
            return "" + leftObject + "" + rightObject + ": " + this.left.toString() + "\t" + this.right.toString();
        }
    }
}