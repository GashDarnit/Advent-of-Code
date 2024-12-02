import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
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
        
        System.out.println("Part One: " + safeReportCount(inputs));
        System.out.println("Part Two: " + safeReportCountWithDampener(inputs));
    }
    
    private static int safeReportCount(String[] inputs) {
        int count = 0;
        for(String input : inputs) {
            String[] temp = input.split(" ");
            int[] values = new int[temp.length];
            
            for(int i = 0; i < values.length; i++) values[i] = Integer.parseInt(temp[i].trim());
            if(checkValidity(values)) {
                count++;
            }
        }
        
        return count;
    }
    
    private static int safeReportCountWithDampener(String[] inputs) {
        Set<int[]> validSet = new HashSet<>();
        int count = 0;
        for(String input : inputs) {
            String[] temp = input.split(" ");
            int[] values = new int[temp.length];
            boolean valid = false;
            
            for(int i = 0; i < values.length; i++) values[i] = Integer.parseInt(temp[i].trim());
            
            if(!validSet.contains(values)) {
                for(int i = 0; i < values.length; i++) {
                    List<Integer> skippedList = new ArrayList<>();
                    for(int j = 0; j < values.length; j++)
                        if(i != j) skippedList.add(values[j]);
                    
                    if(checkValidity(skippedList)) {
                        validSet.add(values);
                        break;
                    }
                }
            }
        }
        
        return validSet.size();
    }
    
    private static boolean checkValidity(int[] values) {
        int prev = -1, index = 0;
        boolean increase = false, decrease = false;
        
        for(int i : values) {
            if(prev == -1) {
                prev = i;
                index++;
                continue;
            }
            if(i > prev && (!increase && !decrease)) increase = true;
            else if(i < prev && (!increase && !decrease)) decrease = true;
            
            if((increase && i < prev) || (decrease && i > prev)) break;
            
            int difference = Math.abs(i - prev);
            if(difference > 3 || difference <= 0) break;
            
            prev = i;
            index++;
        }
        
        if(index == values.length) return true;
        return false;
    }
    
    private static boolean checkValidity(List<Integer> values) {
        int prev = -1, index = 0;
        boolean increase = false, decrease = false;
        
        for(int i : values) {
            if(prev == -1) {
                prev = i;
                index++;
                continue;
            }
            if(i > prev && (!increase && !decrease)) increase = true;
            else if(i < prev && (!increase && !decrease)) decrease = true;
            
            if((increase && i < prev) || (decrease && i > prev)) break;
            
            int difference = Math.abs(i - prev);
            if(difference > 3 || difference <= 0) break;
            
            prev = i;
            index++;
        }
        
        if(index == values.size()) return true;
        return false;
    }
}