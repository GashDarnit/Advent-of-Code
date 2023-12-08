import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public static void main(String[] args) {
        Map<String, String[]> locationMapping = new HashMap<>();
        
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
        String instructions = inputs[0];
        
        for(int i = 2; i < inputs.length; i++)
            parseAndStoreInput(inputs[i], locationMapping);
        
        System.out.println("Part 1: " + stepsToReachTarget(instructions, locationMapping));
    }
    
    private static long stepsToReachTarget(String moves, Map<String, String[]> locationMapping) {
        long steps = 0;
        char[] moveSequence = moves.toCharArray();
        char currentInstruction = '.';
        String currentLocation = "AAA";
        
        for(int i = 0; i < moveSequence.length; i++) {
            currentInstruction = moveSequence[i];
            String[] positions = locationMapping.get(currentLocation);
            
            if(currentInstruction == 'L')
                currentLocation = positions[0];
            else if(currentInstruction == 'R')
                currentLocation = positions[1];
            
            steps++;
            
            if(currentLocation.equals("ZZZ"))
                break;
            
            if(i == moveSequence.length - 1) // loop it back
                i = -1;
        }
        
        return steps;
    }
    
    private static void parseAndStoreInput(String input, Map<String, String[]> map) {
        String[] parts = input.split(" = ");
        String key = parts[0];
        String valuesString = parts[1].replaceAll("[()]", "");
        String[] values = valuesString.split(", ");
        
        map.put(key, new String[] {values[0], values[1]});
    }
}