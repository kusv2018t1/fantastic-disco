package com.example;

public class helloworld {
    public int testcode(String word) {
        int cnt = 0;
        for (int i = 0; i < word.length(); i++) {
            cnt++;
        }

        return cnt;
    }
}