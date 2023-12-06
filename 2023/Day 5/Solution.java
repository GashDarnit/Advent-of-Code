import java.io.*;
import java.util.*;

class Solution {
    private static List<Long> seeds = new ArrayList<>();
    private static List<long[]> mappingList = new ArrayList<>();
    private static Queue<long[]> seedRange = new LinkedList<>();
    private static int index = 3;
    
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
        String[] seedValues = inputs[0].split(":")[1].trim().split(" ");
        
        for (String seedValue : seedValues) {
            seeds.add(Long.parseLong(seedValue));
        }
        
        System.out.println("Part 1: " + getMinimumLocation(inputs));
        System.out.println("Part 2: " + getMinimumLocationPartTwo(inputs));
    }
    
    private static long getMinimumLocation(String[] inputs) {
        long[] arr = new long[seeds.size()];
        
        for(int i = 0; i < seeds.size(); i++) arr[i] = seeds.get(i);
        
        while(index < inputs.length) { //iterate through each mapping
            getNextMapping(inputs);
            
            int cur = 0;
            for(long info : arr) {
                for(long[] data : mappingList) {
                    if(info >= data[1] && info < data[1] + data[2]) {
                        arr[cur] =  data[0] + (info - data[1]);
                        break;
                    }
                }
                cur++;
            }
        }
        
        Arrays.sort(arr);
        return arr[0];
    }

    private static long getMinimumLocationPartTwo(String[] inputs) {
        index = 3;
        getSeeds();
        
        while(index < inputs.length) {
            getNextMapping(inputs);
            
            Queue<long[]> newSeeds = new LinkedList<>();
            while(seedRange.size() > 0) {
                long[] range = seedRange.poll();
                long start = range[0], end = range[1];
                
                boolean rangeMatched = false;
                
                for(long[] data : mappingList) {
                    long overlapStart = Math.max(start, data[1]);
                    long overlapEnd = Math.min(end, data[1] + data[2]);
                    
                    if(overlapStart < overlapEnd) {
                        newSeeds.add(new long[] {overlapStart - data[1] + data[0], overlapEnd - data[1] + data[0]});
                        
                        if(overlapStart > start)
                            seedRange.add(new long[] {start, overlapStart});
                        
                        if(end > overlapEnd)
                            seedRange.add(new long[] {overlapEnd, end});
                        
                        rangeMatched = true;
                        break;
                    }
                }
                
                if(!rangeMatched)
                    newSeeds.add(new long[] {start, end});
            }
            
            seedRange = newSeeds;
        }
        
        List<long[]> answer = new ArrayList<>();
        while(seedRange.size() > 0) {
            long[] values = seedRange.poll();
            answer.add(values);
        }
        
        Collections.sort(answer, (a, b) -> Long.compare(a[0], b[0]));
        
        return answer.get(0)[0];
    }
    
    private static void getSeeds() {
        for(int i = 0; i < seeds.size(); i += 2) {
            seedRange.add(new long[] {seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1});
        }
    }
   
    private static void getNextMapping(String[] inputs) {
        mappingList.clear();
        
        while( index < inputs.length && !(inputs[index].equals("")) ) {
            String[] values = inputs[index].trim().split(" ");
            
            long[] mapping = new long[values.length];
            for (int i = 0; i < values.length; i++)
                mapping[i] = Long.parseLong(values[i]);
            
            mappingList.add(mapping);
            index++;
        }
        
        index += 2;
    }
}