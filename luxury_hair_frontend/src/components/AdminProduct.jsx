import { useEffect, useState } from "react";
//import Navbar from "./Navbar";
import Navbar from "./AdminNavbar";
import Footer from "./Footer";
import productService from "../Services/ProductService";
import "../assets/style.css";
import "../assets/products.css";

const AdminProduct = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newProduct, setNewProduct] = useState({
    hairTexture: "",
    hairStyle: "",
    hairSize: "",
    hairColor: "",
    hairStock: true,
    hairPrice: "",
    image: null,
  });
  const [editingProduct, setEditingProduct] = useState(null);

  
  const getToken = () => {
    return localStorage.getItem("token");
  };

 
  const fetchProducts = async () => {
    try {
      const token = getToken();
      if (!token) throw new Error("Unauthorized. Please login first.");

      const data = await productService.getAllProducts(token); 
      setProducts(data);
      setLoading(false);
    } catch (error) {
      console.error("There was an error fetching the products:", error);
      setError(error);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const allowedTypes = ["image/jpeg", "image/png"];
      if (allowedTypes.includes(file.type)) {
        setNewProduct((prevState) => ({
          ...prevState,
          image: file,
        }));
      } else {
        alert("Please select a valid image file (JPEG or PNG).");
      }
    }
  };

  const handleCreateProduct = async (e) => {
    e.preventDefault();
    try {
      const token = getToken();
      await productService.createProduct(newProduct, token); 
      alert("Product created successfully");
      fetchProducts();
      resetForm();
    } catch (error) {
      console.error("There was an error creating the product:", error);
      setError(error);
    }
  };

  const handleEditProduct = (product) => {
    setEditingProduct(product);
    setNewProduct({
      productId: product.productId,
      hairTexture: product.hairTexture,
      hairStyle: product.hairStyle,
      hairSize: product.hairSize,
      hairColor: product.hairColor,
      hairStock: product.hairStock,
      hairPrice: product.hairPrice,
      image: null,
    });
  };

  const handleUpdateProduct = async (e) => {
    e.preventDefault();
    try {
      const token = getToken();
      await productService.updateProduct(newProduct, token); 
      alert("Product updated successfully");
      fetchProducts();
      resetForm();
      setEditingProduct(null);
    } catch (error) {
      console.error("There was an error updating the product:", error);
      setError(error);
    }
  };

  const handleDeleteProduct = async (productId) => {
    try {
      const token = getToken();
      await productService.deleteProduct(productId, token); 
      alert("Product deleted successfully");
      fetchProducts();
    } catch (error) {
      console.error("There was an error deleting the product:", error);
      setError(error);
    }
  };

  const resetForm = () => {
    setNewProduct({
      hairTexture: "",
      hairStyle: "",
      hairSize: "",
      hairColor: "",
      hairStock: true,
      hairPrice: "",
      image: null,
    });
  };

  const getProductImageUrl = (image) => {
    return `data:image/jpeg;base64,${image}`;
  };

  if (loading) {
    return <p>Loading products...</p>;
  }

  if (error) {
    return <p>There was an error: {error.message}</p>;
  }

  return (
    <>
      <Navbar />
      <div className="admin-container">
        <h1 className="admin-title">Admin - Manage Products</h1>
        <form
          onSubmit={editingProduct ? handleUpdateProduct : handleCreateProduct}
          className="product-form"
        >
          <input
            type="text"
            name="hairTexture"
            placeholder="Hair Texture"
            value={newProduct.hairTexture}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="hairStyle"
            placeholder="Hair Style"
            value={newProduct.hairStyle}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="hairSize"
            placeholder="Hair Size"
            value={newProduct.hairSize}
            onChange={handleInputChange}
            required
          />
          <input
            type="text"
            name="hairColor"
            placeholder="Hair Color"
            value={newProduct.hairColor}
            onChange={handleInputChange}
            required
          />
          <select
            name="hairStock"
            value={newProduct.hairStock}
            onChange={(e) =>
              setNewProduct((prev) => ({
                ...prev,
                hairStock: e.target.value === "true",
              }))
            }
            required
          >
            <option value={true}>Available</option>
            <option value={false}>Not Available</option>
          </select>
          <input
            type="number"
            name="hairPrice"
            placeholder="Hair Price"
            value={newProduct.hairPrice}
            onChange={handleInputChange}
            required
          />
          <input type="file" name="image" onChange={handleFileChange} />
          <button type="submit">
            {editingProduct ? "Update Product" : "Create Product"}
          </button>
        </form>
        <div className="products-grid">
          {products.map((product) => (
            <div key={product.productId} className="product-card">
              {product.image && (
                <img
                  src={getProductImageUrl(product.image)}
                  alt={product.hairTexture}
                  className="product-image"
                />
              )}
              <p>
                <strong>Hair Texture:</strong> {product.hairTexture}
              </p>
              <p>
                <strong>Hair Style:</strong> {product.hairStyle}
              </p>
              <p>
                <strong>Hair Size:</strong> {product.hairSize} inches
              </p>
              <p>
                <strong>Hair Color:</strong> {product.hairColor}
              </p>
              <p>
                <strong>Stock Status:</strong>{" "}
                {product.hairStock ? "Available" : "Not Available"}
              </p>
              <p>
                <strong>Price:</strong> R{product.hairPrice.toFixed(2)}
              </p>

              <div className="button-group">
                <button onClick={() => handleEditProduct(product)}>Edit</button>
                <button onClick={() => handleDeleteProduct(product.productId)}>
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default AdminProduct;
