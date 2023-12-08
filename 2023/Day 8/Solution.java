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
        System.out.println("Part 2: " + stepsForAllToReachTarget(instructions, locationMapping));
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
    
    private static long stepsForAllToReachTarget(String moves, Map<String, String[]> locationMapping) {
        List<String> currentPositions = new ArrayList();
        for(String location : locationMapping.keySet())
            if(location.charAt(2) == 'A')
                currentPositions.add(location);
        
        List<List<Long>> cycles = new ArrayList<>();
        
        for (String current : currentPositions) {
            List<Long> visited = new ArrayList<>();

            String currentSteps = moves;
            long stepCount = 0;
            String firstZ = null;

            while (true) {
                while (stepCount == 0 || current.charAt(2) != 'Z') {
                    stepCount++;
                    String[] positions = locationMapping.get(current);
                    
                    if(currentSteps.charAt(0) == 'L') current = positions[0];
                    else current = positions[1];
                    
                    currentSteps = currentSteps.substring(1) + currentSteps.charAt(0); //shift first character to the end
                }

                visited.add(stepCount);

                if (firstZ == null) {
                    firstZ = current;
                    stepCount = 0;
                } else if (current.equals(firstZ))
                    break;
            }

            cycles.add(visited);
        }
        
        long answer = cycles.get(0).get(0);
        for(int i = 1; i < cycles.size(); i++)
            answer = answer * cycles.get(i).get(0) / greatestCommonDivisor(answer, cycles.get(i).get(0));
        
        return answer;
    }
    
    private static long greatestCommonDivisor(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static void parseAndStoreInput(String input, Map<String, String[]> map) {
        String[] parts = input.split(" = ");
        String key = parts[0];
        String valuesString = parts[1].replaceAll("[()]", "");
        String[] values = valuesString.split(", ");
        
        map.put(key, new String[] {values[0], values[1]});
    }
}