import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
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
        
        System.out.println("Part One: " + sumOfDistance(inputs));
        System.out.println("Part Two: " + sumOfSimilarity(inputs));
    }
    
    private static long sumOfDistance(String[] inputs) {
        long sum = 0;
        long[] leftValues = new long[inputs.length];
        long[] rightValues = new long[inputs.length];
        String[] temp;
        
        
        int index = 0;
        
        for(String i : inputs) {
            temp = i.split("   ");
            leftValues[index] = Long.parseLong(temp[0]);
            rightValues[index] = Long.parseLong(temp[1]);
            
            index++;
        }
        
        Arrays.sort(leftValues);
        Arrays.sort(rightValues);
        
        for(int i = 0; i < leftValues.length; i++) sum += Math.abs(leftValues[i] - rightValues[i]);
        
        return sum;
    }
    
    private static long sumOfSimilarity(String[] inputs) {
        long sum = 0;
        Map<Long, Integer> map = new HashMap<>();
        long[] leftValues = new long[inputs.length];
        long[] rightValues = new long[inputs.length];
        String[] temp;
        int index = 0;
        
        for(String i : inputs) {
            temp = i.split("   ");
            leftValues[index] = Long.parseLong(temp[0]);
            rightValues[index] = Long.parseLong(temp[1]);
            
            index++;
        }
        
        for(long i : leftValues) map.put(i, 0);
        for(long i  : rightValues) if(map.containsKey(i)) map.put(i, map.get(i) + 1);
        
        for(Map.Entry<Long, Integer> entry : map.entrySet()) sum += (entry.getKey() * entry.getValue());
        
        return sum;
    }
}