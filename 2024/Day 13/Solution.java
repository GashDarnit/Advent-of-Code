import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

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

        String[] tempOne = lines.toArray(new String[0]);
        List<Data> inputs = getInput(tempOne);
        
        System.out.println("Part One: " + fewestTokenToWinAll(inputs));
        System.out.println("Part Two: " + fewestTokenToWinAllOnCrack(inputs));
    }
    
    private static long fewestTokenToWinAll(List<Data> inputs) {
        long total = 0;
        for(Data data : inputs) {
            long lowestCost = Long.MAX_VALUE;
            for(int i = 0; i < 100; i++) {
                for(int j = 0; j < 100; j++) {
                    if(data.aX * i + data.bX * j == data.priceX && data.aY * i + data.bY * j == data.priceY)
                        lowestCost = Math.min(lowestCost, i * 3 + j);
                }
            }
            if(lowestCost < Long.MAX_VALUE) total += lowestCost;
        }
        
        return total;
    }
    
    private static long fewestTokenToWinAllOnCrack(List<Data> inputs) {
        long total = 0;
        long crack = 10000000000000L;
        for(Data data : inputs) {
            long ax = data.aX, ay = data.aY, bx = data.bX, by = data.bY, px = data.priceX + crack, py = data.priceY + crack;
            long sNumerator = (ay * px) - (ax * py);
            long sDenominator = (bx * ay) - (by * ax);
            
            if(sNumerator % sDenominator == 0) {
                long s = sNumerator / sDenominator;
                long fNumerator = (px - (bx * s));
                
                if(fNumerator % ax == 0) {
                    long f = fNumerator / ax;
                    total += f * 3 + s;
                }
            } else continue;
        }
        
        return total;
    }
    
    private static List<Data> getInput(String[] tempOne) {
        List<Data> input = new ArrayList<>();
        long[] current = new long[6];
        for(String i : tempOne) {
            if(i.length() == 0) {
                input.add(new Data(current));
                current = new long[6];
                continue;
            }
            
            if(i.contains("Button A: X+")) {
                char[] temp = i.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < temp.length; j++) {
                    char cur = temp[j];
                    if((cur >= '0' && cur <= '9') || (cur == ',')) sb.append(cur);
                }
                String[] nums = sb.toString().split(",");
                current[0] = Long.parseLong(nums[0].trim());
                current[1] = Long.parseLong(nums[1].trim());
                continue;
            }
            
            if(i.contains("Button B: X+")) {
                char[] temp = i.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < temp.length; j++) {
                    char cur = temp[j];
                    if((cur >= '0' && cur <= '9') || (cur == ',')) sb.append(cur);
                }
                String[] nums = sb.toString().split(",");
                current[2] = Long.parseLong(nums[0].trim());
                current[3] = Long.parseLong(nums[1].trim());
                continue;
            }
            
            if(i.contains("Prize: X=")) {
                char[] temp = i.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < temp.length; j++) {
                    char cur = temp[j];
                    if((cur >= '0' && cur <= '9') || (cur == ',')) sb.append(cur);
                }
                String[] nums = sb.toString().split(",");
                current[4] = Long.parseLong(nums[0].trim());
                current[5] = Long.parseLong(nums[1].trim());
                continue;
            }
        }
        input.add(new Data(current));
        
        return input;
    }
    
    private static class Data {
        long aX;
        long aY;
        long bX;
        long bY;
        long priceX;
        long priceY;

        public Data(long aX, long aY, long bX, long bY, long priceX, long priceY) {
            this.aX = aX;
            this.aY = aY;
            this.bX = bX;
            this.bY = bY;
            this.priceX = priceX;
            this.priceY = priceY;
        }
        
        public Data(long[] data) {
            this.aX = data[0];
            this.aY = data[1];
            this.bX = data[2];
            this.bY = data[3];
            this.priceX = data[4];
            this.priceY = data[5];
        }
        
        @Override
        public String toString() {
            String string = "Button A: +" + aX + ", +" + aY + "\n";
            string += "Button B: +" + bX + ", +" + bY + "\n";
            string += "Price: X = " + priceX + ", Y = " + priceY + "\n";
            
            return string;
        }
    }
}