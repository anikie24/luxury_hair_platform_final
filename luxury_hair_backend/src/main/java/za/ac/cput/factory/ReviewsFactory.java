package za.ac.cput.factory;

import za.ac.cput.domain.Reviews;
import za.ac.cput.util.Helper;

public class ReviewsFactory {
    public static Reviews buildReviews(String ReviewId,String ReviewerName,String ReviewDescription,String ReviewDate,int Rating) {
        if(Helper.isNullOrEmpty(ReviewId)||Helper.isNullOrEmpty(ReviewerName)||Helper.isNullOrEmpty(ReviewDescription)||Helper.isNullOrEmpty(ReviewDate)||Helper.isNullOrEmpty(String.valueOf(Rating)))
            return null;
        return new Reviews.Builder().setReviewId(ReviewId)
                .setReviewerName(ReviewerName)
                .setReviewDescription(ReviewDescription)
                .setReviewDate(ReviewDate)
                .setRating(Rating).build();

    }
}