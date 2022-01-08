package persistence;

import model.Account;
import model.Tweet;
import model.Twitter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Twitter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTwitter(jsonObject); //CHECK
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns

    private Twitter parseTwitter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Account a = new Account(name, addToList("follower list", jsonObject),
                addToList("following list", jsonObject));
        Twitter t = new Twitter();
        t.setAccount(a);
        ArrayList<String> tweetTexts = addToList("tweets", jsonObject);
        ArrayList<Integer> likeCounts = addToListInt("like count", jsonObject);
        ArrayList<Tweet> tweets = combineTweets(tweetTexts, likeCounts);
        for (int i = 0; i < tweets.size(); i++) {
            t.addTweet(tweets.get(i));
        }
        return t;
    }


    public ArrayList<Tweet> combineTweets(ArrayList<String> t, ArrayList<Integer> l) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < t.size(); i++) {
            Tweet tweet = new Tweet(t.get(i), l.get(i));
            tweets.add(tweet);
        }
        return tweets;
    }

    private ArrayList<String> addToList(String key, JSONObject jsonObject) {
        ArrayList<String> returnList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (int i = 0; i < jsonArray.length(); i++) {
            String element = jsonArray.getString(i);
            returnList.add(element);
        }
        return returnList;
    }

    private ArrayList<Integer> addToListInt(String key, JSONObject jsonObject) {
        ArrayList<Integer> returnList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (int i = 0; i < jsonArray.length(); i++) {
            int element = jsonArray.getInt(i);
            returnList.add(element);
        }
        return returnList;
    }
}
