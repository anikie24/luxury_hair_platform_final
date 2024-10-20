import { useState, useEffect } from "react";
import "../assets/style.css";
import "../assets/Reviews.css";
import Navbar from "./Navbar";
import Footer from "./Footer";
import { useParams, useNavigate } from "react-router-dom";

const baseUrl = import.meta.env.VITE_BACK_END_URL;

const Reviews = () => {
  const { id } = useParams(); 
  const navigate = useNavigate();

  
  const [name, setName] = useState("");
  const [rating, setRating] = useState(5);
  const [reviewText, setReviewText] = useState("");
  const [reviews, setReviews] = useState([]);
  const [averageRating, setAverageRating] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  
  const token = localStorage.getItem("token");

  
  useEffect(() => {
    const fetchReviews = async () => {
      setLoading(true);
      setError("");
      try {
        const response = await fetch(`${baseUrl}/reviews/read/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`, 
          },
        });

        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log("Fetched reviews data:", data);
        setReviews(data);

        const totalRating = data.reduce(
          (acc, review) => acc + review.rating,
          0
        );
        const avgRating = data.length > 0 ? totalRating / data.length : 0;
        setAverageRating(avgRating);
      } catch (error) {
        console.error("Error fetching reviews:", error);
        if (error.message.includes("401")) {
          
          navigate("/login");
        } else {
          setError("Failed to load reviews. Please try again later.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchReviews();
  }, [id, token, navigate]);

  
  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("reviewId", Math.random().toString(36).substring(2, 15));
    formData.append("ReviewerName", name);
    formData.append("Rating", rating);
    formData.append("ReviewDate", new Date().toISOString().split("T")[0]);
    formData.append("ReviewDescription", reviewText);
    formData.append("ProductId", id); 

    try {
      setLoading(true);
      const response = await fetch(`${baseUrl}/reviews/create`, {
        method: "POST",
        body: formData,
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setReviews((prevReviews) => [...prevReviews, data]);

      const newTotalRating =
        reviews.reduce((acc, review) => acc + review.rating, 0) + rating;
      setAverageRating(newTotalRating / (reviews.length + 1));
      resetForm();
    } catch (error) {
      console.error("Error submitting review:", error);
      if (error.message.includes("401")) {
        
        navigate("/login");
      } else {
        alert("Failed to submit review");
      }
    } finally {
      setLoading(false);
    }
  };

  
  const resetForm = () => {
    setName("");
    setRating(5);
    setReviewText("");
  };

  return (
    <div className="reviews-page">
      <Navbar />
      <header className="header"></header>
      <main className="main-content">
        <section className="review-form-section">
          <button className="back-button" onClick={() => navigate(-1)}>
            ← Back
          </button>
          <h2>Leave Us a Review</h2>
          {error && <p className="error-message">{error}</p>}
          <form onSubmit={handleSubmit} className="review-form">
            <div className="form-group">
              <label htmlFor="name">Name:</label>
              <input
                id="name"
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Your Name"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="rating">Rate Our Services:</label>
              <select
                id="rating"
                value={rating}
                onChange={(e) => setRating(Number(e.target.value))}
                required
              >
                {[1, 2, 3, 4, 5].map((num) => (
                  <option key={num} value={num}>
                    {"★".repeat(num) + "☆".repeat(5 - num)} ({num}/5)
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="reviewText">Review:</label>
              <textarea
                id="reviewText"
                value={reviewText}
                onChange={(e) => setReviewText(e.target.value)}
                placeholder="Write your review here..."
                required
              />
            </div>
            <button type="submit" disabled={loading}>
              {loading ? "Submitting..." : "Submit Review"}
            </button>
          </form>
        </section>

        <section className="existing-reviews-section">
          <h2>Existing Reviews</h2>
          {loading ? (
            <p>Loading reviews...</p>
          ) : (
            <>
              {averageRating !== null && (
                <p>
                  Average Rating: {averageRating.toFixed(1)} ({reviews.length}{" "}
                  review{reviews.length !== 1 ? "s" : ""})
                </p>
              )}
              {reviews.length > 0 ? (
                reviews.map((review) => (
                  <div key={review.review_Id} className="review-card">
                    <div className="review-header">
                      <h3>{review.reviewerName || "Name is missing"}</h3>
                      <div className="review-rating">
                        {"★".repeat(review.rating || 0) +
                          "☆".repeat(5 - review.rating || 0)}
                        ({review.rating || "0"}/5)
                      </div>
                      <span className="review-date">
                        {review.reviewDate
                          ? new Date(review.reviewDate).toLocaleDateString()
                          : "Date is missing"}
                      </span>
                    </div>
                    <p>
                      {review.reviewDescription ||
                        "No review description provided."}
                    </p>
                  </div>
                ))
              ) : (
                <p>
                  No reviews for this product yet. Be the first to leave a
                  review.
                </p>
              )}
            </>
          )}
        </section>
      </main>
      <Footer />
    </div>
  );
};

export default Reviews;
