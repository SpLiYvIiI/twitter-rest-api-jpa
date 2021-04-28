package ge.tsu.spring2021.twitterclone.tclone.comment;

import ge.tsu.spring2021.twitterclone.tclone.tweet.Tweet;
import ge.tsu.spring2021.twitterclone.tclone.tweet.TweetRepository;
import ge.tsu.spring2021.twitterclone.tclone.user.User;
import ge.tsu.spring2021.twitterclone.tclone.user.UserRepository;
import ge.tsu.spring2021.twitterclone.tclone.utils.JwtUtils;
import ge.tsu.spring2021.twitterclone.tclone.utils.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public List<Comment> getComments(long id,String JwtToken){
        String userName = jwtUtils.extractUsername(JwtToken);
        List <Comment> allComments = commentRepository.findAll();
        List <Comment> filtered = new ArrayList<>();
        for(Comment c : allComments){
            if(c.getUser().getUserName().equals(userName) && c.getTweet().getTweet_id() == id)
                filtered.add(c);
        }
        return filtered;
    }
    public Comment getComment(long tweetId,long commentId,String JwtToken) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtToken);
        Comment c = commentRepository.findById(commentId);
        if(c == null) throw new RecordNotFoundException("Comment with that id doesnt exist");
        if(c.getTweet().getTweet_id() == tweetId) return c;
        else throw new RecordNotFoundException("Tweet doesnt have comment with that id");
    }

    public void postComment(long id,Comment comment,String JwtToken) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtToken);
        Tweet tweet = tweetRepository.findById(id);
        User user = userRepository.findByUserName(userName);
        if(tweet == null) throw new RecordNotFoundException("Tweet with that id doesnt exist");
        else{
            commentRepository.save(new Comment(comment.getComment_text(),tweet,user));
        }
    }
    public void deleteComment(long tweetId, long commentId, String JwtToken) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtToken);
        User u = userRepository.findByUserName(userName);
        Comment c = commentRepository.findById(commentId);
        if(c == null) throw new RecordNotFoundException("Comment with that id doesnt exist");
        if(u.getUser_id() != c.getUser().getUser_id() || tweetId != c.getTweet().getTweet_id()) return;
        commentRepository.deleteById(commentId);
    }
    public void editComment(long tweetId,long commentId,Comment newComment,String JwtToken) throws RecordNotFoundException{
        String userName = jwtUtils.extractUsername(JwtToken);
        User u = userRepository.findByUserName(userName);
        Comment toChange = commentRepository.findById(commentId);
        if(toChange == null) throw new RecordNotFoundException("Comment with that id doesnt exist");
        if(toChange.getUser().getUser_id() != u.getUser_id() || toChange.getTweet().getTweet_id() != tweetId) return;
        toChange.setComment_text(newComment.getComment_text());
        commentRepository.save(toChange);
    }
}
