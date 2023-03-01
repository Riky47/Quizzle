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
			UserData newData = new UserData();
			newData.setUserId(++totalUsers);
			newData.setUsername(name);
			
			usersList.put(name, newData);
			return newData;
		}
	}
	
	// Create sets to update data.
	// Add save to json???
}
