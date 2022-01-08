package persistence;


import model.Twitter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest  extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Twitter t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTwitter() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTwitter.json");
        try {
            Twitter t = reader.read();
            assertEquals("Sean", t.getAccount().getName());
            assertEquals(0, t.getAccount().followerCount());
            assertEquals(new ArrayList<>(), t.getTweets());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTwitter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTwitter.json");
        try {
            Twitter t = reader.read();
            assertEquals("Sean", t.getAccount().getName());
            assertEquals(3, t.getAccount().followingCount());
            assertEquals(4, t.getAccount().followerCount());
            assertTrue(t.getAccount().getFollowers().contains("Adam"));
            assertTrue(t.getAccount().getFollowing().contains("Smith"));
            assertTrue(t.getAccount().getFollowing().contains("Adam"));
            assertTrue(t.getTweets().contains("Shaon"));
            assertTrue(t.getTweets().contains("Abhay"));
            assertTrue(t.getTweets().contains("What"));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }




}