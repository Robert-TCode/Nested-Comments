package displaycomments;
/**
 *
 * @author Robert Tanase
 */
public class Comment {
    
    private final int id;
    private final int parent;
    private final String text;
    private final long date;
    
    public Comment(int id, int reply, long date, String text) {
        this.id = id;
        this.parent = reply;
        this.date = date;
        this.text = text;
    }
    
    public String toString(String indent) {
        return indent + " @" + parent + " \"" + text + "\"\n" + indent + "     Id: " 
                + id + "  ReplyTo: " + parent + "  Date: "  + date + "\n";
    } 
    
}
