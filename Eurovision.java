import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A program for Eurovision.
 * The user will input the countries and acts for Eurovision which can then
 * be saved. It can then allocate out countries for each player to support.
 *
 * @author Daniella McSorley
 * @version 0.2
 */
public class Eurovision implements Serializable {
    private ArrayList<Country> setList;
    private Scanner sc;
    private final long serialVersionUID = 1L;
    private int numOfPlayers;

    /**
     * Constructor for a Eurovision object.
     * 
     * @param numOfPlayers The number of people being allocated countries to
     *                     support.
     */
    public Eurovision(int numOfPlayers) {
        setList = new ArrayList<>();
        sc = new Scanner(System.in);
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * The main method which utilises the rest of the methods.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Welcome to the Eurovision allocator!");
        Thread.sleep(3000);
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players?");
        int players = scan.nextInt();
        String[] playerNames = new String[players];
        for (int i = 0; i < players; i++) {
            System.out.println();
            System.out.println("What is the name of PLAYER " + (i + 1));
            playerNames[i] = scan.next();
        }
        Eurovision euro = new Eurovision(players);
        euro.addCountriesFromCSV(false);
        String ans = "";
        System.out.println();
        System.out.println("Available years:\n" + euro.getAvailableYears());
        while (!ans.equals("Y") && !ans.equals("N")) {
            System.out.println();
            System.out.println("Is the set list one of the above? Y/N");
            ans = scan.next().toUpperCase();
        }
        if (ans.equals("Y")) {
            System.out.println();
            System.out.println("What year is the set list?");
            String year = scan.next().toUpperCase();

            euro.loadSetList(year);
        } else {
            ans = "";
            String fileName = "";
            while (!ans.equals("Y") && !ans.equals("N")) {
                System.out.println();
                System.out.println("Load from CSV? Y/N");
                ans = scan.next().toUpperCase();
            }
            if (ans.equals("Y")) {
                fileName = euro.addCountriesFromCSV(true);
            } else {
                euro.addCountryConsole();
            }
            ans = "";
            while (!ans.equals("Y") && !ans.equals("N")) {
                System.out.println();
                System.out.println("Save the current set list? Y/N");
                ans = scan.next().toUpperCase();
            }
            if (ans.equals("Y")) {
                if (!fileName.isEmpty()) {
                    euro.saveSetList(fileName);
                } else {
                    System.out.println();
                    System.out.println("What year is this set list for?");
                    String year = scan.next().toUpperCase();
                    euro.saveSetList(year);
                }
            }
        }
        System.out.println();
        if (players > 0) {
            System.out.println("Now allocating countries to each player...");
            Thread.sleep(1000);
            euro.allocateCountries(playerNames);
            scan.close();
        }
    }

    /**
     * Getter for the setList ArrayList.
     * 
     * @return setList ArrayList.
     */
    public ArrayList<Country> getSetList() {
        return setList;
    }

    /**
     * Getter for the numOfPlayers field.
     * 
     * @return numOfPlayers field.
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * Setter for the numOfPlayers field.
     * 
     * @param numOfPlayers The number of people playing.
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * This can be used for any Country objects created manually. The
     * addCountryConsole method is recommended for most cases.
     * 
     * @param country a Country Object to add to the setList ArrayList.
     */
    public void addCountry(Country country) {
        setList.add(country);
    }

