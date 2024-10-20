package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Reviews;
import za.ac.cput.services.ProductService;
import za.ac.cput.services.ReviewsService;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;
    private final ProductService productService;
    @Autowired
    public ReviewsController(ReviewsService reviewsService,ProductService productService) {
        this.reviewsService = reviewsService;
        this.productService= productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Reviews> createReview(
            @RequestParam("reviewId") String reviewId,
            @RequestParam("ReviewerName") String reviewerName,
            @RequestParam("Rating") int rating,
            @RequestParam("ReviewDate") String reviewDate,
            @RequestParam("ReviewDescription") String reviewDescription,
            @RequestParam("ProductId")Long productId
    ) {


        Product product =productService.read(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Reviews reviews = new Reviews.Builder()
                .setReviewId(reviewId)
                .setReviewerName(reviewerName)
                .setRating(rating)
                .setReviewDate(reviewDate)
                .setReviewDescription(reviewDescription)
                .setProduct(product)
                .build();

        Reviews createdReview = reviewsService.create(reviews);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }



    @GetMapping("/read/{productId}")
    public ResponseEntity <List<Reviews> >read(@PathVariable Long productId) {
        List<Reviews>review = reviewsService.getReviewsByProduct(productId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/update")
    public ResponseEntity<Reviews> update(@RequestBody Reviews reviews) {
        Reviews updatedReview = reviewsService.update(reviews);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Reviews>> getAllReviews() {
        List<Reviews> reviews = reviewsService.getall();
        return reviews != null && !reviews.isEmpty()
                ? ResponseEntity.ok(reviews)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
    }
    public static class ReviewsRequest{
        private Product product;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Reviews>> getProductReviews(@PathVariable Long productId) {
        List<Reviews> reviews = reviewsService.getReviewsByProduct(productId);
        return reviews.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reviews);
    }

}
