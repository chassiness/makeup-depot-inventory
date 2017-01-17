package com.makeupdepot.inventory.repo.domain.obj;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.makeupdepot.inventory.misc.Currency;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by chassiness on 11/16/16.
 */
public class ProductPriceTest {
    ProductPrice sample;
    Currency exchangeRate = new Currency(Currency.Type.Php, BigDecimal.valueOf(48.75));

    @Before
    public void initialize() {
        sample = new ProductPrice(new Product("Stila Cosmetics", "Liquid Lipstick", new Currency(Currency.Type.USD,
                BigDecimal.valueOf(24))), new Currency(Currency.Type.Php, BigDecimal.valueOf(1350)), exchangeRate);
    }

    @Test
    public void getTotalCost() throws Exception {
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(24).setScale(2)));
    }

    @Ignore
    @Test
    public void test() {
        Map<Integer, Set<String>> keypad = new HashMap<>();
        keypad.put(1, ImmutableSet.of());
        keypad.put(2, ImmutableSet.of("a","b","c"));
        keypad.put(3, ImmutableSet.of("d","e","f"));
        keypad.put(4, ImmutableSet.of("g","h","i"));
        keypad.put(5, ImmutableSet.of("j","k","l"));
        keypad.put(6, ImmutableSet.of("m","n","o"));
        keypad.put(7, ImmutableSet.of("p","q","r","s"));
        keypad.put(8, ImmutableSet.of("t","u","v"));
        keypad.put(9, ImmutableSet.of("w","x","y","z"));

        int[] input = {4,1,2,8,1};

        List<String> wordsSoFar = new ArrayList<>();
        for (int i : input) {
            for (String s : keypad.get(i)) {
                List<String> words = new ArrayList<>();
                words.addAll(wordsSoFar);
                if (words.isEmpty()) {
                    wordsSoFar.add(s);
                } else {
                    for (String word : words) {
                        if (!Collections.replaceAll(wordsSoFar, word, word+s)){ wordsSoFar.add(word+s); }
                    }
                }
            }
        }

        for (String word : wordsSoFar) {
            System.out.println(word);
        }

    }

    @Test
    public void getTotalCost_withTax() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0.0875));
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(26.1).setScale(2)));
    }

    @Test
    public void getTotalCost_zeroTax() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0));
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), equalTo(BigDecimal.valueOf(24).setScale(2)));
    }

    @Test
    public void getTotalCost_noShipping() throws Exception {
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(0)));
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(24).setScale(2)));
    }

    @Test
    public void getTotalCost_withShipping() throws Exception {
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(32.95)));
    }

    @Test
    public void getTotalCost_taxAndShipping() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0.0875));
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency totalCost = sample.getProduct().computeTotalCost();
        assertThat(totalCost.getType(), is(Currency.Type.USD));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(35.05)));
    }

    @Test
    public void getTotalCost_exchangeRate() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0.0875));
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency totalCost = sample.getProduct().computeTotalCost(exchangeRate);
        assertThat(totalCost.getType(), is(Currency.Type.Php));
        assertThat(totalCost.getValue(), is(BigDecimal.valueOf(1708.69)));
    }

    @Test
    public void getMarginAtRetailPrice() throws Exception {
        Currency margin = sample.computeMarginAtRetailPrice(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(180).setScale(2)));
    }

    @Test
    public void getMarginAtRetailPrice_tax() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0.0875));
        Currency margin = sample.computeMarginAtRetailPrice(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(77.62)));
    }

    @Test
    public void getMarginAtRetailPrice_shipping() throws Exception {
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency margin = sample.computeMarginAtRetailPrice(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(-256.31)));
    }

    @Test
    public void getMarginAtRetailPrice_taxAndShipping() throws Exception {
        sample.getProduct().setTax(new BigDecimal(0.0875));
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency margin = sample.computeMarginAtRetailPrice(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(-358.69)));
    }

    @Test
    public void getMarginAtApplicableDiscount() throws Exception {
        sample.getProduct().setApplicableDiscount(new BigDecimal(0.20));
        Currency margin = sample.computeMarginAtApplicableDiscount(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(414).setScale(2)));
    }

    @Test
    public void getMarginAtApplicableDiscount_noDiscount() throws Exception {
        Currency margin = sample.computeMarginAtApplicableDiscount(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(180).setScale(2)));
    }

    @Test
    public void getMarginAtApplicableDiscount_tax() throws Exception {
        sample.getProduct().setApplicableDiscount(new BigDecimal(0.20));
        sample.getProduct().setTax(new BigDecimal(0.0875));
        Currency margin = sample.computeMarginAtApplicableDiscount(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(332.1).setScale(2)));
    }

    @Test
    public void getMarginAtApplicableDiscount_shipping() throws Exception {
        sample.getProduct().setApplicableDiscount(new BigDecimal(0.20));
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency margin = sample.computeMarginAtApplicableDiscount(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(-22.31)));
    }

    @Test
    public void getMarginAtApplicableDiscount_taxAndShipping() throws Exception {
        sample.getProduct().setApplicableDiscount(new BigDecimal(0.20));
        sample.getProduct().setTax(new BigDecimal(0.0875));
        sample.getProduct().setShipping(new Currency(Currency.Type.USD, new BigDecimal(8.95)));
        Currency margin = sample.computeMarginAtApplicableDiscount(exchangeRate);
        assertThat(margin.getType(), is(Currency.Type.Php));
        assertThat(margin.getValue(), is(BigDecimal.valueOf(-104.21)));
    }

    @Ignore
    @Test
    public void longestSubString() {
        String input1 = "abac";
        assertThat(findLongestSubstringCount(input1), is(3));
        assertThat(findLongestSubstring(input1), is("aba"));

        String input2 = "abcbbbbcccbdddadacb";
        assertThat(findLongestSubstringCount(input2), is(10));
        assertThat(findLongestSubstring(input2), is("bcbbbbcccb"));
    }

    private int findLongestSubstringCount(String inputStr) {
        if (inputStr == null || inputStr.length() < 2) {
            throw new IllegalArgumentException("Input String needs to be at least 2 chars.");
        }
        // length = 2; chars are the same

        HashMap<Character, Integer> charMap = new HashMap<>(3);
        int start = 0;
        int maxSoFar = 0;
        char[] inputCharArr = inputStr.toCharArray();

        for (int i=0; i<inputStr.length(); i++) {
            Character currentChar = inputCharArr[i];
            if (charMap.containsKey(currentChar)) {
                charMap.put(currentChar, charMap.get(currentChar)+1);
            } else {
                charMap.put(currentChar, 1);
            }

            if (charMap.size() > 2) {
                maxSoFar = Math.max(maxSoFar, i-start);

                while (charMap.size() > 2) {
                    Character tempChar = inputCharArr[start];
                    if (charMap.get(tempChar) > 1) {
                        charMap.put(tempChar, charMap.get(tempChar)-1);
                    } else {
                        charMap.remove(tempChar);
                    }
                    start++;
                }
            }
        }
        return maxSoFar;
    }

    private String findLongestSubstring(String inputStr) {
        if (inputStr == null || inputStr.length() < 2) {
            throw new IllegalArgumentException("Input String needs to be at least 2 chars.");
        }
        // length = 2; chars are the same

        HashMap<Character, Integer> charMap = new HashMap<>(3);
        int start = 0;
        int maxSoFar = 0;
        String maxSubStrSoFar = "";
        char[] inputCharArr = inputStr.toCharArray();

        for (int i=0; i<inputStr.length(); i++) {
            Character currentChar = inputCharArr[i];
            if (charMap.containsKey(currentChar)) {
                charMap.put(currentChar, charMap.get(currentChar)+1);
            } else {
                charMap.put(currentChar, 1);
            }

            if (charMap.size() > 2) {
                if (i-start > maxSoFar) {
                    maxSoFar = i-start;
                    maxSubStrSoFar = inputStr.substring(start, i);
                }
                //maxSoFar = Math.max(maxSoFar, i-start);

                while (charMap.size() > 2) {
                    Character tempChar = inputCharArr[start];
                    if (charMap.get(tempChar) > 1) {
                        charMap.put(tempChar, charMap.get(tempChar)-1);
                    } else {
                        charMap.remove(tempChar);
                    }
                    start++;
                }
            }
        }
        return maxSubStrSoFar;
    }

    @Ignore
    @Test
    public void findSubsets() {
//        int[] input1 = {24, 1, 15, 3, 4, 15, 3};
//        assertThat(findNumSubSets(input1, 25), is(2));
        int[] input2 = {1, 2, 3, 4, 5, 9};
        //2,3,4
        //4,5
        //9
        //1,3,5

//        assertThat(findNumSubSets(input2, 9), is(4));
        System.out.println(Arrays.asList(findSubsets(input2, 9)));

    }

    private List<List<Integer>> findSubsets(int[] inputArr, int targetSum){
        int index = 0;
        List<List<Integer>> result = new ArrayList<>();
        while(index < inputArr.length) {
            List<Integer> found = findSubsets(inputArr, targetSum, new ArrayList<Integer>(), index);
            if (!found.isEmpty()) {
                result.add(found);
            }
            index++;
        }
        return result;
    }

    private List<Integer> findSubsets(int[] inputArr, int targetSum, ArrayList<Integer> current, int index) {
        if (sum(current) == targetSum) {
            return current;
        }

        if (sum(current) > targetSum) {
            return Collections.emptyList();
        }

        List<Integer> subset = findSubsets(inputArr, targetSum, current, index+1);
        if(subset.isEmpty()) {
            current.add(inputArr[index]);
            return findSubsets(inputArr, targetSum, current, index+1);
        } else {
            return subset;
        }
    }


    private int findNumSubSets(int[] input, int target) {
        int index = 0;
        int possibilities = 0;
        while (index < input.length) {
            possibilities += findNumSubsets(input, target, index, new ArrayList<>());
            index = index + 1;
        }
        return possibilities;
    }

    protected int findNumSubsets(int[] input, int target, int index, List<Integer> currentList) {
        if (index > input.length-1) {
            return 0;
        }

        if (input[index] > target) {
            System.out.println(input[index] + " None here. Target: " + target);
            return 0;
        }

        int possibilities = 0;

        if (sum(currentList) == target) {
            possibilities += 1;
            System.out.println("Possible:" + input[index]);
        } else if (sum(currentList) < target) {
            System.out.println(String.format("Finding extra numbers: for input [%s], target [%s], index[%s])",
                    input[index], target, index+1));
            possibilities += findNumSubsets(input, target, index+1, currentList);

            currentList.add(input[index]);
            possibilities += findNumSubsets(input, target, index+1, currentList);
        }

        return possibilities;
    }

    private int sum(List<Integer> addends) {
        int sum = 0;
        for (Integer i : addends) {
            sum += i;
        }
        System.out.println("SUM: " + sum);
        return sum;
    }


}