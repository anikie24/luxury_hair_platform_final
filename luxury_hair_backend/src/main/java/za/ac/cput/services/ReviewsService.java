package za.ac.cput.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Reviews;
import za.ac.cput.repository.ReviewsRepository;

import java.util.List;

@Service
public class ReviewsService {

    private ReviewsRepository reviewsRepository;
    @Autowired
    public ReviewsService(ReviewsRepository repo) {
        this.reviewsRepository = repo;
    }

    private ReviewsService(){

    }
    public Reviews create(Reviews reviews){

        return reviewsRepository.save(reviews);
    }

    public Reviews read(String ReviewId){
        return reviewsRepository.findById(ReviewId).orElse(null);
    }

    public Reviews update(Reviews reviews){
        return reviewsRepository.save(reviews);
    }
    public List<Reviews> getall(){
        return reviewsRepository.findAll();
    }
    public List<Reviews> getReviewsByProduct(Long productId) {
        return reviewsRepository.findByProduct_ProductId(productId);
    }
}


