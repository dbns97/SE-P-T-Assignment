package application;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Buisness {
	String usersFilepath = "../JSONdatabase/users.json";
	ArrayList <JSONObject> loadedUsers;
	JSONObject users = JSONUtils.getJSONObjectFromFile(usersFilepath);
	
	public void loadUsers()
	{
		JSONArray usernames = users.names();
		
		int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	loadedUsers.add(users.)

                i++;
            }
        }
	}
	

}
