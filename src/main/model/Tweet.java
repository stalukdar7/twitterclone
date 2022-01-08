package model;

public class Tweet {
    private String body;
    private int likeCount;


    //tweet and likes
    public Tweet(String text, int count) {
        this.body = text;
        this.likeCount = count;
    }

    public String getText() {
        return body;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void likeTweet() {
        likeCount++;
    }

    public void unlikeTweet() {
        if (likeCount > 0) {
            likeCount--;
        } else {
            likeCount = 0;
        }
    }

    public String tweetInfo() {
        String info;
        info = "Tweet" + " " + getText() + " " + "Like Count is" + " " + getLikeCount();
        return info;
    }

}
