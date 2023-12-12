import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Collections;

class Solution {
    static Map<Tuple, Long> memoize = new HashMap<>();
    
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
        
        System.out.println("Part One: " + calculateCombinations(inputs));
        System.out.println("Part Two: " + calculateCombinationsPartTwo(inputs));
    }
    
    private static long calculateCombinations(String[] inputs) {
        long total = 0;
        for(String input : inputs) {
            String temp[] = input.split(" ");
            String configuration = temp[0];
            String[] tempGroup = temp[1].split(",");
            
            int[] damagedGroups = new int[tempGroup.length];
            for (int i = 0; i < tempGroup.length; i++) {
                damagedGroups[i] = Integer.parseInt(tempGroup[i]);
            }
            total += getResult(configuration, damagedGroups);
        }
        return total;
    }
    
    private static long calculateCombinationsPartTwo(String[] inputs) {
        long total = 0;
        
        for(String input : inputs) {
            String temp[] = input.split(" ");
            
            String configuration = temp[0];
            configuration = String.join("?", Collections.nCopies(5, configuration));
            
            
            String[] tempGroup = temp[1].split(",");
            int[] tempDamagedGroups = new int[tempGroup.length];
            for (int i = 0; i < tempGroup.length; i++)
                tempDamagedGroups[i] = Integer.parseInt(tempGroup[i]);
            
            int[] damagedGroups = new int[tempDamagedGroups.length * 5];
            for (int i = 0; i < 5; i++)
                System.arraycopy(tempDamagedGroups, 0, damagedGroups, i * tempDamagedGroups.length, tempDamagedGroups.length);
            
            total += getResultPartTwo(configuration, damagedGroups);
        }
        return total;
    }
    
    
    private static long getResult(String configuration, int[] damagedGroups) {
        if(configuration.equals("")) {
            return (damagedGroups.length == 0) ? 1: 0;
        }
        
        if(damagedGroups.length == 0) {
            return (configuration.contains("#")) ? 0 : 1;
        }
        
        long result = 0;
        if(".?".contains(Character.toString(configuration.charAt(0)))) {
            result += getResult(configuration.substring(1), sliceArray(damagedGroups, 0));
        }
        
        if("#?".contains(Character.toString(configuration.charAt(0)))) {
            if(damagedGroups[0] <= configuration.length() && !(configuration.substring(0, damagedGroups[0]).contains(".")) && (damagedGroups[0] == configuration.length() || configuration.charAt(damagedGroups[0]) != '#')) {
                String subString = "";
                if(damagedGroups[0] + 1 < configuration.length())
                    subString = configuration.substring(damagedGroups[0] + 1);
                
                result += getResult(subString, sliceArray(damagedGroups, 1));
            }
        }
        
        return result;
    }
    
    private static long getResultPartTwo(String configuration, int[] damagedGroups) {
        if(configuration.equals("")) {
            return (damagedGroups.length == 0) ? 1: 0;
        }
        
        if(damagedGroups.length == 0) {
            return (configuration.contains("#")) ? 0 : 1;
        }
        
        Tuple key = new Tuple(configuration, damagedGroups);
        if(memoize.containsKey(key)) {
            return memoize.get(key);
        }
        
        long result = 0;
        if(".?".contains(Character.toString(configuration.charAt(0)))) {
            result += getResultPartTwo(configuration.substring(1), damagedGroups);
        }
        
        if("#?".contains(Character.toString(configuration.charAt(0)))) {
            if(damagedGroups[0] <= configuration.length() && !(configuration.substring(0, damagedGroups[0]).contains(".")) && (damagedGroups[0] == configuration.length() || configuration.charAt(damagedGroups[0]) != '#')) {
                String subString = "";
                if(damagedGroups[0] + 1 < configuration.length())
                    subString = configuration.substring(damagedGroups[0] + 1);
                
                result += getResultPartTwo(subString, sliceArray(damagedGroups, 1));
            }
        }
        
        memoize.put(key, result);
        return result;
    }
    
    private static int[] sliceArray(int[] damagedGroups, int sliceAmount) {
        int[] newArray = new int[damagedGroups.length - sliceAmount];
        for(int i = 0; i < newArray.length; i++)
            newArray[i] = damagedGroups[i + sliceAmount];
        return newArray;
    }
    
    static class Tuple {
        String configuration;
        int[] damagedGroups;
        
        public Tuple() {}
        public Tuple(String configuration, int[] damagedGroups) {
            this.configuration = configuration;
            this.damagedGroups = damagedGroups;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple that = (Tuple) o;
            return configuration.equals(that.configuration) && Arrays.equals(damagedGroups, that.damagedGroups);
        }

        
        private boolean checkArray(int[] arrOne, int[] arrTwo) {
            return Arrays.equals(arrOne, arrTwo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(configuration, Arrays.hashCode(damagedGroups));
        }
    }
}