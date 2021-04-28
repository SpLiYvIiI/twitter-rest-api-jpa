package ge.tsu.spring2021.twitterclone.tclone.tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Tweet findById(long id);
    void deleteById(long id);
}