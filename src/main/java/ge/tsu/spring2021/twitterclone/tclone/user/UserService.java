package ge.tsu.spring2021.twitterclone.tclone.user;

import ge.tsu.spring2021.twitterclone.tclone.tweet.Tweet;
import ge.tsu.spring2021.twitterclone.tclone.tweet.TweetRepository;
import ge.tsu.spring2021.twitterclone.tclone.utils.JwtUtils;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.RecordNotFoundException;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getFollowers(String JwtString) {
        String userName = jwtUtils.extractUsername(JwtString);
        User toFind = userRepository.findByUserName(userName);
        return toFind.getFollowers();
    }
    public User getFollower(long id,String JwtString) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtString);
        User u = userRepository.findByUserName(userName);
        for(User v : u.getFollowers())
            if(v.getUser_id() == id) return v;

        throw new RecordNotFoundException("Follower with such id doesnt exist");
    }

    public List <User> getFollowing(String JwtString){
        String userName = jwtUtils.extractUsername(JwtString);
        User toFind = userRepository.findByUserName(userName);
        return toFind.getFollowings();
    }

    public User getFollowingById(long id,String JwtString) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtString);
        User u = userRepository.findByUserName(userName);
        for(User v : u.getFollowings())
            if(v.getUser_id() == id) return v;

        throw new RecordNotFoundException("Follower with such id doesnt exist");
    }

    public void followUser(long following,String JwtString) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtString);
        User u1 = userRepository.findByUserName(userName);
        User u2 = userRepository.findById(following);
        if(u1 == null) throw new RecordNotFoundException("user with such id doesnt exist");
        for(User u : u1.getFollowings()){
            if(u.getUser_id() == following)
                return;
        }
        u1.getFollowings().add(u2);
        userRepository.save(u1);
    }
    public void unfollow(long toUnfollow,String JwtString){
        String userName = jwtUtils.extractUsername(JwtString);
        User toChange = userRepository.findByUserName(userName);
        List <User> followings = toChange.getFollowings();
        for(int i = 0;  i < followings.size(); i++){
            if(followings.get(i).getUser_id() == toUnfollow)
                followings.remove(i);
        }
        toChange.setFollowings(followings);
        userRepository.save(toChange);
    }
    public void editUser(User user , String JwtString){
        String userName = jwtUtils.extractUsername(JwtString);
        User toChange = userRepository.findByUserName(userName);
        toChange.setUserName(user.getUserName());
        toChange.setEmail(user.getEmail());
        toChange.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(toChange);
    }
    public List<Tweet> userTimeLine(String JwtString) {
        String userName = jwtUtils.extractUsername(JwtString);
        User u = userRepository.findByUserName(userName);
        List <Tweet> allTweets = tweetRepository.findAll();
        List <Tweet> timeLine = new ArrayList<>();
        List <User> following = u.getFollowings();
        Set<Long> followings = new HashSet<>();
        for(User v : following) followings.add(v.getUser_id());
        for(Tweet t : allTweets){
            if(followings.contains(t.getUser().getUser_id()) || t.getUser().getUser_id() == u.getUser_id())
                timeLine.add(t);
        }
        return timeLine;
    }
}
