package ge.tsu.spring2021.twitterclone.tclone.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findById(long id);
    void deleteById(long id);
}
