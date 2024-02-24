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
 * @version 0.2
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
        String ans = "";
        while (!ans.equals("Y") && !ans.equals("N")) {
            System.out.println("Do you have a set list already saved? Y/N");
            ans = scan.next().toUpperCase();
        }
        if (ans.equals("Y")) {
            System.out.println("What year is the set list?");
            String year = scan.next().toUpperCase();
            
            euro.loadSetList(year);
        }
        else {
            euro.addCountryConsole();
            ans = "";
            while (!ans.equals("Y") && !ans.equals("N")) {
                System.out.println("Save the current set list? Y/N");
                ans = scan.next().toUpperCase();
            }
            if (ans.equals("Y")) {
                System.out.println("What year is this set list for?");
                String year = scan.next().toUpperCase();
                euro.saveSetList(year);
            }
        }
        System.out.println("Now allocating countries to each player...");
        Thread.sleep(1000);
        euro.allocateCountries(playerNames);
        scan.close();
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
        String correct = "";
        while (!(ans.equals("N"))) {
            ans = "";
            correct = "";
            System.out.println("What is the name of the country?");
            String country = sc.nextLine();
            System.out.println("What artist is performing for " + country + "?");
            String artist = sc.nextLine();
            System.out.println("What is the name of the song performed by " + artist + "?");
            String title = sc.nextLine();
            Song theSong = new Song(artist, title);
            Country theCountry = new Country(country, theSong);
            
            while (!correct.equals("Y") && !correct.equals("N")) {
                System.out.println();
                System.out.println("So that is...");
                System.out.println(country.toUpperCase());
                System.out.println(artist + " - " + title);
                System.out.println();
                System.out.println("Does that look right? Y/N");
                correct = sc.nextLine().toUpperCase();
            }
            
            if (correct.equals("Y")) {
                setList.add(theCountry);
                System.out.println("Country added!");
                System.out.println();
            }
            else {
                System.out.println("Let's scrap that one then and start it again.");
            }

            while (!ans.equals("Y") && !ans.equals("N")) 
            {
                System.out.println("Add another country? Y/N.");
                ans = sc.nextLine().toUpperCase();
            }
        }
    }
    
    /**
     * Allocates the maximum amount of countries to each player so that each
     * person has the same amount to support. This is then output into the
     * terminal to show who is allocated each country.
     * 
     * @param playernames The list of player names.
     * @throws InterruptedException 
     */
    public void allocateCountries(String[] playerNames) throws InterruptedException
    {
        int songsPerPlayer = setList.size() / numOfPlayers;
        int remainingSongs = setList.size() % numOfPlayers;
        Random rand = new Random();
        
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println();
            System.out.println();
            System.out.println("***" + playerNames[i].toUpperCase() + "***");
            int numToBeAllocated = songsPerPlayer + (i < remainingSongs ? 1 : 0);
            Thread.sleep(1000);
            
            for (int j = 0; j < numToBeAllocated; j++) {
                int index = rand.nextInt(setList.size());
                System.out.println(setList.get(index));
                setList.remove(index);
                Thread.sleep(1000);
            }
        }
    }
    
    /**
     * Saves the setList ArrayList so it can be used again later without
     * having to manually input everything from scratch each time.
     * 
     * @throws ClassNotFoundException Throws if a class is not found.
     * @throws IOException Throws if unable to save the file.
     * @param year The year of the Eurovision set list being saved. 
     */
    public void saveSetList(String year) throws IOException, ClassNotFoundException
    {
        FileOutputStream fos = new FileOutputStream("Set_Lists/" + year);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(setList);
        oos.close();
    }
    
    /**
     * Loads a saved setList ArrayList which has already been set up and saved. 
     * 
     * @throws InterruptedException Throws when interrupted.
     * @throws ClassNotFoundException Throws if a class is not found.
     * @throws IOException Throws if unable to locate the file.
     * @param year The year of the Eurovision set list being loaded. 
     */
    public void loadSetList(String year) throws IOException, ClassNotFoundException, InterruptedException
    {
        try 
        {
            FileInputStream fis = new FileInputStream("Set_Lists/" + year);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Country> loadedList = (ArrayList<Country>) ois.readObject();
            setList = loadedList;
            ois.close();
        } 
        catch (IOException e)
        {
            System.out.println("Problem finding file. Starting again.");
            Thread.sleep(3000);
            main(null);
        }
    }
}