    /**
     * Allows a user to manually remove a country by just the name rather
     * than requiring a Country object.
     * 
     * @param aCountry a String name of a country to be removed.
     * @return true if the country has been removed, or false if not.
     */
    public boolean removeCountry(String aCountry) {
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
    public void clearSetList() {
        setList.clear();
    }

    /**
     * Allows to user to see every Country object in setList ArrayList.
     */
    public void outputString() {
        for (Country country : setList) {
            System.out.println(country.toString());
        }
    }

    /**
     * This is the preferred method to add new countries to setList as the
     * Song and Country objects are made behind the scenes and added in a user
     * friendly way.
     */
    public void addCountryConsole() {
        String ans = "";
        String correct = "";
        while (!(ans.equals("N"))) {
            ans = "";
            correct = "";
            System.out.println();
            System.out.println("What is the name of the country?");
            String country = sc.nextLine();
            System.out.println();
            System.out.println("What artist is performing for " + country + "?");
            String artist = sc.nextLine();
            System.out.println();
            System.out.println("What is the name of the song performed by " + artist + "?");
            String title = sc.nextLine();
            Song theSong = new Song(artist, title);
            Country theCountry = new Country(country, theSong);

            while (!correct.equals("Y") && !correct.equals("N")) {
                System.out.println();
                System.out.println("So that is...");
                System.out.println(country.toUpperCase());
                System.out.println(artist + ": " + title);
                System.out.println();
                System.out.println("Does that look right? Y/N");
                correct = sc.nextLine().toUpperCase();
            }

            if (correct.equals("Y")) {
                setList.add(theCountry);
                System.out.println();
                System.out.println("Country added!");
            } else {
                System.out.println();
                System.out.println("Let's scrap that one then and start it again.");
            }

            while (!ans.equals("Y") && !ans.equals("N")) {
                System.out.println();
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
    public void allocateCountries(String[] playerNames) throws InterruptedException {
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
     * @throws IOException            Throws if unable to save the file.
     * @param year The year of the Eurovision set list being saved.
     */
    public void saveSetList(String year) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream("Set_Lists/" + year);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(setList);
        oos.close();
    }

    /**
     * Loads a saved setList ArrayList which has already been set up and saved.
     * 
     * @throws InterruptedException   Throws when interrupted.
     * @throws ClassNotFoundException Throws if a class is not found.
     * @throws IOException            Throws if unable to locate the file.
     * @param year The year of the Eurovision set list being loaded.
     */
    public void loadSetList(String year) throws IOException, ClassNotFoundException, InterruptedException {
        try {
            FileInputStream fis = new FileInputStream("Set_Lists/" + year);
            ObjectInputStream ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            ArrayList<Country> loadedList = (ArrayList<Country>) ois.readObject();
            setList = loadedList;
            ois.close();
        } catch (IOException e) {
            System.out.println("Problem finding file. Starting again.");
            Thread.sleep(3000);
            main(null);
        }
    }

    /**
     * Adds countries and songs from a CSV file.
     *
     * @param cssFilePath The path of the CSV file.
     */
    public String addCountriesFromCSV(boolean useUserInput) {
        if (useUserInput) {
            // Your existing code for user input
            String file = "";
            try {
                System.out.println();
                System.out.println("What is the name of the CSV file?");
                file = sc.nextLine();
                String csvFilePath = "Set_Lists/CSV/" + file + ".csv";
                BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String countryName = parts[0].trim().replace("\"", "");
                        String artistName = parts[1].trim().replace("\"", "");
                        String songTitle = parts[2].trim().replace("\"", "");

                        Song theSong = new Song(artistName, songTitle);
                        Country theCountry = new Country(countryName, theSong);

                        setList.add(theCountry);
                    }
                }
                System.out.println("Countries added from CSS file.");
            } catch (IOException e) {
                System.err.println("Error reading CSS file: " + e.getMessage());
            }
            return file;
        } else {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("Set_Lists/CSV"), "*.csv")) {
                for (Path filePath : stream) {
                    BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            String countryName = parts[0].trim().replace("\"", "");
                            String artistName = parts[1].trim().replace("\"", "");
                            String songTitle = parts[2].trim().replace("\"", "");

                            Song theSong = new Song(artistName, songTitle);
                            Country theCountry = new Country(countryName, theSong);

                            setList.add(theCountry);
                        }
                    }
                    reader.close();

                    // Extract year and save setList outside the loop
                    String year = extractYearFromFilePath(filePath.toString());
                    try {
                        saveSetList(year);
                        clearSetList();
                    } catch (Exception e) {
                        System.out.println("Something went wrong: " + e);
                    }
                }
                System.out.println();
                System.out.println("Countries loaded.");
            } catch (IOException e) {
                System.err.println("Error reading CSV files: " + e.getMessage());
            }
            return "";
        }
    }

    /**
     * Extracts the given year from a filepath.
     *
     * @param filePath The path of the CSV file.
     * @return The year being extracted.
     */
    public static String extractYearFromFilePath(String filePath) {
        Pattern pattern = Pattern.compile("\\b(\\d{4})\\.csv\\b");
        Matcher matcher = pattern.matcher(filePath);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    /**
     * Extracts the given year from a filepath.
     *
     * @return A line separated string of available years in the system.
     */
    public String getAvailableYears() {
        ArrayList<String> output = new ArrayList<>();
        StringBuilder outputStringBuilder = new StringBuilder();
    
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("Set_Lists/CSV"), "*.csv")) {
            for (Path filePath : stream) {
                String year = extractYearFromFilePath(filePath.toString());
                output.add(year);
            }
            Collections.sort(output);
    
            for (String item : output) {
                outputStringBuilder.append(item).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV files: " + e.getMessage());
        }

        return outputStringBuilder.toString();
    }
}