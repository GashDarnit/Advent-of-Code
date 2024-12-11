import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

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

        String[] temp = lines.toArray(new String[0])[0].trim().split(" ");
        long[] inputs = new long[temp.length];
        for(int i = 0; i < inputs.length; i++) inputs[i] = Long.parseLong(temp[i]); 
        
        System.out.println("Part One: " + numberOfRocksAfter25Blinks(inputs));
        System.out.println("Part Two: " + numberOfRocksAfter75Blinks(inputs));
    }
    
    private static long numberOfRocksAfter25Blinks(long[] inputs) {
        List<Long> stones = new ArrayList<>();
        for(long i : inputs) stones.add(i);
        for(int i = 1; i <= 25; i++) applyRule(stones);
        
        return stones.size();
    }
    
    private static long numberOfRocksAfter75Blinks(long[] inputs) {
        long total = 0;
        Map<Pair, Long> cache = new HashMap<>();
        List<Long> stones = new ArrayList<>();
        for(long i : inputs) stones.add(i);
        
        for(long stone : stones)
            total += recursiveCount(75, stone, cache);
        return total;
    }
    
    private static long recursiveCount(long step, long stone, Map<Pair, Long> cache) {
        Pair pair = new Pair(stone, step);
        if(cache.containsKey(pair)) return cache.get(pair);
        
        if(step == 0) return 1;
        
        String currentToString = String.valueOf(stone);
        if(stone == 0) {
            long total = recursiveCount(step - 1, 1, cache);
            cache.put(pair, total);
            return total;
        }
        else if(currentToString.length() % 2 == 0) {
            String leftHalf = currentToString.substring(0, currentToString.length() / 2);
            String rightHalf = currentToString.substring((currentToString.length() / 2), currentToString.length());
            
            long total = recursiveCount(step - 1, Long.parseLong(leftHalf), cache) + recursiveCount(step - 1, Long.parseLong(rightHalf), cache);
            cache.put(pair, total);
            
            return total;
        }
        long total = recursiveCount(step - 1, stone * 2024, cache);
        cache.put(pair, total);
        return total;
    }
    
    private static void applyRule(List<Long> stones) {
        boolean endOfList = false;
        int index = 0;
        while(!endOfList) {
            long current = stones.get(index);
            String currentToString = String.valueOf(current);
            
            if(current == 0) stones.set(index, (long) 1);
            else if(currentToString.length() % 2 == 0) {
                String leftHalf = currentToString.substring(0, currentToString.length() / 2);
                String rightHalf = currentToString.substring((currentToString.length() / 2), currentToString.length());
                
                stones.set(index, Long.parseLong(rightHalf));
                stones.add(index, Long.parseLong(leftHalf));
                index++;
            } else {
                stones.set(index, current * 2024);
            }
            
            if(index == stones.size() - 1) break;
            index++;   
        }
        //System.out.println(stones.toString());
    }
    
    private static class Pair {
        long a;
        long b;

        public Pair(long a, long b) {
            this.a = a;
            this.b = b;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair that = (Pair) o;
            return a == that.a && b == that.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }
}