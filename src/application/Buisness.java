package application;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class Buisness {
	private String usersFilepath = "../JSONdatabase/users.json";
	//private JSONObject[] loadedUsers;
	private JSONObject users; 
	
	public Buisness()
	{
		users = JSONUtils.getJSONObjectFromFile(usersFilepath); 
	}
	public JSONObject getUsers()
	{
		return users;
	}
	/*
	public void loadUsers()
	{
		System.out.println("LOADING");
		
		ArrayList<JSONObject> loadedUsersList = new ArrayList<JSONObject>();
		JSONArray usernames = users.names();
		System.out.println(usernames.toString());
		
		int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	loadedUsersList.add(users.getJSONObject(usernames.getString(i)));
                i++;
            }
        }
        
        loadedUsers = loadedUsersList.toArray(new JSONObject[0]);
        
	}
	*/
	public void addUser(String username, JSONObject newUserData)
	{
		users.put(username, newUserData);
		/*
		JSONObject[] newArray = new JSONObject[loadedUsers.length + 1];
		for (int i = 0; i < loadedUsers.length; i++){
	        newArray[i] = loadedUsers[i];
	    }
		newArray[newArray.length - 1] = newUser;
		loadedUsers = newArray;
		*/
	}
	public void updateFile()
	{
		try
        {
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(users.toString(4));
            custWriter.flush();
            custWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

}