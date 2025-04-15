package domain;

public class Comment {
    private String productId;
    private String username;
    private int rating;
    private String comment;

    public Comment() {
        this.productId = "";
        this.username = "";
        this.rating = 0;
        this.comment = "";
    }

    // Getters and setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}