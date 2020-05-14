package com.pk.numberparser.service;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class PhoneNumberService {
    public Set<String> letterCombinations(String digits) {
        HashMap<Character, char[]> dict = new HashMap<Character, char[]>();
        dict.put('0',new char[]{'0'});
        dict.put('1',new char[]{'1'});
        dict.put('2',new char[]{'2','a','b','c'});
        dict.put('3',new char[]{'3','d','e','f'});
        dict.put('4',new char[]{'4','g','h','i'});
        dict.put('5',new char[]{'5','j','k','l'});
        dict.put('6',new char[]{'6','m','n','o'});
        dict.put('7',new char[]{'7','p','q','r','s'});
        dict.put('8',new char[]{'8','t','u','v'});
        dict.put('9',new char[]{'9','w','x','y','z'});

        Set<String> result = new HashSet<String>();
        if(digits==null||digits.length()==0){
            return result;
        }

        char[] arr = new char[digits.length()];
        helper(digits, 0, dict, result, arr);

        return result;
    }
    public String numberCombination(String alphaNumeric) {
        if (StringUtils.isNotBlank(alphaNumeric)) {
            alphaNumeric=alphaNumeric.replaceAll("[\\s\\-()]", "");
            alphaNumeric=alphaNumeric.toLowerCase();
            HashMap<Character, char[]> dict = new HashMap<Character, char[]>();
            dict.put('0',new char[]{'0'});
            dict.put('1',new char[]{'1'});
            dict.put('2',new char[]{'2'});
            dict.put('3',new char[]{'3'});
            dict.put('4',new char[]{'4'});
            dict.put('5',new char[]{'5'});
            dict.put('6',new char[]{'6'});
            dict.put('7',new char[]{'7'});
            dict.put('8',new char[]{'8'});
            dict.put('9',new char[]{'9'});

            dict.put('0',new char[]{'0'});
            dict.put('1',new char[]{'1'});
            dict.put('a',new char[]{'2'});
            dict.put('d',new char[]{'3'});
            dict.put('g',new char[]{'4'});
            dict.put('j',new char[]{'5'});
            dict.put('m',new char[]{'6'});
            dict.put('p',new char[]{'7'});
            dict.put('t',new char[]{'8'});
            dict.put('w',new char[]{'9'});

            dict.put('b',new char[]{'2'});
            dict.put('e',new char[]{'3'});
            dict.put('h',new char[]{'4'});
            dict.put('k',new char[]{'5'});
            dict.put('n',new char[]{'6'});
            dict.put('q',new char[]{'7'});
            dict.put('u',new char[]{'8'});
            dict.put('x',new char[]{'9'});

            dict.put('c',new char[]{'2'});
            dict.put('f',new char[]{'3'});
            dict.put('i',new char[]{'4'});
            dict.put('l',new char[]{'5'});
            dict.put('o',new char[]{'6'});
            dict.put('r',new char[]{'7'});
            dict.put('v',new char[]{'8'});
            dict.put('y',new char[]{'9'});

            dict.put('s',new char[]{'7'});
            dict.put('z',new char[]{'9'});
            String numeric = null;

            Set<String> result = new HashSet<String>();
            if(alphaNumeric==null||alphaNumeric.length()==0){
                return null;
            }else{
                char[] arr = new char[alphaNumeric.length()];
                helper(alphaNumeric, 0, dict, result, arr);

                if (!result.isEmpty()) {
                    numeric = result.iterator().next();
                }
                return numeric;
            }
        }else {
            return null;
        }


    }
    private void helper(String digits, int index, HashMap<Character, char[]> dict,
                        Set<String> result, char[] arr){
        if(index==digits.length()){
            result.add(new String(arr));
            return;
        }

        char number = digits.charAt(index);
        char[] candidates = dict.get(number);
        for(int i=0; i<candidates.length; i++){
            arr[index]=candidates[i];
            helper(digits, index+1, dict, result, arr);
        }
    }

}
