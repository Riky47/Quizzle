package main;

import java.util.HashMap;
import java.util.Map;

public class UsersDB {
	private static Map<String, UserData> usersList = new HashMap<String, UserData>();
	private static int totalUsers = 0;
	
	public static UserData getUser(String name) {
		if (usersList.get(name) != null) {
			return usersList.get(name);
		}
		else {
			UserData newData = new UserData(++totalUsers, name);
			usersList.put(name, newData);
			return newData;
		}
	}
	
	public static void removeUser(String name) {
		if (usersList.get(name) != null) {
			usersList.remove(name);
		}
	}
	
	public static boolean loadUsers() {
		
		return true;
	}
	
	public static boolean saveUsers() {
		
		return true;
	}
}
