package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Reviews;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,String> {
    //Reviews findReviewsByReviewsId(String ReviewId);

    Reviews findReviewsByReviewId(String r2435);

    List<Reviews> findByProduct_ProductId(Long productId);
}