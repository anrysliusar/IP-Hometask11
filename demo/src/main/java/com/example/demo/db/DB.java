package com.example.demo.db;

import java.util.HashMap;
import java.util.Map;

public class DB {
    public static Map<String, String> dictionary;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("Hello", "Hi");
        map.put("Laptop", "Notebook");
        map.put("Excellent", "Amazing");
        map.put("Unpleasant", "Nasty");
        dictionary = new HashMap<>(map);
    }
}
