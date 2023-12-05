import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {
    private static Map<String, Integer> map = new HashMap<>();
    
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        
        initializeMap();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String[] temp = lines.toArray(new String[0]);
        String[] inputs = new String[temp.length];
        int index = 0;
        
        for(String i : temp) {
            inputs[index] = i.split(": ")[1];
            index++;
        }
        
        System.out.println("Sum of Games: " + sumOfGames(inputs));
        System.out.println("Sum of Multiple of Games: " + sumOfMultipleOfGames(inputs));
        //sumOfGames(inputs);
        //sumOfMultipleOfGames(inputs);
    }
    
    private static void initializeMap() {
        map.put("red", 12);
        map.put("green", 13);
        map.put("blue", 14);
    }
    
    private static long sumOfGames(String[] inputs) {
        long sum = 0;
        StringBuilder sb, value;
        boolean flag;
        int index = 1;
        
        for(String input : inputs) {
            sb = new StringBuilder();
            value = new StringBuilder();
            flag = true;
            
            for(char i : input.toCharArray()) {
                int cur = (int) i;
                
                if(cur >= 97 && cur <= 122) {
                    sb.append(i);
                }
                
                if(cur >= 48 && cur <= 57) {
                    value.append(i);
                }
                
                if(map.containsKey(sb.toString())) {
                    //System.out.println("Game " + index + ": " + value.toString() + " " + sb.toString());
                    
                    if(Integer.parseInt(value.toString()) > map.get(sb.toString()))
                        flag = false;
                    
                    sb = new StringBuilder();
                    value = new StringBuilder();
                }
            }
            
            if(flag)
                sum += index;
            
            index++;
        }
        
        return sum;
    }
    
    private static long sumOfMultipleOfGames(String[] inputs) {
        Map<String, Integer> min = new HashMap<>();
        
        long sum = 0;
        StringBuilder sb, value;
        
        for(String input : inputs) {
            sb = new StringBuilder();
            value = new StringBuilder();
            
            min.put("red", -1);
            min.put("green", -1);
            min.put("blue", -1);
        
            for(char i : input.toCharArray()) {
                int cur = (int) i;
                
                if(cur >= 97 && cur <= 122) {
                    sb.append(i);
                }
                
                if(cur >= 48 && cur <= 57) {
                    value.append(i);
                }
                
                if(min.containsKey(sb.toString())) {
                    int val = Integer.parseInt(value.toString());
                    if(val > min.get(sb.toString())) {
                        min.put(sb.toString(), val);
                    }
                    
                    sb = new StringBuilder();
                    value = new StringBuilder();
                }
            }
            
            sum += (min.get("red") * min.get("green") * min.get("blue"));
        }
        
        return sum;
    }
}