import { useEffect, useState } from "react";
import "../assets/style.css";
import "../assets/products.css";

const FeaturedProducts = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

 
  useEffect(() => {
    const baseUrl = import.meta.env.VITE_BACK_END_URL;

    
    const token = localStorage.getItem("token");

    const fetchProducts = async () => {
      try {
        const response = await fetch(`${baseUrl}/product/getall`, {
          headers: {
           
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }

        const data = await response.json();
        setProducts(data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading) {
    return <p>Loading products...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <section id="products" className="products">
      <h2>Featured Products</h2>
      <div className="product-list">
        {products.slice(0, 3).map((product) => (
          <div key={product.productId} className="product-card">
            {}
            {product.image ? (
              <img
                src={`data:image/jpeg;base64,${product.image}`}
                alt={product.hairStyle}
                className="product-image"
              />
            ) : (
              <p>No Image Available</p>
            )}
            <h3>{product.hairStyle}</h3>
            <p>R{product.hairPrice.toFixed(2)}</p>
          </div>
        ))}
      </div>
    </section>
  );
};

export default FeaturedProducts;
