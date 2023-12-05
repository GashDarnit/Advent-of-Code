import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class Solution {
    private static List<Long> seeds = new ArrayList<>();
    private static List<long[]> mappingList = new ArrayList<>();
    private static int index = 3;
    
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
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
        
        /*
        for (int i = 0; i < mappingList.size(); i++)
            System.out.println("Mapping " + i + ": " + java.util.Arrays.toString(mappingList.get(i)));
        */
        
        System.out.println(getMinimumLocation(inputs));
        
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