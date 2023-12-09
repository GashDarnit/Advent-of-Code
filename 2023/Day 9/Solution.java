import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Solution {
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

        System.out.println("Part 1: " + sumOfExtrapolatedValues(inputs));
        System.out.println("Part 2: " + sumOfExtrapolatedValuesPartTwo(inputs));
    }
    
    private static long sumOfExtrapolatedValues(String[] inputs) {
        int total = 0;
        
        for(String input : inputs) {
            List<Integer> values = new ArrayList<>();
            String[] numStrings = input.split(" ");
            
            for (String numString : numStrings) {
                values.add(Integer.parseInt(numString));
            }

            total += extrapolate(values);
        }
        
        return total;
    }
    
    private static long sumOfExtrapolatedValuesPartTwo(String[] inputs) {
        int total = 0;
        
        for(String input : inputs) {
            List<Integer> values = new ArrayList<>();
            String[] temp = input.split(" ");
            
            for(String i : temp) values.add(Integer.parseInt(i));
            total += extrapolatePartTwo(values);
        }
        
        return total;
    }
    
    public static int extrapolate(List<Integer> array) {
        if (allZeros(array)) {
            return 0;
        }

        List<Integer> deltas = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            deltas.add(array.get(i) - array.get(i - 1));
        }

        int diff = extrapolate(deltas);
        return array.get(array.size() - 1) + diff;
    }
    
    public static long extrapolatePartTwo(List<Integer> array) {
        if (allZeros(array))
            return 0;

        List<Integer> deltas = new ArrayList<>();
        for (int i = 1; i < array.size(); i++)
            deltas.add(array.get(i) - array.get(i - 1));

        long diff = extrapolatePartTwo(deltas);
        return array.get(0) - diff;
    }

    public static boolean allZeros(List<Integer> values) {
        for (int num : values)
            if (num != 0)
                return false;
        return true;
    }
}
