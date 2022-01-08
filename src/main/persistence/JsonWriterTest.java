package persistence;


import model.Twitter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonWriterTest extends JsonTest {
//NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
//write data to a file and then use the reader to read it back in and check that we
//read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Twitter t = new Twitter();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTwitter() {
        try {
            Twitter t = new Twitter();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTwitter.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTwitter.json");
            t = reader.read();
            assertEquals("Shaon", t.getAccount().getName());
            assertEquals(0, t.getAccount().followingCount());
            assertEquals(0, t.getAccount().followerCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTwitter() {
        try {
            Twitter t = new Twitter();
            t.getAccount().addFollowing("John");
            t.getAccount().addFollowing("Smith");
            t.getAccount().addFollowing("Adam");
            t.getAccount().addFollowers("Adam");
            t.getAccount().addFollowers("Goldberg");
            t.getAccount().addFollowers("Joe");
            t.getAccount().addFollowers("Steve");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTwitter.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTwitter.json");
            t = reader.read();
            assertEquals("Shaon", t.getAccount().getName());
            assertEquals(4, t.getAccount().followingCount());
            assertEquals(4, t.getAccount().followerCount());
            assertTrue(t.getAccount().getFollowers().contains("Adam"));
            assertTrue(t.getAccount().getFollowing().contains("Joe"));



        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
