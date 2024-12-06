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

        String[] inputs = lines.toArray(new String[0]);
        List<int[]> pageOrderingRules = new ArrayList<>();
        List<List<Integer>> pageToProduce = new ArrayList<>();
        Map<Pair, Boolean> mappings = new HashMap<>();
        
        parseInput(inputs, pageOrderingRules, pageToProduce);
        for(int[] i : pageOrderingRules) {
            mappings.put(new Pair(i[0], i[1]), true);
            mappings.put(new Pair(i[1], i[0]), false);
        }
        
        System.out.println("Part One: " + sumOfMiddlePageNumbers(pageOrderingRules, pageToProduce, mappings));
        System.out.println("Part Two: " + sumOfSortedMiddlePageNumbers(pageOrderingRules, pageToProduce, mappings));
    }
    
    private static long sumOfMiddlePageNumbers(List<int[]> pageOrderingRules, List<List<Integer>> pageToProduce, Map<Pair, Boolean> mappings) {
        long total = 0;
        for(List<Integer> pages : pageToProduce)
            if(checkOrder(pages, mappings))
                total += pages.get(pages.size() / 2);
        
        return total;
    }
    
    private static long sumOfSortedMiddlePageNumbers(List<int[]> pageOrderingRules, List<List<Integer>> pageToProduce, Map<Pair, Boolean> mappings) {
        long total = 0;
        for(List<Integer> pages : pageToProduce) {
            if(!checkOrder(pages, mappings)) {
                sortList(pages, mappings);
                total += pages.get(pages.size() / 2);
            }
        }
        
        return total;
    }
    
    private static boolean checkOrder(List<Integer> pages, Map<Pair, Boolean> mappings) {
        for(int i = 0; i < pages.size(); i++) {
            for(int j = i + 1; j < pages.size(); j++) {
                Pair pair = new Pair(pages.get(i), pages.get(j));
                if(mappings.containsKey(pair) && !mappings.get(pair)) return false;
            }
        }
        return true;
    }
    
    private static void sortList(List<Integer> pages, Map<Pair, Boolean> mappings) {
        for(int i = 0; i < pages.size() - 1; i++) {
            boolean swapped = false;
            for(int j = 0; j < pages.size() - i - 1; j++) {
                Pair pair = new Pair(pages.get(j), pages.get(j + 1));
                if(mappings.containsKey(pair) && !mappings.get(pair)) {
                    int temp = pages.get(j);
                    pages.set(j, pages.get(j + 1));
                    pages.set(j + 1, temp);
                    swapped = true;
                }
            }
            if(!swapped) break;
        }
    }
    
    private static void parseInput(String[] inputs, List<int[]> pageOrderingRules, List<List<Integer>> pageToProduce) {
        boolean switchData = false;
        for(String i : inputs) {
            if(i.length() == 0) {
                switchData = true;
                continue;
            }
            
            if(!switchData) {
                String[] temp = i.trim().split("\\|");
                pageOrderingRules.add(new int[] {Integer.parseInt(temp[0]), Integer.parseInt(temp[1])});
            } else {
                pageToProduce.add(new ArrayList<>());
                for(String str : i.trim().split(","))
                    pageToProduce.get(pageToProduce.size() - 1).add(Integer.parseInt(str));
            }
        }
    }
    
    private static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
        this.a = a;
        this.b = b;
        }

        public void setA(int a) {
            this.a = a;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getA() {
            return this.a;
        }

        public int getB() {
            return this.b;
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
        
        @Override
        public String toString() {
            return "(" + this.a + ", " + b + ")";
        }
    }
}