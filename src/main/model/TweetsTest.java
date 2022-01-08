package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TweetsTest {
    private Tweet testTweet;

    @BeforeEach
    void runBefore() {
        testTweet = new Tweet("Hello", 0);

    }

    @Test
    void testConstructor() {
        assertEquals("Hello", testTweet.getText());
        assertEquals(0, testTweet.getLikeCount());

    }

    @Test
    void testLike() {
        testTweet.likeTweet();
        assertEquals(1, testTweet.getLikeCount());
        testTweet.likeTweet();
        assertEquals(2, testTweet.getLikeCount());
        testTweet.likeTweet();
        assertEquals(3, testTweet.getLikeCount());
        testTweet.unlikeTweet();
        assertEquals(2, testTweet.getLikeCount());
        testTweet.unlikeTweet();
        assertEquals(1, testTweet.getLikeCount());
        testTweet.unlikeTweet();
        assertEquals(0, testTweet.getLikeCount());
        testTweet.unlikeTweet();
        assertEquals(0, testTweet.getLikeCount());


    }

    @Test
    void infoCompile() {
        testTweet.unlikeTweet();
        testTweet.unlikeTweet();
        assertEquals("Tweet Hello Like Count is 0", testTweet.tweetInfo());
        testTweet.likeTweet();
        assertEquals("Tweet Hello Like Count is 1", testTweet.tweetInfo());
        testTweet.likeTweet();
        assertEquals("Tweet Hello Like Count is 2", testTweet.tweetInfo());
        testTweet.likeTweet();
        assertEquals("Tweet Hello Like Count is 3", testTweet.tweetInfo());
        testTweet.unlikeTweet();
        assertEquals("Tweet Hello Like Count is 2", testTweet.tweetInfo());


    }
}
