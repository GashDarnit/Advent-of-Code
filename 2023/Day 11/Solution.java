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
        
        List<Integer> emptyRows = new ArrayList<>();
        List<Integer> emptyColumns = new ArrayList<>();
        List<Pair> galaxies = new ArrayList<>();
        
        getEmptyRows(inputs, emptyRows);
        getEmptyColumns(inputs, emptyColumns);
        getGalaxies(inputs, galaxies);
        
        System.out.println("Part 1: " + sumDistanceBetweenGalaxyPairs(emptyRows, emptyColumns, galaxies, 2));
        System.out.println("Part 2: " + sumDistanceBetweenGalaxyPairs(emptyRows, emptyColumns, galaxies, 1000000));
    }
    
    private static long sumDistanceBetweenGalaxyPairs(List<Integer> emptyRows, List<Integer> emptyColumns, List<Pair> galaxies, int expansionRate) {
        long total = 0;
        
        for (int i = 0; i < galaxies.size(); i++) {
            int r1 = galaxies.get(i).valOne;
            int c1 = galaxies.get(i).valTwo;

            for (int j = 0; j < i; j++) {
                int r2 = galaxies.get(j).valOne;
                int c2 = galaxies.get(j).valTwo;

                for (int r = Math.min(r1, r2); r < Math.max(r1, r2); r++) {
                    total += (emptyRows.contains(r)) ? expansionRate : 1;
                }

                for (int c = Math.min(c1, c2); c < Math.max(c1, c2); c++) {
                    total += (emptyColumns.contains(c)) ? expansionRate : 1;
                }
            }
        }
        
        return total;
    }
    
    private static void getGalaxies(String[] inputs, List<Pair> galaxies) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length(); j++) {
                char ch = inputs[i].charAt(j);
                if (ch == '#') {
                    galaxies.add(new Pair(i, j));
                }
            }
        }
    }
    
    private static void getEmptyRows(String[] inputs, List<Integer> emptyRows) {
        boolean found;
        int index = 0;
        
        for(String input : inputs) {
            found = false;
            for(char col : input.toCharArray()) {
                if(col != '.') 
                    found = true;
            }
            if(!found)
                emptyRows.add(index);
            
            index++;
        }
    }
    
    private static void getEmptyColumns(String[] inputs, List<Integer> emptyColumns) {
        boolean found;
        int m = inputs.length, n = inputs[0].length();
        
        for(int i = 0; i < n; i++) {
            found = false;
            for(int j = 0; j < m; j++) {
                if(inputs[j].charAt(i) != '.')
                    found = true;
            }
            if(!found)
                emptyColumns.add(i);
        }
    }
    
    static class Pair {
        public int valOne;
        public int valTwo;
        
        public Pair() {}
        public Pair(int valOne, int valTwo) {
            this.valOne = valOne;
            this.valTwo = valTwo;
        }
    }
}