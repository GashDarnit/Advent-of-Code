import java.io.*;
import java.util.List;
import java.util.ArrayList;
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
        
        String[] tempInputs = lines.toArray(new String[0]);
        String[] inputs = new String[tempInputs.length];
        
        int index = 0;
        for(String i : tempInputs) {
            String[] temp = i.split(": ");
            inputs[index] = temp[1];
            index++;
        }
        
        System.out.println("Part 1: " + totalWinningPoints(inputs));
    }
    
    private static long totalWinningPoints(String[] inputs) {
        long sum = 0;
        
        for(String i : inputs) {
            long current = 1;
            HashSet<Integer> winningNumbers = new HashSet<>();
            List<Integer> numbersList = new ArrayList<>();
            List<Integer> wins = new ArrayList<>();
        
            String[] parts = i.split("\\|");
            String leftPart = parts[0].trim();
            String rightPart = parts[1].trim();
            
            String[] leftValues = leftPart.split("\\s+");
            for (String value : leftValues) {
                winningNumbers.add(Integer.parseInt(value));
            }
            
            String[] rightValues = rightPart.split("\\s+");
            for (String value : rightValues) {
                numbersList.add(Integer.parseInt(value));
            }
            
            for(int num : numbersList) {
                if(winningNumbers.contains(num))
                    wins.add(num);
            }
            
            sum += Math.pow(2, wins.size() - 1);
        }
        
        return sum;
    }
}