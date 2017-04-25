package displaycomments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

/**
 *
 * @author Robert Tanase
 */
public class DisplayComments {

    private static final List<Comment> comments = new ArrayList<Comment>();
    private static final HashMap<Integer, List<Integer>> Map = new HashMap<Integer, List<Integer>>();
    
    public static void main(String[] args) {
        
        try {
            URL myURL = new URL("http://178.62.33.226/interview/comments.php");
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()))) {
                String inputLine;
                inputLine = in.readLine();
                
                JSONObject obj = new JSONObject(inputLine);
                JSONArray arr = obj.getJSONArray("comments");
                
                Map.put(0, new ArrayList<>());
                
                for (int i = 0; i < arr.length(); i++) {
                    
                    int id = arr.getJSONObject(i).getInt("id");
                    int reply = arr.getJSONObject(i).getInt("reply_to");
                    long date = arr.getJSONObject(i).getLong("date");
                    String text = arr.getJSONObject(i).getString("text");
                    
                    Map.put(id, new ArrayList<>());
                    Map.get((Integer)reply).add((Integer)id);
                    
                    Comment c = new Comment(id, reply, date, text);
                    comments.add(c);
                    
                }
                
                List<Integer> start = Map.get(0);
                DisplayComm(start, "");
            }
        }
        catch (MalformedURLException e) { 
            System.out.println("Error: " + e.getMessage());
        } 
        catch (IOException e) {   
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    private static void DisplayComm (List<Integer> Parent, String indent) {
        if (Parent.isEmpty())
            return;
        for (Integer i: Parent) {
            System.out.println(comments.get(i-1).toString(indent));
            DisplayComm(Map.get(i), indent + "     ");
        }
    }
    
}
