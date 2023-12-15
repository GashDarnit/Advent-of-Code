import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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

        String[] temp = lines.toArray(new String[0]);
        String[] inputs = temp[0].split(",");
        
        System.out.println("Part One: " + sumOfHashes(inputs));
        System.out.println("Part Two: " + focusingPowerOfLensConfiguration(inputs));
    }
    
    public static long sumOfHashes(String[] inputs) {
        long total = 0;
        for(String input : inputs) {
            long currentVal = 0;
            total += hash(input);
        }
        
        return total;
    }
    
    public static long focusingPowerOfLensConfiguration(String[] inputs) {
        long total = 0;
        List<List<String>> boxes = new ArrayList<>();
        Map<String, Integer> focalLengths = new HashMap<>();
        
        for(int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }
        
        for(String input : inputs) {
            if(input.contains("-")) {
                String label = input.substring(0, input.length() - 1);
                int index = hash(label);
                
                if(boxes.get(index).size() > 0 && boxes.get(index).contains(label)) {
                    boxes.get(index).remove(label);
                }
            } else {
                String[] temp = input.split("=");
                int index = hash(temp[0]);
                
                if(!(boxes.get(index).contains(temp[0]))) {
                    boxes.get(index).add(temp[0]);
                }
                
                focalLengths.put(temp[0], Integer.parseInt(temp[1]));
            }
        }
        
        int boxIndex = 0;
        for(List<String> box : boxes) {
            int lensSlot = 0;
            for(String label : box) {
                total += (boxIndex + 1) * (lensSlot + 1) * focalLengths.get(label);
                lensSlot++;
            }
            
            boxIndex++;
        }
        
        return total;
    }
    
    public static int hash(String cur) {
        int curVal = 0;
        for(char i : cur.toCharArray()) {
            curVal += (int) i;
            curVal *= 17;
            curVal %= 256;
        }
        return curVal;
    }
}