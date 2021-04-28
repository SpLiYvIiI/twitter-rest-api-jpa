package ge.tsu.spring2021.twitterclone.tclone.tweet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ge.tsu.spring2021.twitterclone.tclone.user.User;

import javax.persistence.*;

@Entity
@Table(name = "tweets")
@JsonIgnoreProperties("user")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tweet_id;
    private String tweet_title;
    private String tweet_text;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private User user;

    public Tweet(){

    }

    public Tweet(String tweet_title, String tweet_text, User user) {
        this.tweet_title = tweet_title;
        this.tweet_text = tweet_text;
        this.user = user;
    }

    public String getTweet_title() {
        return tweet_title;
    }

    public void setTweet_title(String tweet_title) {
        this.tweet_title = tweet_title;
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTweet_id() {
        return tweet_id;
    }
}
