import java.io.*;
import java.util.List;
import java.util.ArrayList;

class Solution {
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
        for(String i : inputs)
            System.out.println(i);
        
        System.out.println("\n");
        sumEngineSchematics(inputs);
    }
    
    private static long sumEngineSchematics(String[] input) {
        int n = input.length, m = input[0].length();
        boolean topLeft, topRight;
        long sum = 0;
        StringBuilder sb;
        
        for(int i = 0; i < n; i++) {
            char[] arr = input[i].toCharArray();
            sb = new StringBuilder();
            
            for(int j = 0; j < m; j++) {
                char cur = arr[j];
                if( !(cur >= 46 && cur <= 57) ) {
                    topLeft = true;
                    topRight = true;
                    
                    //check above
                    if(i >= 1 && (input[i - 1].charAt(j)) >= 48 && input[i - 1].charAt(j) <= 57) {
                        sb.append(input[i - 1].charAt(j));
                        
                        int left = j - 1;
                        while(left >= 0 && (input[i - 1].charAt(left) >= 48 && input[i - 1].charAt(left) <= 57)) {
                            topLeft = false;
                            sb.insert(0, input[i - 1].charAt(left));
                            left--;
                        }
                        
                        int right = j + 1;
                        while(right <= m - 1 && (input[i - 1].charAt(right) >= 48 && input[i - 1].charAt(right) <= 57)) {
                            topRight = false;
                            sb.append(input[i - 1].charAt(right));
                            right++;
                        }
                        
                        System.out.println(sb.toString());
                        sb = new StringBuilder();
                    }
                    
                    //check top left
                    if(topLeft && i >= 1 && j >= 1 && (input[i - 1].charAt(j - 1) >= 48 && input[i - 1].charAt(j - 1) <= 57)) {
                        sb.append(input[i - 1].charAt(j - 1));
                        
                        int left = j - 2;
                        while(left >= 0 && (input[i - 1].charAt(left) >= 48 && input[i - 1].charAt(left) <= 57)) {
                            sb.insert(0, input[i - 1].charAt(left));
                            left--;
                        }
                        System.out.println(sb.toString());
                        sb = new StringBuilder();
                    }
                }
            }
            
        }
        
        return sum;
    }
    
}