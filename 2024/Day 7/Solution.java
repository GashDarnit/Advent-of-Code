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
        
        System.out.println("Part One: " + totalCalibrationResult(inputs));
        System.out.println("Part Two: " + totalCalibrationResultExtended(inputs));
    }
    
    private static long totalCalibrationResult(String[] inputs) {
        long total = 0;
        for(String input : inputs) {
            String[] temp = input.split(":");
            long target = Long.parseLong(temp[0].trim());
            List<Long> valuesToEvaluate = new ArrayList<>();
            getValuesToEvaluate(temp[1].trim(), valuesToEvaluate);
            
            List<Long> newValues = new ArrayList<>();
            newValues.add(valuesToEvaluate.get(0));
            valuesToEvaluate.remove(0);
            for(int i = 0; i < valuesToEvaluate.size(); i++) {
                long current = valuesToEvaluate.get(i);
                List<Long> tempNewVal = new ArrayList<>();
                
                for(Long currentEvaluation : newValues) {
                    long addition = currentEvaluation + current;
                    long multiplication = currentEvaluation * current;
                    
                    if(addition <= target) tempNewVal.add(addition);
                    if(multiplication <= target) tempNewVal.add(multiplication);
                }
                newValues = tempNewVal;
            }
            for(long i : newValues) {
                if(i == target) {
                    total += i;
                    break;
                }
            }
        }
        
        return total;
    }
    
    private static long totalCalibrationResultExtended(String[] inputs) {
        long total = 0;
        for(String input : inputs) {
            String[] temp = input.split(":");
            long target = Long.parseLong(temp[0].trim());
            List<Long> valuesToEvaluate = new ArrayList<>();
            getValuesToEvaluate(temp[1].trim(), valuesToEvaluate);
            
            List<Long> newValues = new ArrayList<>();
            newValues.add(valuesToEvaluate.get(0));
            valuesToEvaluate.remove(0);
            for(int i = 0; i < valuesToEvaluate.size(); i++) {
                long current = valuesToEvaluate.get(i);
                List<Long> tempNewVal = new ArrayList<>();
                
                for(Long currentEvaluation : newValues) {
                    long addition = currentEvaluation + current;
                    long multiplication = currentEvaluation * current;
                    long concat = Long.parseLong("" + currentEvaluation + "" + current);
                    
                    if(addition <= target) tempNewVal.add(addition);
                    if(multiplication <= target) tempNewVal.add(multiplication);
                    if(concat <= target) tempNewVal.add(concat);
                }
                newValues = tempNewVal;
            }
            
            for(long i : newValues) {
                if(i == target) {
                    total += i;
                    break;
                }
            }
        }
        
        return total;
    }
    
    private static void getValuesToEvaluate(String inputs, List<Long> valuesToEvaluate) {
        for(String i : inputs.split(" "))
            valuesToEvaluate.add(Long.parseLong(i));
    }
    
}