package com.techlabs.camunda.entity;

import java.util.HashMap;
import java.util.Map;

public class Database {
	
	private static Database instance;
    private Map<String, CardDetails> database;

    private Database() {
    	database = new HashMap<>();
    	
    	database.put("1234567890123456", new CardDetails(5000.0, 1234));
    	database.put("9876543210987654", new CardDetails(10000.0, 5678));
    	database.put("12345", new CardDetails(10000.0, 1234));
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Map<String, CardDetails> getDatabase() {
        return database;
    }

}
