import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
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
        List<Robot> robots = getRobots(inputs);
        
        System.out.println("Part One: " + safetyFactorAfter100Seconds(robots, 101, 103));
        safetyRobotsChrimstmasTree(robots, 101, 103, inputs);
    }
    
    private static long safetyFactorAfter100Seconds(List<Robot> robots, int width, int height) {
        long total = 1;
        int[][] grid = new int[height][width];
        int[] quadrant = new int[4];
        
        for(Robot robot : robots) {
            robot.position = new Pair(((robot.position.a + robot.velX * 100) % width + width) % width, ((robot.position.b + robot.velY * 100) % height + height) % height); //weird ahh true modulo
            grid[robot.position.b][robot.position.a]++;
        }
        
        int verticalMidpoint = width / 2;
        int horizontalMidpoint = height / 2;
        
        //Upper left quadrant
        for(int i = 0; i < horizontalMidpoint; i++)
            for(int j = 0; j < verticalMidpoint; j++)
                quadrant[0] += grid[i][j];
        
        //Upper right quadrant
        for(int i = 0; i < horizontalMidpoint; i++)
            for(int j = verticalMidpoint + 1; j < width; j++)
                quadrant[1] += grid[i][j];
        
        //Bottom left quadrant
        for(int i = horizontalMidpoint + 1; i < height; i++)
            for(int j = 0; j < verticalMidpoint; j++)
                quadrant[2] += grid[i][j];
        
        //Bottom right quadrant
        for(int i = horizontalMidpoint + 1; i < height; i++)
            for(int j = verticalMidpoint + 1; j < width; j++)
                quadrant[3] += grid[i][j];
        
        return quadrant[0] * quadrant[1] * quadrant[2] * quadrant[3];
    }
    
    private static void safetyRobotsChrimstmasTree(List<Robot> robots, int width, int height, String[] inputs) {
        char[][] grid = new char[height][width];
        
        for(int second = 6500; second <= 8000; second++) {
            for(Robot robot : robots) {
                robot.position = new Pair(((robot.position.a + robot.velX * second) % width + width) % width, ((robot.position.b + robot.velY * second) % height + height) % height); //weird ahh true modulo
                grid[robot.position.b][robot.position.a] = '#';
            }
            
            // Keep your eyes peeled
            robots = getRobots(inputs);
            System.out.println("\n\n=========================================================================================================");
            printGrid(grid, width, height);
            System.out.println("Seconds: " + second);
            System.out.println("=========================================================================================================\n\n\n\n\n\n");
            
            grid = new char[height][width];
            for(int i = 0; i < height; i++)
                for(int j = 0; j < width; j++)
                    grid[i][j] = ' ';
        }
    }
    
    private static void printGrid(char[][] grid, int width, int height) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++)
                sb.append(grid[i][j]);
            System.out.println(sb.toString());
            sb = new StringBuilder();
        }
    }
    
    private static List<Robot> getRobots(String[] inputs) {
        List<Robot> robots = new ArrayList<>();
        for(String i : inputs) {
            String[] temp = i.trim().split(" ");
            String[] robotPos = temp[0].split("=")[1].split(",");
            String[] robotVel = temp[1].split("=")[1].split(",");
            robots.add(new Robot(new Pair(Integer.parseInt(robotPos[0]), Integer.parseInt(robotPos[1])), Integer.parseInt(robotVel[0]), Integer.parseInt(robotVel[1])));
        }
        
        return robots;
    }
    
    private static class Robot {
        Pair position;
        int velX;
        int velY;
        
        public Robot(Pair position, int velX, int velY) {
            this.position = position;
            this.velX = velX;
            this.velY = velY;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Robot that = (Robot) o;
            return position.equals(that.position) && velX == that.velX && velY == that.velY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, velX, velY);
        }
        
        @Override
        public String toString() {
            return "Position: " + position.toString() + "\tVelocity: (" + this.velX + ", " + this.velY + ")";
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
            return "(" + this.a + ", " + this.b + ")";
        }
    }
}