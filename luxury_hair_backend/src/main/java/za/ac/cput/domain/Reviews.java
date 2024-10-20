package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class Reviews {

    @Id
    private String reviewId;
    private String ReviewerName;
    private String ReviewDescription;
    private String ReviewDate;
    private int Rating;
    @ManyToOne
    @JoinColumn(name="productId",referencedColumnName = "productid")
    private Product product;


    public Reviews() {
    }

    public Reviews(Builder builder) {
        this.reviewId = builder.reviewId;
        this.ReviewerName = builder.ReviewerName;
        this.ReviewDescription = builder.ReviewDescription;
        this.Rating = builder.Rating;
        this.ReviewDate = builder.ReviewDate;
        this.product = builder.product;
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public String getReviewerName() {
        return ReviewerName;
    }

    public String getReviewDescription() {
        return ReviewDescription;
    }

    public String getReviewDate() {
        return ReviewDate;
    }

    public int getRating() {
        return Rating;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reviews reviews)) return false;
        return Rating == reviews.Rating &&
                Objects.equals(reviewId, reviews.reviewId) &&
                Objects.equals(ReviewerName, reviews.ReviewerName) &&
                Objects.equals(ReviewDescription, reviews.ReviewDescription) &&
                Objects.equals(ReviewDate, reviews.ReviewDate) &&
                product == reviews.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, ReviewerName, ReviewDescription, ReviewDate, Rating, product);  // Include productId in hashCode
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviewId='" + reviewId + '\'' +
                ", ReviewerName='" + ReviewerName + '\'' +
                ", ReviewDescription='" + ReviewDescription + '\'' +
                ", ReviewDate='" + ReviewDate + '\'' +
                ", Rating=" + Rating +
                ", productId=" + product +
                '}';
    }

    public static class Builder {
        private String reviewId;
        private String ReviewerName;
        private String ReviewDescription;
        private String ReviewDate;
        private int Rating;
        private  Product product;

        public Builder setReviewId(String reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder setReviewerName(String ReviewerName) {
            this.ReviewerName = ReviewerName;
            return this;
        }

        public Builder setReviewDescription(String ReviewDescription) {
            this.ReviewDescription = ReviewDescription;
            return this;
        }

        public Builder setReviewDate(String ReviewDate) {
            this.ReviewDate = ReviewDate;
            return this;
        }

        public Builder setRating(int Rating) {
            this.Rating = Rating;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product =  product;
            return this;
        }

        public Builder copy(Reviews reviews) {
            this.reviewId = reviews.reviewId;
            this.ReviewerName = reviews.ReviewerName;
            this.ReviewDescription = reviews.ReviewDescription;
            this.ReviewDate = reviews.ReviewDate;
            this.Rating = reviews.Rating;
            this.product =  reviews.product;
            return this;
        }

        public Reviews build() {
            return new Reviews(this);
        }
    }
}
