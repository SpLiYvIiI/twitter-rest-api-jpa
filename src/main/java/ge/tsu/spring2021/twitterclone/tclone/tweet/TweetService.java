package ge.tsu.spring2021.twitterclone.tclone.tweet;

import ge.tsu.spring2021.twitterclone.tclone.user.User;
import ge.tsu.spring2021.twitterclone.tclone.user.UserRepository;
import ge.tsu.spring2021.twitterclone.tclone.utils.JwtUtils;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public List<Tweet> getTweets(String JwtToken){
        String userName = jwtUtils.extractUsername(JwtToken);
        List <Tweet> all = tweetRepository.findAll();
        List <Tweet> filtered = new ArrayList<>();
        for(Tweet t : all){
            if(t.getUser().getUserName().equals(userName))
                filtered.add(t);
        }
        return filtered;
    }
    public Tweet getTweet(long id,String JwtToken) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtToken);
        Tweet t = tweetRepository.findById(id);
        if(t == null) throw new RecordNotFoundException("Tweet with such id doesnt exist");
        return t;
    }
    public void postTweet(Tweet newTweet,String JwtString){
        String userName = jwtUtils.extractUsername(JwtString);
        User u = userRepository.findByUserName(userName);
        tweetRepository.save(new Tweet(newTweet.getTweet_title(), newTweet.getTweet_text(), u));
    }
    public void deleteTweet(long id, String JwtString) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtString);
        Tweet check = tweetRepository.findById(id);
        if(check == null) throw new RecordNotFoundException("Tweet with such id doesnt exist");
        else if(check.getUser().getUserName().equals(userName))
                tweetRepository.deleteById(id);

    }

    public void editTweet(long id,Tweet newTweet, String JwtString) throws RecordNotFoundException {
        String userName = jwtUtils.extractUsername(JwtString);
        Tweet toChange = tweetRepository.findById(id);
        if(toChange == null) throw new RecordNotFoundException("Tweet with such id doesnt exist");
        else if(toChange.getUser().getUserName().equals(userName)){
            toChange.setTweet_title(newTweet.getTweet_title());
            toChange.setTweet_text(newTweet.getTweet_text());
            tweetRepository.save(toChange);
        }
    }
}
