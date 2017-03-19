import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONObject;


public class JSONUtils
{
   public static String getJSONStringFromFile(String path)
   {
      Scanner sc;
      InputStream in = FileHandle.inputStreamFromFile(path);
      sc = new Scanner(in);
      String json = sc.useDelimiter("\\z").next();
      sc.close();
      
      return json;
      
   }
   
   public static JSONObject getJSONObjectFromFile(String path)
   {
      return new JSONObject(getJSONStringFromFile(path));
   }
   
   public static boolean objectExists(JSONObject jsonObject, String key)
   {
      Object o;
      
      try
      {
         o = jsonObject.get(key);
      }
      catch(Exception e)
      {
         return false;
      }
      
      return o != null;
   }
}
