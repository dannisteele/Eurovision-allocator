import java.io.Serializable;

/**
 * Joins a Song object with a country.
 *
 * @author Daniella
 * @version 2.0
 */
public class Country implements Serializable
{
    private String country;
    private Song song;
    
    /**
     * Constructor for a Country object.
     * 
     * @param country A string of a country name. 
     * @param song A song object of a song. 
     */
    public Country(String country, Song song)
    {
        this.country = country;
        this.song = song;
    }
    
    /**
     * Getter for the country field.
     * 
     * @return The country as a string. 
     */
    public String getCountry()
    {
        return country;
    }
    
    /**
     * Getter for the song field. 
     * 
     * @return The Song object stored in the song field. 
     */
    public Song getSong()
    {
        return song;
    }
    
    /**
     * Setter for the country field.
     * 
     * @param country A string country name to be assigned to a song. 
     */
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    /**
     * Similar to toString, but ouputs to the terminal rather than returning. 
     */
    public void outputAsString()
    {
        System.out.println(country + "\n" + song.toString());
    }
    
    /**
     * Outputs a Country object with the country name on one line, 
     * followed by the Artist and song on the following line. 
     */
    @Override
    public String toString()
    {
        return country + "\n" + song.toString() + "\n";
    }
}
