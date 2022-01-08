package ui;

import model.Account;
import model.Tweet;
import model.Twitter;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Social Media Based app
//Modelled after "JsonSerializationDemo - WorkRoomApp" and "Teller App"
public class MainApp {
    private static final String JSON_STORE = "./data/twitter.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Twitter twitter;
    private Scanner input;

    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        twitter = new Twitter();
    }

    public MainApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMain();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMain() {
        init();
        boolean keepGoing = true;
        String command = null;


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                //nameChange(command);
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    public void nameChange(String name) {
        twitter.getAccount().changeName(name);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAccount(); //create a function to check account iin app
        } else if (command.equals("t")) {
            doTweet(); //create a function to check tweet in app
        } else if (command.equals("s")) {
            saveTwitter(); //create a function to save account
        } else if (command.equals("l")) {
            loadTwitter(); //create a function to load account
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Check my Account!");
        System.out.println("\tt -> Tweet Actions");
        System.out.println("\ts -> save account to file");
        System.out.println("\tl -> load account from file");
        System.out.println("\tq -> quit");
    }

    private void displayAccountOptions() {
        System.out.println("Select From");
        System.out.println("\ti -> Account Information Compiled");
        System.out.println("\tf -> Check Follower Count");
        System.out.println("\tn -> Check Following Count");
        System.out.println("\tt -> Check Followers");
        System.out.println("\ts -> Check Following");
        System.out.println("\ta -> Add Follower/Following");
    }

    // MODIFIES: this
    // EFFECTS: conducts a method with the account class
    public void doAccount() {
        displayAccountOptions();
        String selection = "";
        selection = input.next();
        selection = selection.toLowerCase();
        if (selection.equals("i")) {
            System.out.println(twitter.getAccount().informationCompiled());
        } else if (selection.equals("f")) {
            System.out.println(twitter.getAccount().followerCount());
        } else if (selection.equals("n")) {
            System.out.println(twitter.getAccount().followingCount());
        } else if (selection.equals("t")) {
            printAllFollowers();
        } else if (selection.equals("s")) {
            printAllFollowing();
        } else if (selection.equals("a")) {
            System.out.println("\te -> Follower");
            System.out.println("\ti -> Following");
            selection = input.next();
            followAdd(selection);
        }


    }

    private void followAdd(String selection) {
        if (selection.equals("e")) {
            System.out.println("\tType Follower Name");
            selection = input.next();
            selection = selection.toLowerCase();
            twitter.getAccount().addFollowers(selection);
        } else if (selection.equals("i")) {
            System.out.println("\tType Following Name");
            selection = input.next();
            selection = selection.toLowerCase();
            twitter.getAccount().addFollowing(selection);
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void doTweet() {
        tweetOptions();
        String selection = input.next();
        if (selection.equals("n")) {
            System.out.println("Enter text input");
            String inputText = input.next();
            twitter.getTweets().add(new Tweet(inputText, 0));
        } else if (selection.equals("h")) {
            System.out.println("Tweet History Starts Here");
            printAllTweets();
        } else if (selection.equals("l")) {
            likeActions();
        } else if (selection.equals("s")) {
            statsActions();
        }
    }

    public void likeActions() {
        System.out.println("Select which Tweet");
        printAllTweetsNumbered();
        String selection = input.next();
        int tweetNumber = Integer.parseInt(selection);
        tweetNumber--;
        System.out.println("\tl -> like tweet");
        System.out.println("\tu -> unlike tweet");
        selection = input.next();
        if (selection.equals("l")) {
            twitter.getTweets().get(tweetNumber).likeTweet();
        } else if (selection.equals("u")) {
            twitter.getTweets().get(tweetNumber).unlikeTweet();
        }

    }

    public void tweetOptions() {
        System.out.println("Select From");
        System.out.println("\tn -> New Tweet");
        System.out.println("\th -> Tweet History");
        System.out.println("\tl -> like/unlike tweet");
        System.out.println("\ts -> Tweet Stats");
    }

    public void statsActions() {
        System.out.println("Select which Tweet");
        printAllTweetsNumbered();
        String selection = input.next();
        int tweetNumber = Integer.parseInt(selection);
        tweetNumber--;
        System.out.println(twitter.getTweets().get(tweetNumber).tweetInfo());
    }

    public void printAllTweetsNumbered() {
        for (int i = 0; i < twitter.getTweets().size(); i++) {
            int temp = i + 1;
            System.out.println(Integer.toString(temp) + " " + twitter.getTweets().get(i).getText());
        }
    }

    public void printAllTweets() {
        for (int i = 0; i < twitter.getTweets().size(); i++) {
            System.out.println(twitter.getTweets().get(i).getText());
        }
    }

    public void printAllFollowers() {
        for (int i = 0; i < twitter.getAccount().getFollowers().size(); i++) {
            System.out.println(twitter.getAccount().getFollowers().get(i));
        }
    }

    public void printAllFollowing() {
        for (int i = 0; i < twitter.getAccount().getFollowing().size(); i++) {
            System.out.println(twitter.getAccount().getFollowing().get(i));
        }
    }


    // EFFECTS: saves the workroom to file
    private void saveTwitter() {
        try {
            jsonWriter.open();
            jsonWriter.write(twitter);
            jsonWriter.close();
            System.out.println("Saved " + twitter.getAccount().getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Account1 from file
    private void loadTwitter() {
        try {
            twitter = jsonReader.read();
            System.out.println("Loaded " + twitter.getAccount().getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
