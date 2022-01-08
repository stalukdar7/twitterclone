package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import model.EventLog;

//Represents an account with details like followers, following and name
public class Account {
    private String name;                                              // the account owner name
    ArrayList<String> followerList = new ArrayList<>();         // the list of followers of the account
    ArrayList<String> followingList = new ArrayList<>();        // the list of following of the account


    public Account(String username, ArrayList<String> initialFollowerList, ArrayList<String> initialFollowingList) {
        name = username;
        followerList = initialFollowerList;
        followingList = initialFollowingList;
    }

    public String getName() {
        return name;
    }

    public void changeName(String n) {
        this.name = n;
    }

    public ArrayList<String> getFollowers() {
        return followerList;
    }

    public ArrayList<String> getFollowing() {
        return followingList;
    }

    public void addFollowers(String name) {
        followerList.add(name);
        EventLog.getInstance().logEvent(new Event("Follower Added."));
    }

    public void addFollowing(String name) {
        followingList.add(name);
        EventLog.getInstance().logEvent(new Event("Following Added."));
    }

    public int followingCount() {
        EventLog.getInstance().logEvent(new Event("Following Count Checked."));
        return followingList.size();
    }

    public int followerCount() {
        EventLog.getInstance().logEvent(new Event("Follower Count Checked."));
        return followerList.size();
    }

    public String firstFollowerList() {
        return followerList.get(0);
    }

    public String firstFollowingList() {
        return followingList.get(0);
    }

    public String informationCompiled() {
        String information = "Hi " + name + " You have " + followingCount()
                + " Followers and " + followerCount() + " Following";
        EventLog.getInstance().logEvent(new Event("Account Information Compiled retrieved."));
        return information;
    }

}

