import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {
    private static Map<String, String> numbers = new HashMap<>();
    
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        
        initializeNumbers();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String[] inputs = lines.toArray(new String[0]);
        
        System.out.println(sumOfCalibrationValues(inputs));
        System.out.println(sumOfNumbers(inputs));
        
        
        //System.out.println(sumOfCalibrationValues(new String[] {"1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"}));
        //sumOfCalibrationValues(new String[] {"eight91oneighthreedgfnqjffkdxcmljvfrmh4"});
    }
    
    private static long sumOfNumbers(String[] inputs) {
        long sum = 0;
        for(String input : inputs) {
            int first = -1, last = -1;
            for(char i : input.toCharArray()) {
                int cur = (int) i;
                if(cur >= 48 && cur <= 57) {
                    first = Character.getNumericValue(i);
                    break;
                }
            }
            
            for(int i = input.length() - 1; i >= 0; i--) {
                int cur = (int) input.charAt(i);
                if(cur >= 48 && cur <= 57) {
                    last = Character.getNumericValue(input.charAt(i));
                    break;
                }
            }
            
            sum += ((first * 10) + last);
        }
        
        return sum;
    } 
    
    private static void initializeNumbers() {
        numbers.put("one", "o1e");
        numbers.put("two", "t2o");
        numbers.put("three", "t3e");
        numbers.put("four", "f4r");
        numbers.put("five", "f5e");
        numbers.put("six", "s6x");
        numbers.put("seven", "s7n");
        numbers.put("eight", "e8t");
        numbers.put("nine", "n9e");
    }
    
    private static long sumOfCalibrationValues(String[] inputs) {
        long sum = 0;
        StringBuilder sb;
        
        for(String input : inputs) {
            int first = -1, last = -1;
            sb = new StringBuilder();
            String current = input;
            
            for(char i : input.toCharArray()) {
                sb.append(i);
                String[] check = hasNumber(sb.toString());
                if(check[0].length() == 1) {
                    current = current.replaceFirst(check[1], check[2]);
                    sb = new StringBuilder();
                    sb.append(i);
                }
            }
            
            for(char i : current.toCharArray()) {
                int cur = (int) i;
                if(cur >= 48 && cur <= 57) {
                    first = Character.getNumericValue(i);
                    break;
                }
            }
            
            for(int i = current.length() - 1; i >= 0; i--) {
                int cur = (int) current.charAt(i);
                if(cur >= 48 && cur <= 57) {
                    last = Character.getNumericValue(current.charAt(i));
                    break;
                }
            }
            //System.out.println(Integer.toString((first * 10) + last));
            
            sum += ((first * 10) + last);
        }
        
        return sum;
    } 
    
    private static String[] hasNumber(String string) {
        for(Map.Entry<String, String> entry : numbers.entrySet()) {
            if(string.contains(entry.getKey())) {
                return new String[] {"1", entry.getKey(), entry.getValue()};
            }
        }
        
        return new String[] {"", "", ""};
    }
}