import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Solution {
    private static Map<Character, Character> cardToValueMapping = new HashMap<>();
    private static Map<Character, Character> partTwoCardToValueMapping = new HashMap<>();
    private static List<String[]> hands = new ArrayList<>();

    static {
        cardToValueMapping.put('T', 'A');
        cardToValueMapping.put('J', 'B');
        cardToValueMapping.put('Q', 'C');
        cardToValueMapping.put('K', 'D');
        cardToValueMapping.put('A', 'E');
        
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
        
        System.out.println("Part 1: " + totalWinnings(inputs));
    }

    private static long totalWinnings(String[] inputs) {
        long total = 0;
        for (String input : inputs) {
            String[] temp = input.split(" ");
            hands.add(new String[]{temp[0], temp[1]});
        }
        
        sortHands();
        
        int rank = 1;
        for (String[] hand : hands) {
            total += (Integer.parseInt(hand[1]) * rank);
            rank++;
        }
        
        return total;
    }
    
    private static int getHandType(String hand) {
        Map<Character, Integer> map = new HashMap<>();

        for (char i : hand.toCharArray()) {
            if (!map.containsKey(i)) map.put(i, 1);
            else map.put(i, map.get(i) + 1);
        }

        if (map.values().contains(5))
            return 6;
        else if (map.values().contains(4))
            return 5;
        else if (map.values().contains(3)) {
            if (map.values().contains(2))
                return 4;
            return 3;

        } else if (map.values().contains(2)) {
            int count = 0;
            for (int i : map.values()) if (i == 2) count++;

            if (count == 2) return 2;
            else return 1;
        }

        return 0;
    }
    
    private static void sortHands() { //Selection Sort
        int n = hands.size();
 
        for (int i = 0; i < n - 1; i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                String[] tempOne = hands.get(j);
                String[] tempTwo = hands.get(minIndex);
                
                if(getHandType(tempOne[0]) < getHandType(tempTwo[0]))
                    minIndex = j;
                else if(getHandType(tempOne[0]) == getHandType(tempTwo[0])) {
                    if(strengthCheck(tempOne[0], tempTwo[0]))
                        minIndex = j;
                }
            }
            
            String[] temp = hands.get(minIndex);
            hands.set(minIndex, hands.get(i));
            hands.set(i, temp);
        }
    }
    
    private static boolean strengthCheck(String handOne, String handTwo) {
        char[] arr1 = handOne.toCharArray();
        char[] arr2 = handTwo.toCharArray();
        
        for(int curIndex = 0; curIndex < 5; curIndex++) {
            char charOne = cardToValueMapping.containsKey(arr1[curIndex]) ? cardToValueMapping.get(arr1[curIndex]) : arr1[curIndex];
            char charTwo = cardToValueMapping.containsKey(arr2[curIndex]) ? cardToValueMapping.get(arr2[curIndex]) : arr2[curIndex];
            
            if (charOne < charTwo)
                return true;
            
            else if (charOne == charTwo)
                continue;
            
            else if(charOne > charTwo)
                return false;
        }
        
        return false;
    }

}
