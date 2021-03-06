import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * A program for Eurovision. 
 * The user will input the countries and acts for Eurovision which can then
 * be saved. It can then allocate out countries for each player to support.
 *
 * @author Daniella McSorley
 * @version 2.0
 */
public class Eurovision implements Serializable
{
    private ArrayList<Country> setList;
    private Scanner sc;
    private final long serialVersionUID = 1L;
    private int numOfPlayers;
    
    /**
     * Constructor for a Eurovision object.
     * 
     * @param numOfPlayers The number of people being allocated countries to support.
     */
    public Eurovision(int numOfPlayers)
    {
        setList = new ArrayList<>();
        sc = new Scanner(System.in);
        this.numOfPlayers = numOfPlayers;
    }
    
    /**
     * The main method which utilises the rest of the methods.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players?");
        int players = scan.nextInt();
        String[] playerNames = new String[players];
        for (int i = 0; i < players; i++) {
            System.out.println("What is the name of PLAYER " + (i+1));
            playerNames[i] = scan.next();
        }
        Eurovision euro = new Eurovision(players);
        while (true){    
            System.out.println("Do you have a set list already saved? \nType Y to load it or N to start new.");
            String ans = scan.next().toUpperCase();
            if (ans.equals("Y")) {
                euro.loadSetList();
                break;
            }
            else if (ans.equals("N")){
                euro.addCountryConsole();
                System.out.println("Save the current set list? Type Y to save or anything else to skip.");
                ans = scan.next().toUpperCase();
                if (ans.equals("Y")) {
                    euro.saveSetList();
                }
                break;
            }
            else {
                System.out.println("Invalid input, please try again.");
            }
        }
        System.out.println("Now allocating countries to each player...");
        Thread.sleep(1000);
        euro.allocateCountries(playerNames);
    }
    
    /**
     * Getter for the setList ArrayList.
     * 
     * @return setList ArrayList.
     */
    public ArrayList<Country> getSetList()
    {
        return setList;
    }
    
    /**
     * Getter for the numOfPlayers field.
     * 
     * @return numOfPlayers field.
     */
    public int getNumOfPlayers()
    {
        return numOfPlayers;
    }
    
    /**
     * Setter for the numOfPlayers field.
     * 
     * @param numOfPlayers The number of people playing. 
     */
    public void setNumOfPlayers(int numOfPlayers)
    {
        this.numOfPlayers = numOfPlayers;
    }
    
    /**
     * This can be used for any Country objects created manually. The 
     * addCountryConsole method is recommended for most cases.
     * 
     * @param country a Country Object to add to the setList ArrayList.
     */
    public void addCountry(Country country)
    {
        setList.add(country);
    }
    
    /**
     * Allows a user to manually remove a country by just the name rather
     * than requiring a Country object.
     * 
     * @param aCountry a String name of a country to be removed.
     * @return true if the country has been removed, or false if not.
     */
    public boolean removeCountry(String aCountry)
    {
        Iterator<Country> it = setList.iterator();
        while (it.hasNext()) {
            if (it.next().getCountry().equals(aCountry)) {
                it.remove();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Empties the setList ArrayList.
     */
    public void clearSetList()
    {
        setList.clear();
    }
    
    /**
     * Allows to user to see every Country object in setList ArrayList.
     */
    public void outputString()
    {
        for (Country country : setList) {
            System.out.println(country.toString());
        }
    }
    
    /**
     * This is the preferred method to add new countries to setList as the
     * Song and Country objects are made behind the scenes and added in a user
     * friendly way.
     */
    public void addCountryConsole()
    {
        String ans = "";
        while (!(ans.equals("no"))) {
            System.out.println("What is the name of the country?");
            String country = sc.nextLine();
            System.out.println("What artist is performing for " + country + "?");
            String artist = sc.nextLine();
            System.out.println("What is the name of the song performed by " + artist + "?");
            String title = sc.nextLine();
            Song theSong = new Song(artist, title);
            Country theCountry = new Country(country, theSong);
            setList.add(theCountry);
            System.out.println("Add another country? Type no if finished or anything else to continue.");
            ans = sc.nextLine();
        }
    }
    
    /**
     * Allocates the maximum amount of countries to each player so that each
     * person has the same amount to support. This is then output into the
     * terminal to show who is allocated each country.
     */
    public void allocateCountries(String[] playerNames)
    {
        int numToBeAllocated = setList.size() / numOfPlayers;
        Random rand = new Random();
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println("***" + playerNames[i].toUpperCase() + "***");
            for (int j = 0; j < numToBeAllocated; j++) {
                int index = rand.nextInt(setList.size());
                System.out.println(setList.get(index));
                setList.remove(index);
            }
        }
    }
    
    /**
     * Saves the setList ArrayList so it can be used again later without
     * having to manually input everything from scratch each time. 
     */
    public void saveSetList() throws IOException, ClassNotFoundException
    {
        FileOutputStream fos = new FileOutputStream("Set List");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(setList);
        oos.close();
    }
    
    /**
     * Loads a saved setList ArrayList which has already been set up and saved. 
     */
    public void loadSetList() throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream("Set List");
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        setList = (ArrayList<Country>) ois.readObject();
        ois.close();
    }
}
