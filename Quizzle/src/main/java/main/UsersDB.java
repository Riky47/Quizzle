package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UsersDB {
	private static Map<String, UserData> usersList = new LinkedHashMap<String, UserData>();
	private static int totalUsers;
	private static int totQuestion;
	
	public static UserData getUser(String name) {
		MainTest.loadDataBase();
		
		if (usersList.get(name) != null) {
			return usersList.get(name);
		}
		else {
			UserData newData = new UserData(++totalUsers, name, totQuestion);
			usersList.put(name, newData);
			return newData;
		}
	}
	
	public static void removeUser(String name) {
		if (usersList.get(name) != null) {
			usersList.remove(name);
		}
	}
	
	public static Map<String, Integer> getLeaderboard() {
		Map<String, Integer> board = new LinkedHashMap<String, Integer>();
		usersList.forEach((key, value)-> board.put(key, value.getTotalPoints()));
		
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(board.entrySet());
        list.sort(Entry.comparingByValue()); // FIX SORTING

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
		
		return result;
	}
	
	public static boolean loadUsers() {
		
		return true;
	}
	
	public static boolean saveUsers() {
		
		return true;
	}
	
	public UsersDB(int totQuestion)
	{
		totalUsers = 0;
		this.totQuestion = totQuestion;
		usersList = new HashMap<String, UserData>();
	}
}
