package main;

public class UserData {
	private int userId;
	private String username;
	
	
	
	// Sets
	public void setUserId(int id) {
		userId = id;
	}
	public void setUsername(String name) {
		username = name;
	}
	
	// Gets
	public final int getUserId() {
		return userId;
	}
	public final String getUsername() {
		return username;
	}
}
