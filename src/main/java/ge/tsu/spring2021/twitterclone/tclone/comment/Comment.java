package ge.tsu.spring2021.twitterclone.tclone.comment;


import ge.tsu.spring2021.twitterclone.tclone.tweet.Tweet;
import ge.tsu.spring2021.twitterclone.tclone.user.User;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comment_id;
    private String comment_text;
    @ManyToOne(targetEntity = Tweet.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "tweetId")
    private Tweet tweet;
    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private User user;

    public Comment(){

    }

    public Comment(String comment_text, Tweet tweet, User user) {
        this.comment_text = comment_text;
        this.tweet = tweet;
        this.user = user;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getComment_id() {
        return comment_id;
    }
}
