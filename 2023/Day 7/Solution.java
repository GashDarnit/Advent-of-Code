import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public static Map<Character, Character> mapping = new HashMap<>();
    
    static {
        mapping.put('T', 'A');
        mapping.put('J', 'B');
        mapping.put('Q', 'C');
        mapping.put('K', 'D');
        mapping.put('A', 'E');
    }
    
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
        System.out.println("Part One: " + totalWinnings(inputs));
    }
    
    private static long totalWinnings(String[] inputs) {
        Map<String, Integer> handsAndValues = new HashMap<>();
        List<Map<String, Integer>> listOfHands = new ArrayList<>();
        long total = 0;
        
        for(String input : inputs) {
            String[] temp = input.split(" ");
            Map<String, Integer> tempMap = new HashMap<>();
            tempMap.put(temp[0], Integer.parseInt(temp[1]));
            listOfHands.add(tempMap);
        }
        
        sortHandsAndValues(listOfHands);
        
        int rankIteration = 1;
        for(Map<String, Integer> items : listOfHands) {
            for(Map.Entry<String, Integer> entry : items.entrySet()) {
                total += (entry.getValue() * rankIteration);
            }
            rankIteration++;
        }
        
        return total;
    }
    
    private static void sortHandsAndValues(List<Map<String, Integer>> listOfHands) {
        int n = listOfHands.size();
 
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                String handOne = "";
                int bidOne = 0;
                
                String handTwo = "";
                int bidTwo = 0;
                
                for(Map.Entry<String, Integer> entry : listOfHands.get(j).entrySet()) {
                    handOne = entry.getKey();
                    bidOne = entry.getValue();
                }
                
                for(Map.Entry<String, Integer> entry : listOfHands.get(minIndex).entrySet()) {
                    handTwo = entry.getKey();
                    bidTwo = entry.getValue();
                }
                
                
                if(getHandTypeValue(handOne) <  getHandTypeValue(handTwo))
                    minIndex = j;
                else if(getHandTypeValue(handOne) == getHandTypeValue(handTwo)) {
                    int comparisonResult = compareHandValues(getHandValue(handOne), getHandValue(handTwo));
                    if(comparisonResult == 1)
                        minIndex = j;
                }
                
            }
            
            Map<String, Integer> temp = listOfHands.get(minIndex);
            listOfHands.set(minIndex, listOfHands.get(i));
            listOfHands.set(i, temp);
        }
    }
    
    private static int compareHandValues(char[] arrOne, char[] arrTwo) {
        for(int i = 0; i < arrOne.length; i++) {
            if(arrOne[i] < arrTwo[i])
                return 1;
            else if(arrOne[i] > arrTwo[i])
                return 0;
        }
        return 0;
    }
    
    private static char[] getHandValue(String hand) {
        char[] arr = new char[hand.length()];
        for(int i = 0; i < arr.length; i++) {
            if(mapping.containsKey(hand.charAt(i))) arr[i] = mapping.get(hand.charAt(i));
            else arr[i] = hand.charAt(i);
        }
        
        return arr;
    }
    
    private static int getHandTypeValue(String hand) {
        int sumHandValue = 0;
        
        Map<Character, Integer> map = new HashMap<>();
        for(char i : hand.toCharArray()) {
            if(!map.containsKey(i)) map.put(i, 1);
            else map.put(i, map.get(i) + 1);
        }
        
        for(Map.Entry<Character, Integer> entry : map.entrySet())
            sumHandValue += (int) Math.pow(entry.getValue(), 2);
        
        return sumHandValue;
    }

}
