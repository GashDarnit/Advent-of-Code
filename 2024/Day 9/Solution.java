import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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
        System.out.println("Part One: " + compressedChecksum(inputs[0]));
        System.out.println("Part Two: " + compressedByFilesChecksum(inputs[0]));
    }
    
    private static long compressedChecksum(String input) {
        long checksum = 0;
        int current = 0;
        int index = 0;
        List<Integer> diskArrangement = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            for(int j = 0; j < Character.getNumericValue(input.charAt(i)); j++) {
                if(current == 0) {
                    diskArrangement.add(index);
                }
                else diskArrangement.add(-1);
            }
            
            if(current == 0) {
                current = 1;
                index++;
            } else current = 0;
        }
        
        
        current = -1;
        while(true) {
            if(!containsSpace(diskArrangement)) break;
            
            if(diskArrangement.get(diskArrangement.size() - 1) == -1) {
                diskArrangement.remove(diskArrangement.size() - 1);
                continue;
            }
            
            for(int i = 0; i < diskArrangement.size(); i++) {
                if(diskArrangement.get(i) == -1) {
                    current = i;
                    break;
                }
            }
            if(current != -1) {
                int temp = diskArrangement.get(diskArrangement.size() - 1);
                diskArrangement.remove(diskArrangement.size() - 1);
                diskArrangement.set(current, temp);
            }
            current = -1;
        }
        
        
        for(int i = 0; i < diskArrangement.size(); i++)
            checksum += (diskArrangement.get(i) * i);
        
        return checksum;
    }
    
    private static long compressedByFilesChecksum(String input) {
        long checksum = 0;
        int current = 0;
        int index = 0;
        List<Integer> diskArrangement = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            for(int j = 0; j < Character.getNumericValue(input.charAt(i)); j++) {
                if(current == 0) diskArrangement.add(index);
                else diskArrangement.add(-1);
            }
            
            if(current == 0) {
                current = 1;
                index++;
            } else current = 0;
        }
        
        int limit = diskArrangement.size();
        while(limit > 0) {
            List<int[]> availableSlots = new ArrayList<>();
            int[] lastFile = new int[3];
            getLastFile(lastFile, diskArrangement, limit);
            limit = lastFile[2];
            getAvailableSlots(availableSlots, diskArrangement, limit);
            
            for(int[] spot : availableSlots) {
                if(spot[1] >= lastFile[1]) {
                    for(int i = 0; i < lastFile[1]; i++) {
                        diskArrangement.set(spot[0] + i, lastFile[0]);
                        diskArrangement.set(lastFile[2] + i, -1);
                    }
                    break;
                }
            }
        }
        for(int i = 0; i < diskArrangement.size(); i++)
            if(diskArrangement.get(i) != -1)
                checksum += (diskArrangement.get(i) * i);
        
        return checksum;
    }
    
    private static void getAvailableSlots(List<int[]> availableSlots, List<Integer> diskArrangement, int limit) { 
        int index = -1, count = 0;
        boolean start = false;
        for(int i = 0; i <= limit; i++) {
            if(diskArrangement.get(i) == -1 && !start) {
                count++;
                index = i;
                start = true;
                continue;
            }
            if(diskArrangement.get(i) == -1 && start) {
                count++;
                continue;
            }
            if(diskArrangement.get(i) != -1 && start) {
                availableSlots.add(new int[] {index, count});
                count = 0;
                index = -1;
                start = false;
            }
        }
    }
    
    private static void getLastFile(int[] lastFile, List<Integer> diskArrangement, int limit) {
        int index = -1, count = 0;
        boolean start = false;
        for(int i = limit - 1; i >= 0; i--) {
            if(diskArrangement.get(i) != -1 && !start) {
                lastFile[0] = diskArrangement.get(i);
                count++;
                start = true;
                continue;
            }
            if(diskArrangement.get(i) == lastFile[0] && start) {
                count++;
                continue;
            } else if(diskArrangement.get(i) != lastFile[0] && start) {
                lastFile[1] = count;
                lastFile[2] = i + 1;
                break;
            }
        }
    }
    
    private static boolean containsSpace(List<Integer> diskArrangement) {
        for(int i : diskArrangement) if(i == -1) return true;
        return false;
    }

}