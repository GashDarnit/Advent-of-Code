import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
        
        System.out.println("Part One: " + getArea(inputs));
        System.out.println("Part Two: " + getAreaPartTwo(inputs));
    }
    
    public static long getArea(String[] inputs) {
        long totalArea = 0;
        long internal = 0;
        Map<Character, int[]> directions = new HashMap<>();
        List<int[]> points = new ArrayList<>();
        initializeMap(directions);
        long perimeter = 0;
        
        points.add(new int[] {0, 0});
        
        for(String input : inputs) {
            String[] temp = input.split(" ");
            
            char direction = temp[0].charAt(0);
            int steps = Integer.parseInt(temp[1]);
            int[] dir = directions.get(direction);
            int[] coords = points.get(points.size() - 1);
            
            perimeter += steps;
            points.add(new int[] {coords[0] + dir[0] * steps, coords[1] + dir[1] * steps});
        }
        totalArea = (Math.abs(sum(points) / 2)) + (perimeter / 2) + 1;
        return totalArea;
    }
    
    public static long getAreaPartTwo(String[] inputs) {
        long totalArea = 0;
        long internal = 0;
        Map<Character, int[]> directions = new HashMap<>();
        Map<Integer, Character> directionMap = new HashMap<>();
        List<int[]> points = new ArrayList<>();
        initializeMap(directions);
        initializeDirectionMap(directionMap);
        long perimeter = 0;
        
        points.add(new int[] {0, 0});
        
        for(String input : inputs) {
            String temp = input.split(" ")[2];
            
            String hex = temp.substring(2, temp.length() - 1);
            int lastChar = Character.getNumericValue(hex.charAt(hex.length() - 1));
            int[] dir = directions.get(directionMap.get(lastChar));
            int steps = Integer.parseInt(hex.substring(0, hex.length() - 1), 16);
            int[] coords = points.get(points.size() - 1);
            
            perimeter += steps;
            points.add(new int[] {coords[0] + dir[0] * steps, coords[1] + dir[1] * steps});
        }
        totalArea = (Math.abs(sum(points) / 2)) + (perimeter / 2) + 1;
        return totalArea;
    }
    
    public static long sum(List<int[]> points) {
        int n = points.size();
        long totalSum = 0;
        
        for(int i = 0; i < n; i++) {
            int prevIndex = (i - 1 + n) % n;
            int nextIndex = (i + 1) % n;
            long contribution = ((long) points.get(i)[0]) * (points.get(prevIndex)[1] - points.get(nextIndex)[1]);
            
            totalSum += contribution;
        }
        return totalSum;
    }
    
    private static void initializeMap(Map<Character, int[]> map) {
        map.put('U', new int[] {-1, 0});
        map.put('D', new int[] {1, 0});
        map.put('L', new int[] {0, -1});
        map.put('R', new int[] {0, 1});
    }
    
    private static void initializeDirectionMap(Map<Integer, Character> map) {
        map.put(0, 'R');
        map.put(1, 'D');
        map.put(2, 'L');
        map.put(3, 'U');
    }
}