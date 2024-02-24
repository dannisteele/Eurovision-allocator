import java.io.Serializable;

/**
 * Defines a song with an artist and title.
 *
 * @author Daniella McSorley
 * @version 2.0
 */
public class Song implements Serializable
{
    private String artist;
    private String title;
    
    /**
     * Constructor for a Song object.
     * 
     * @param artist The artist performing a song.
     * @param title The title of a song being performed. 
     */
    public Song(String artist, String title)
    {
        this.artist = artist;
        this.title = title;
    }
    
    /**
     * Getter for the artist field. 
     * 
     * @return The artist field. 
     */
    public String getArtist()
    {
        return artist;
    }
    
    /**
     * Getter for the title field.
     * 
     * @return The title field. 
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Setter for the artist field.
     * 
     * @param artist A string of an artist performing. 
     */
    public void setArtist(String artist)
    {
        this.artist = artist;
    }
    
    /**
     * Setter for the title field.
     * 
     * @param title A string of the title of a song.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * Returns a song in the form "artist: title".
     * 
     * @return A String in the form "artist: title".
     */
    @Override
    public String toString()
    {
        return artist + ": " + title;
    }
}