package da;

import domain.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentDA {
    private static List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }
}