package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new Account("Sean", new ArrayList<String>(), new ArrayList<String>());

    }

    @Test
    void testConstructor() {
        assertEquals("Sean", testAccount.getName());
        assertEquals(new ArrayList<String>(), testAccount.getFollowing());
        assertEquals(new ArrayList<String>(), testAccount.getFollowers());
        assertEquals(0, testAccount.followerCount());
        assertEquals(0, testAccount.followingCount());


    }

    @Test
    void testFollowers() {
        testAccount.addFollowers("Steve");
        testAccount.addFollowers("John");
        assertEquals(2, testAccount.followerCount());
        assertTrue(testAccount.followerList.contains("Steve"));
        assertTrue(testAccount.followerList.contains("John"));
    }

    @Test
    void testFollowing() {
        testAccount.addFollowing("Ben");
        testAccount.addFollowing("Friend");
        testAccount.addFollowing("Friend2");
        assertEquals(3, testAccount.followingCount());
        assertTrue(testAccount.followingList.contains("Ben"));
        assertTrue(testAccount.followingList.contains("Friend"));
        assertTrue(testAccount.followingList.contains("Friend2"));
    }

    @Test
    void testInformationCompiled() {
        testAccount.addFollowing("Ben");
        testAccount.addFollowing("Friend");
        testAccount.addFollowing("Friend2");
        testAccount.addFollowers("Ben");
        testAccount.addFollowers("Friend");
        testAccount.addFollowers("Friend2");
        assertEquals("Hi Sean You have 3 Followers and 3 Following", testAccount.informationCompiled());
    }

    @Test
    void testGetName() {
        testAccount.addFollowing("Ben");
        testAccount.addFollowing("Friend");
        testAccount.addFollowing("Friend2");
        assertEquals("Sean", testAccount.getName());
    }

    @Test
    void addFunction() {
        testAccount.addFollowing("Ben");
        testAccount.addFollowing("Friend");
        testAccount.addFollowing("Friend2");
        testAccount.addFollowers("Ben");
        testAccount.addFollowers("Friend");
        testAccount.addFollowers("Friend2");
        assertTrue(testAccount.getFollowing().contains("Ben"));
        assertTrue(testAccount.getFollowing().contains("Friend"));
        assertTrue(testAccount.getFollowing().contains("Friend2"));
        assertTrue(testAccount.getFollowers().contains("Ben"));
        assertTrue(testAccount.getFollowers().contains("Friend"));
        assertTrue(testAccount.getFollowers().contains("Friend2"));

    }

}