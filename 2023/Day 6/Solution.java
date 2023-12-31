import java.io.*;
import java.util.List;
import java.util.ArrayList;

class Solution {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        List<long[]> timeDistanceMapping = new ArrayList<>();
        long concatenatedTime = 0, concatenatedDistance = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] inputs = lines.toArray(new String[0]);
        
        String times = inputs[0];
        String distances = inputs[1];
        
        
        String[] timeValues = times.split("\\s+");
        String[] distanceValues = distances.split("\\s+");
        
        StringBuilder timeString = new StringBuilder();
        StringBuilder distanceString = new StringBuilder();
        
        for (int i = 1; i < timeValues.length; i++) {
            int time = Integer.parseInt(timeValues[i]);
            int distance = Integer.parseInt(distanceValues[i]);
            
            timeString.append(timeValues[i]);
            distanceString.append(distanceValues[i]);
            
            timeDistanceMapping.add(new long[] {time, distance});
        }
        
        
        concatenatedTime = Long.parseLong(timeString.toString());
        concatenatedDistance = Long.parseLong(distanceString.toString());
        
        //for(long[] entry : timeDistanceMapping)
        //    System.out.println(entry[0] + " -> " + entry[1]);
        
        System.out.println(marginOfError(timeDistanceMapping));
        System.out.println(marginOfErrorPartTwo(concatenatedTime, concatenatedDistance));
    }
    
    private static long marginOfError(List<long[]> timeDistanceMapping) {
        long total = 1;
        long time, distance;
        
        for(long[] input : timeDistanceMapping) {
            long totalTime = input[0], totalDistance = input[1];
            long count = 0;
            
            for(int hold = 0; hold <= totalTime; hold++) {
                time = totalTime - hold;
                distance = time * hold;
                
                if(distance > totalDistance) count++;
            }
            
            total *= count;
        }        
        
        return total;
    }
    
    private static long marginOfErrorPartTwo(long totalTime, long totalDistance) {
        long time, distance;
        
        long count = 0;
        
        for(int hold = 0; hold <= totalTime; hold++) {
            time = totalTime - hold;
            distance = time * hold;
            
            if(distance > totalDistance) count++;
        }
        
        return count;
    }
}