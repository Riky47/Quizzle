package main;

import java.util.HashMap;
import java.util.Map;

public class UsersDB {
	private static Map<String, UserData> usersList;
	private static int totalUsers;
	private static int totQuestion;
	
	public static UserData getUser(String name) {
		if (usersList.get(name) != null) {
			return usersList.get(name);
		}
		else {
			UserData newData = new UserData(++totalUsers, name, 10);
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
	public UsersDB(int totQuestion)
	{
		this.totQuestion = totQuestion;
		totalUsers = 0;
		usersList = new HashMap<String, UserData>();
		
	}
}
