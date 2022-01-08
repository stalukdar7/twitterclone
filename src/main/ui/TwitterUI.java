package ui;

import model.Event;
import model.EventLog;
import model.Tweet;
import model.Twitter;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TwitterUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Twitter twitter;
    private JDesktopPane desktop;
    private JInternalFrame twitterScreen;
    private static final String JSON_STORE = "./data/twitter.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    ImageIcon cpsc110 = new ImageIcon("./data/cpsc110.png");

    public TwitterUI() {
        twitter = new Twitter();
        desktop = new JDesktopPane();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        desktop.addMouseListener(new DesktopFocusAction());
        twitterScreen = new JInternalFrame("Twitter Screen", false, false, false, false);
        twitterScreen.setLayout(new BorderLayout());
        addButtonPanel();

        setContentPane(desktop);
        setTitle("CPSC 210 Personal Project: Twitter by Shaon Talukdar");
        setSize(WIDTH, HEIGHT);


        twitterScreen.pack();
        twitterScreen.setVisible(true);
        desktop.add(twitterScreen);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
            }
        });

        centreOnScreen();
        setVisible(true);
    }


    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.add(new JButton(new CheckAccountAction()));
        buttonPanel.add(new JButton(new TweetAction()));
        buttonPanel.add(new JButton(new SaveAccountAction()));
        buttonPanel.add(new JButton(new LoadAccountAction()));
        buttonPanel.add(new JButton(new PrintLogAction()));
        twitterScreen.add(buttonPanel, BorderLayout.WEST);

    }

    /*
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Print log");
        }

        public void actionPerformed(ActionEvent evt) {
            printLog(EventLog.getInstance());
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    private class CheckAccountAction extends AbstractAction {
        JInternalFrame addFrame;

        CheckAccountAction() {
            super("Check my Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            addFrame = new JInternalFrame("Account Options", true, true, false);
            addFrame.setLayout(new GridLayout(3, 2));
            addFrame.add(new JButton(new accountInfoCompiled()));
            addFrame.add(new JButton(new checkFollowerCount()));
            addFrame.add(new JButton(new checkFollowingCount()));
            addFrame.add(new JButton(new checkFollowers()));
            addFrame.add(new JButton(new checkFollowing()));
            addFrame.add(new JButton(new addFollowersFollowing()));
            addFrame.pack();
            addFrame.setVisible(true);
            desktop.add(addFrame);


        }


        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class accountInfoCompiled extends AbstractAction {
            JInternalFrame output;

            accountInfoCompiled() {
                super("Account Information Compiled");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                output = new JInternalFrame("Account Information Compiled", true, true, false);
                JLabel i = new JLabel(twitter.getAccount().informationCompiled());
                output.add(i);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class checkFollowerCount extends AbstractAction {
            JInternalFrame output;

            checkFollowerCount() {
                super("Check Follower Count");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                int i = twitter.getAccount().followerCount();
                String s = String.valueOf(i);
                output = new JInternalFrame("Follower Count", true, true, false);
                JLabel f = new JLabel(s);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class checkFollowingCount extends AbstractAction {
            JInternalFrame output;

            checkFollowingCount() {
                super("Check Following Count");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                int i = twitter.getAccount().followingCount();
                String s = String.valueOf(i);
                output = new JInternalFrame("Following Count", true, true, false);
                JLabel f = new JLabel(s);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class checkFollowers extends AbstractAction {
            JInternalFrame output;

            checkFollowers() {
                super("Check Followers");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                output = new JInternalFrame("Followers", true, true, false);
                String s = null;
                s = listToString(twitter.getAccount().getFollowers());
                JLabel f = new JLabel(s);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class checkFollowing extends AbstractAction {
            JInternalFrame output;

            checkFollowing() {
                super("Check Following");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                output = new JInternalFrame("Following", false, true, true, false);
                String s = null;
                s = listToString(twitter.getAccount().getFollowing());
                JLabel f = new JLabel(s);
                output.add(f);

                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class addFollowersFollowing extends AbstractAction {
            JInternalFrame addFrame;
            JTextField followName;
            JComboBox<String> printCombo;

            addFollowersFollowing() {
                super("Add Follower/Following");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                addFrame = new JInternalFrame("Add Follower/Following", true, true, false);
                addFrame.setLayout(new GridLayout(3, 3));
                addFrame.add(new JLabel("Follow or Following"));
                printCombo = new JComboBox<String>();
                printCombo.addItem("Follower");
                printCombo.addItem("Following");
                addFrame.add(printCombo);


                addFrame.add(new JLabel("Type Name in"));
                followName = new JTextField();
                addFrame.add(followName);
                addFrame.add(new JButton(new newAddition()));

                addFrame.pack();
                addFrame.setVisible(true);
                desktop.add(addFrame);

            }

            private class newAddition extends AbstractAction {
                String selected = (String) printCombo.getSelectedItem();
                JInternalFrame confirmation;

                newAddition() {
                    super("Done");
                }

                @Override
                public void actionPerformed(ActionEvent evt) {
                    confirmation = new JInternalFrame("Confirmation", true, true, false);
                    confirmation.setLayout(new GridLayout(1, 2));
                    String addName = followName.getText();
                    if (selected == "Follower") {
                        twitter.getAccount().addFollowers(addName);
                        confirmation.add(new JLabel("Follower Successfully Added" + " " + addName));
                        confirmation.add(new JLabel(cpsc110));
                    } else if (selected == "Following") {
                        twitter.getAccount().addFollowing(addName);
                        confirmation.add(new JLabel("Following Successfully Added" + " " + addName));
                        confirmation.add(new JLabel(cpsc110));
                    }
                    confirmation.pack();
                    confirmation.setVisible(true);
                    desktop.add(confirmation);
                }


            }
        }


    }


    private class TweetAction extends AbstractAction {
        JInternalFrame addFrame;

        TweetAction() {
            super("Tweet Actions");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            addFrame = new JInternalFrame("Account Options");
            addFrame.setLayout(new GridLayout(2, 2));
            addFrame.add(new JButton(new newTweetActions()));
            addFrame.add(new JButton(new historyTweetActions()));
            addFrame.add(new JButton(new likeTweetActions()));
            //  addFrame.add(new JButton(new statsTweetOptions()));
            addFrame.pack();
            addFrame.setVisible(true);
            desktop.add(addFrame);
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class newTweetActions extends AbstractAction {
            JInternalFrame output;
            JTextField newTweet;

            newTweetActions() {
                super("New Tweet");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                addFrame = new JInternalFrame("New Tweet", true, true, false);
                addFrame.setLayout(new GridLayout(3, 2));
                addFrame.add(new JLabel("Enter new Tweet"));
                newTweet = new JTextField();
                addFrame.add(newTweet);
                addFrame.add(new JButton("Done"));
                Tweet tweet = new Tweet(newTweet.getText(), 0);
                twitter.getTweets().add(twitter.getTweets().size(), tweet);
            }
        }


        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class historyTweetActions extends AbstractAction {
            JInternalFrame output;

            historyTweetActions() {
                super("Tweet History");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                output = new JInternalFrame("Tweet History", true, true, false);
                String stringTweets = twitterToString(twitter);
                JLabel f = new JLabel(stringTweets);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

        @SuppressWarnings({"checkstyle:TypeName", "checkstyle:SuppressWarnings"})
        private class likeTweetActions extends AbstractAction {
            JInternalFrame output;

            likeTweetActions() {
                super("like/unlike tweet");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                output = new JInternalFrame("like/unlike tweet", true, true, false);
                for (int i = 0; i < twitter.getTweets().size(); i++) {
                    JLabel f = new JLabel(twitter.getTweets().get(i).getText());
                    output.add(f);
                }
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }

/*
        private class statsTweetOptions extends AbstractAction {
            JInternalFrame output;

            statsTweetOptions() {
                super("Tweet Stats");
            }

            @Override
            public void actionPerformed(ActionEvent evt) {
                int i = twitter.getAccount().followerCount();
                String s = String.valueOf(i);
                output = new JInternalFrame("Tweet Stats", true, true, false);
                JLabel f = new JLabel(s);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }
        }
*/
    }


    private class SaveAccountAction extends AbstractAction {
        JInternalFrame output;

        SaveAccountAction() {
            super("Save Account to File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                output = new JInternalFrame("Save Operation", false, true, true, false);
                jsonWriter.open();
                jsonWriter.write(twitter);
                jsonWriter.close();
                JLabel f = new JLabel("Saved " + twitter.getAccount().getName() + " to " + JSON_STORE);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            } catch (FileNotFoundException e) {
                output = new JInternalFrame("Save Operation", false, true, true, false);
                JLabel f = new JLabel("Unable to write to file: " + JSON_STORE);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }

        }

    }

    private class LoadAccountAction extends AbstractAction {
        JInternalFrame output;

        LoadAccountAction() {
            super("Load Account to File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                output = new JInternalFrame("Save Operation", false, true, true, false);
                twitter = jsonReader.read();
                JLabel f = new JLabel("Loaded " + twitter.getAccount().getName() + " from " + JSON_STORE);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            } catch (IOException e) {
                output = new JInternalFrame("Save Operation", false, true, true, false);
                JLabel f = new JLabel("Unable to read from file: " + JSON_STORE);
                output.add(f);
                output.pack();
                output.setVisible(true);
                desktop.add(output);
            }

        }
    }


    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private String twitterToString(Twitter t) {
        String Tweets = " ";

        for (int i = 0; i < t.getTweets().size(); i++) {
            Tweets.concat(t.getTweets().get(i).getText());
            Tweets.concat(" ");
        }
        return Tweets;
    }

    public String listToString(ArrayList<String> l) {
        String list = "";

        for (int i = 0; i < l.size(); i++) {
            list.concat(l.get(i));
            list.concat(" ");
        }
        return list;
    }


    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            TwitterUI.this.requestFocusInWindow();
        }
    }
}

