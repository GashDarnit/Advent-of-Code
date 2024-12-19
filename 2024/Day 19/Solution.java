import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
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

        String[] inputs = lines.toArray(new String[0]);
        Set<String> available = new HashSet<>();
        List<String> patterns = new ArrayList<>();
        for(String i : inputs[0].split(", ")) available.add(i.trim());
        for(int i = 2; i < inputs.length; i++) patterns.add(inputs[i]);
        
        System.out.println("Part One: " + getNumberOfAvailablePatterns(available, patterns, new HashMap<>()));
        System.out.println("Part Two: " + sumOfDifferentWaysForPatterns(available, patterns, new HashMap<>()));
    }
    
    private static long getNumberOfAvailablePatterns(Set<String> available, List<String> patterns, Map<String, Integer> cache) {
        long total = 0;
        for(String pattern : patterns) 
            if(obtainable(pattern, available, cache) != 0) 
                total++;        
        
        return total;
    }
    
    private static long sumOfDifferentWaysForPatterns(Set<String> available, List<String> patterns, Map<String, Long> cache) {
        long total = 0;
        for(String pattern : patterns) total += obtainableModified(pattern, available, cache);
        return total;
    }
    
    private static int obtainable(String current, Set<String> available, Map<String, Integer> cache) {
        if(cache.containsKey(current)) return cache.get(current);
        if(current.length() == 0) return 1;
        for(int i = 0; i <= current.length(); i++) {
            String before = current.substring(0, i);
            String after = current.substring(i, current.length());
            if(available.contains(before) && obtainable(after, available, cache) != 0) {
                cache.put(current, 1);
                return 1;
            }
        }
        
        cache.put(current, 0);
        return 0;
    }
    
    private static long obtainableModified(String current, Set<String> available, Map<String, Long> cache) {
        long count = 0;
        if(cache.containsKey(current)) return cache.get(current);
        if(current.length() == 0) return 1;
        
        for(int i = 0; i <= current.length(); i++) {
            String before = current.substring(0, i);
            String after = current.substring(i, current.length());
            if(available.contains(before))
                count += obtainableModified(after, available, cache);
        }
        cache.put(current, count);
        return count;
    }
}