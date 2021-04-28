package ge.tsu.spring2021.twitterclone.tclone.user;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"followings","followers"})
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long user_id;
    private String userName;
    private String password;
    private String email;
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="customer_following",
            joinColumns={@JoinColumn(name="customer_id")},
            inverseJoinColumns={@JoinColumn(name="following_id")})
    private List<User> followings = new ArrayList<>();
    @ManyToMany(mappedBy="followings", cascade={CascadeType.ALL})
    private List<User> followers = new ArrayList<>();

    public User(){

    }

    public Long getUser_id() {
        return user_id;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonIgnore
    @JsonProperty(value = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getFollowings() {
        return followings;
    }

    public void setFollowings(List<User> followings) {
        this.followings = followings;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}