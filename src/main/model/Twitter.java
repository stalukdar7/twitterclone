package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Twitter implements Writable  {
    Account account;
    ArrayList<Tweet> tweets;

    public Twitter() {
        ArrayList<String> initialFollowing = new ArrayList<>();
        ArrayList<String> initialFollowers = new ArrayList<>();
        account = new Account("Shaon", initialFollowers, initialFollowing);
        tweets = new ArrayList<>();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addTweet(Tweet t) {
        tweets.add(t);
    }

    public Account getAccount() {
        return account;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void printAllTweetsNumbered() {
        for (int i = 0; i < getTweets().size(); i++) {
            int temp = i + 1;
            System.out.println(Integer.toString(temp) + " " + getTweets().get(i).getText());
        }
    }

    public void printAllTweets() {
        for (int i = 0; i < getTweets().size(); i++) {
            System.out.println(getTweets().get(i).getText());
        }
    }

    public void printAllFollowers() {
        for (int i = 0; i < getAccount().getFollowers().size(); i++) {
            System.out.println(getAccount().getFollowers().get(i));
        }
    }

    public void printAllFollowing() {
        for (int i = 0; i < getAccount().getFollowing().size(); i++) {
            System.out.println(getAccount().getFollowing().get(i));
        }
    }


    public JSONArray addToJson(ArrayList list) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonArray.put(list.get(i));
        }
        return jsonArray;
    }

    //Split Functions
    public ArrayList<String> extractTweets(ArrayList<Tweet> tweets) {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < tweets.size(); i++) {
            output.add(tweets.get(i).getText());
        }
        return output;
    }

    public ArrayList<Integer> extractLikeCount(ArrayList<Tweet> tweets) {
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < tweets.size(); i++) {
            output.add(tweets.get(i).getLikeCount());
        }

        return output;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", account.getName());
        json.put("follower list", addToJson(getAccount().followerList));
        json.put("following list", addToJson(getAccount().followerList));
        json.put("tweets", addToJson(extractTweets(tweets)));
        json.put("like count", addToJson(extractLikeCount(tweets)));
        return json;
    }


}
