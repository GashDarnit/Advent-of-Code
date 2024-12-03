import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

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
        
        System.out.println("Part One: " + sumOfMultiplications(inputs));
        System.out.println("Part Two: " + sumOfConditionalMultiplications(inputs));
    }
    
    private static long sumOfMultiplications(String[] inputs) {
        StringBuilder sb;
        StringBuilder potentialMul;
        long sum = 0;
        
        for(String input : inputs) {
            sb = new StringBuilder();
            potentialMul = new StringBuilder();
            char[] inputArray = input.toCharArray();
            boolean potential = false;
            
            for(char i : inputArray) {
                sb.append(i);
                if(sb.toString().contains("mul(")) {
                    potential = true;
                    sb = new StringBuilder();
                    potentialMul = new StringBuilder();
                    continue;
                }
                
                if(potential && i != ')') {
                    potentialMul.append(i);
                } else if(potential && i == ')') {
                    String[] temp = potentialMul.toString().split(",");
                    boolean valid = true;
                    
                    if(temp.length != 2) {
                        valid = false;
                        potential = false;
                        sb = new StringBuilder();
                        potentialMul = new StringBuilder();
                        continue;
                    }
                    
                    if(!validateInput(temp[0].trim().toCharArray()) || !validateInput(temp[1].trim().toCharArray())) {
                        valid = false;
                    }
                    
                    if(valid) {
                        int valueOne = Integer.parseInt(temp[0]);
                        int valueTwo = Integer.parseInt(temp[1]);
                        
                        if((valueOne >= 1 && valueOne <= 999) && (valueTwo >= 1 && valueTwo <= 999)) sum += (valueOne * valueTwo);
                    }
                    
                    potential = false;
                    sb = new StringBuilder();
                    potentialMul = new StringBuilder();
                }   
            }
        }
        
        return sum;
    }
    
    private static long sumOfConditionalMultiplications(String[] inputs) {
        StringBuilder sb;
        StringBuilder potentialMul;
        long sum = 0;
        boolean toggl = true;
        
        for(String input : inputs) {
            sb = new StringBuilder();
            potentialMul = new StringBuilder();
            char[] inputArray = input.toCharArray();
            boolean potential = false;
            
            for(char i : inputArray) {
                sb.append(i);
                
                if(sb.toString().contains("do()")) {
                    toggl = true;
                    sb = new StringBuilder();
                    potentialMul = new StringBuilder();
                    continue;
                    
                } else if(sb.toString().contains("don't()")) {
                    toggl = false;
                    sb = new StringBuilder();
                    potentialMul = new StringBuilder();
                    
                    continue;
                }
                
                if(toggl) {
                    if(sb.toString().contains("mul(")) {
                        potential = true;
                        sb = new StringBuilder();
                        potentialMul = new StringBuilder();
                        continue;
                    }
                    
                    if(potential && i != ')') {
                        potentialMul.append(i);
                    } else if(potential && i == ')') {
                        String[] temp = potentialMul.toString().split(",");
                        boolean valid = true;
                        
                        if(temp.length != 2) {
                            valid = false;
                            potential = false;
                            sb = new StringBuilder();
                            potentialMul = new StringBuilder();
                            continue;
                        }
                        
                        if(!validateInput(temp[0].trim().toCharArray()) || !validateInput(temp[1].trim().toCharArray())) {
                            valid = false;
                        }
                        
                        if(valid) {
                            int valueOne = Integer.parseInt(temp[0]);
                            int valueTwo = Integer.parseInt(temp[1]);
                            
                            if((valueOne >= 1 && valueOne <= 999) && (valueTwo >= 1 && valueTwo <= 999)) sum += (valueOne * valueTwo);
                        }
                        
                        potential = false;
                        sb = new StringBuilder();
                        potentialMul = new StringBuilder();
                    }   
                    
                }
            }
        }
        
        return sum;
    }
    
    private static boolean isDigit(char i) {
        return (i >= '0' && i <= '9');
    }
    
    private static boolean validateInput(char[] input) {
        for(char i : input) 
            if(!isDigit(i)) return false;
        
        return true;
    }
}