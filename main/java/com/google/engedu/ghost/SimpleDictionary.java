package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private ArrayList<String> newWords;
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if (prefix==null || prefix.trim()==""){
            Random r = new Random();
            //return randomly any word
            return words.get(r.nextInt(words.size()));
        }else{
            Comparator<String> c = new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.substring(0,Math.min(s.length(),t1.length())).compareToIgnoreCase(t1);

                }
            };
            int i= Collections.binarySearch(words,prefix,c);
            if(i>=0){
                return words.get(i);
            }else {
                return null;
            }
        }
        //return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        if (prefix==null || prefix.trim()==""){
            Random r = new Random();
            //return randomly any word
            return words.get(r.nextInt(words.size()));
        }else{
            Comparator<String> c = new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.substring(0,Math.min(s.length(),t1.length())).compareToIgnoreCase(t1);

                }
            };
            int i= Collections.binarySearch(words,prefix,c);
            if(i>=0){   // add all the words having the prefix to newWords
                newWords.add( words.get(i));
            }else {
                return null;
            }
            Comparator comparator = Collections.reverseOrder();
            Collections.sort(newWords,comparator);
            //newWords have words having the prefix but
            // i want to have newWOrds in reverse order of length
            //it will take the largest length string and if we want even or odd acc. to it
            // if it fulfills our needs then we will select that string or else chack next
            //string.. i.e biggest word would be selected
            // or another logic that from the given words select words like having the least number of words having out prefix
            //so that it increases the probability of user doing challenge
            // though there would be words left
            String selected = null;
            if (prefix.length()%2==0){
                //computer started first

                //return even length word
            }else {
                //player started first

                //return odd length word
            }
            return selected;
        }
        //return null;

    }
}
