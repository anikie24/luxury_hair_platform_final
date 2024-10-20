import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

const Cart = () => {
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

 
  const token = localStorage.getItem("token");

  const fetchCartItems = async (userId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/LuxuryHairVendingSystemDB/cart/user/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`, 
          },
        }
      );
      const cartData = response.data.map((item) => ({
        productId: item.product.productId,
        cartId: item.cartId,
        hairStyle: item.product.hairTexture,
        selectedLength: item.product.hairSize,
        selectedColor: item.product.hairColor,
        selectedStyle: item.product.hairStyle,
        hairPrice: item.product.hairPrice,
        quantity: item.quantity,
      }));
      setCartItems(cartData);
      localStorage.setItem("cart", JSON.stringify(cartData));
      setLoading(false);
    } catch (error) {
      console.error("Error fetching cart items:", error);
      if (error.response && error.response.status === 401) {
        console.error("Token expired or invalid. Redirecting to login.");
        navigate("/login"); 
      }
      setError("Failed to load cart items");
      setLoading(false);
    }
  };

  const checkLoginAndFetchCart = () => {
    const isLogin = localStorage.getItem("isLogin");
    const userId = localStorage.getItem("userId");

    if (isLogin !== "true") {
      console.error("User is not logged in");
      navigate("/login");
    } else if (userId) {
      fetchCartItems(userId);
    }
  };

  useEffect(() => {
    checkLoginAndFetchCart();
  }, []);

  const calculateTotalPrice = () => {
    return cartItems.reduce(
      (total, item) => total + item.hairPrice * item.quantity,
      0
    );
  };

  const clearCartFromLocalStorage = () => {
    localStorage.removeItem("cart");
    setCartItems([]);
  };

  const handleRemoveItem = (
    productId,
    selectedLength,
    selectedColor,
    selectedStyle
  ) => {
    const itemToRemove = cartItems.find(
      (item) =>
        item.productId === productId &&
        item.selectedLength === selectedLength &&
        item.selectedColor === selectedColor &&
        item.selectedStyle === selectedStyle
    );

    if (itemToRemove) {
      if (itemToRemove.quantity > 1) {
        if (itemToRemove.cartId !== undefined) {
          axios
            .put(
              `http://localhost:8080/LuxuryHairVendingSystemDB/cart/update/${
                itemToRemove.cartId
              }?newQuantity=${itemToRemove.quantity - 1}`,
              { newQuantity: itemToRemove.quantity - 1 },
              { headers: { Authorization: `Bearer ${token}` } } 
            )
            .then((response) => {
              const updatedCart = cartItems.map((item) =>
                item.cartId === itemToRemove.cartId
                  ? { ...item, quantity: item.quantity - 1 }
                  : item
              );
              setCartItems(updatedCart);
              localStorage.setItem("cart", JSON.stringify(updatedCart));
              alert("1 item removed");
            })
            .catch((error) => {
              console.error(
                "There was an error updating the cart:",
                error.response ? error.response.data : error
              );
            });
        }
      } else {
        axios
          .delete(
            `http://localhost:8080/LuxuryHairVendingSystemDB/cart/delete/${itemToRemove.cartId}`,
            { headers: { Authorization: `Bearer ${token}` } } 
          )
          .then((response) => {
            const updatedCart = cartItems.filter(
              (item) => item.cartId !== itemToRemove.cartId
            );
            setCartItems(updatedCart);
            localStorage.setItem("cart", JSON.stringify(updatedCart));
            alert("Remove Successful");
          })
          .catch((error) => {
            console.error(
              "There was an error removing the item from the cart:",
              error.response ? error.response.data : error
            );
          });
      }
    }
  };

  const clearCart = async () => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      console.error("No userId found in localStorage");
      return;
    }

    try {
      const response = await axios.delete(
        `http://localhost:8080/LuxuryHairVendingSystemDB/cart/user/${userId}/clear`,
        { headers: { Authorization: `Bearer ${token}` } } 
      );

      if (response.status === 200) {
        clearCartFromLocalStorage();
        alert("Cart cleared successfully!");
        location.reload();
      } else {
        console.error("Failed to clear cart:", response.statusText);
      }
    } catch (error) {
      console.error("Error clearing cart:", error);
    }
  };

  const handleCheckout = () => {
    const cartData = {
      userId: 1,
      products: cartItems.map((item) => ({
        productId: item.productId,
        quantity: item.quantity,
      })),
    };
    navigate("/shipping");

    axios
      .post(
        "http://localhost:8080/LuxuryHairVendingSystemDB/cart/create",
        cartData,
        { headers: { Authorization: `Bearer ${token}` } } 
      )
      .then((response) => {
        console.log("Cart saved successfully:", response.data);
        localStorage.removeItem("cart");
        setCartItems([]);
      })
      .catch((error) => {
        console.error("There was an error saving the cart!", error);
      });
  };

  if (cartItems.length === 0) {
    return (
      <>
        <Navbar />
        <div className="flex flex-col items-center mt-10">
          <h2 className="text-2xl font-semibold text-black mb-4">
            Your Cart is Empty
          </h2>
          <Link to="/products" className="text-lg text-black hover:underline">
            Continue Shopping
          </Link>
        </div>
        <Footer />
      </>
    );
  }

  return (
    <>
      <Navbar />
      <div className="max-w-5xl mx-auto mt-4">
        <h2 className="text-3xl font-bold mb-6 text-center text-black">
          Your Cart
        </h2>
        <table className="min-w-full bg-white text-black border border-black rounded-lg shadow-lg overflow-hidden">
          <thead className="bg-black text-white">
            <tr>
              <th className="p-4 text-left">Product</th>
              <th className="p-4 text-left">Length</th>
              <th className="p-4 text-left">Color</th>
              <th className="p-4 text-left">Style</th>
              <th className="p-4 text-left">Quantity</th>
              <th className="p-4 text-left">Price</th>
              <th className="p-4 text-left">Remove</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map((item, index) => (
              <tr key={index} className="border-b border-black">
                <td className="p-4">{item.hairStyle}</td>
                <td className="p-4">{item.selectedLength}</td>
                <td className="p-4">{item.selectedColor}</td>
                <td className="p-4">{item.selectedStyle}</td>
                <td className="p-4">{item.quantity}</td>
                <td className="p-4">R{item.hairPrice * item.quantity}</td>
                <td className="p-4">
                  <button
                    onClick={() =>
                      handleRemoveItem(
                        item.productId,
                        item.selectedLength,
                        item.selectedColor,
                        item.selectedStyle
                      )
                    }
                    className="bg-black text-white px-3 py-1 rounded hover:bg-gray-800 transition"
                  >
                    Remove
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="flex justify-between items-center mt-4">
          <button
            onClick={clearCart}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
          >
            Clear Cart
          </button>
          <h3 className="text-xl font-bold">Total: R{calculateTotalPrice()}</h3>
          <button
            onClick={handleCheckout}
            className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800 transition"
          >
            Checkout
          </button>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Cart;
