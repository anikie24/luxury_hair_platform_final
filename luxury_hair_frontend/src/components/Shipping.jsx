
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import Footer from "./Footer";

const Shipping = () => {
  const [street, setStreet] = useState("");
  const [province, setProvince] = useState("");
  const [city, setCity] = useState("");
  const [zipCode, setZipCode] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      
      const token = localStorage.getItem("token");

      if (!token) {
        alert("You must be logged in to submit shipping details.");
        return;
      }

      console.log("Submitting:", {
        streetName: street,
        province,
        city,
        zipCode,
      });

      
      await axios.post(
        "http://localhost:8080/LuxuryHairVendingSystemDB/address/create",
        {
          streetName: street,
          province,
          city,
          zipCode,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`, 
          },
        }
      );

      alert("Shipping details submitted successfully!");

      navigate("/checkout");
    } catch (error) {
      console.error("Error submitting shipping details:", error);
      alert("Failed to submit shipping details.");
    }
  };

  const handleBackToCart = () => {
    navigate("/cart");
  };

  return (
    <>
      <Navbar />
      <div
        style={{
          maxWidth: "650px",
          margin: "20px auto",
          padding: "20px",
          backgroundColor: "#f7f7f7",
          color: "#000",
          border: "1px solid #000",
          borderRadius: "10px",
        }}
      >
        <div style={{ marginBottom: "20px" }}>
          <h3>Shipping Address</h3>
          <div style={{ display: "flex", gap: "10px" }}>
            <label style={{ flex: 1 }}>
              Street:
              <input
                type="text"
                value={street}
                onChange={(e) => setStreet(e.target.value)}
                placeholder="Street address"
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "8px",
                  border: "1px solid #000",
                  borderRadius: "4px",
                }}
              />
            </label>
            <label style={{ flex: 1 }}>
              City:
              <input
                type="text"
                value={city}
                onChange={(e) => setCity(e.target.value)}
                placeholder="City"
                style={{
                  width: "100%",
                  padding: "8px",
                  marginTop: "8px",
                  border: "1px solid #000",
                  borderRadius: "4px",
                }}
              />
            </label>
          </div>
          <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
            <label style={{ flex: 1 }}>
              Province:
              <input
                type="text"
                value={province}
                onChange={(e) => setProvince(e.target.value)}
                placeholder="Province"
                style={{
                  width: "100%",
                  padding: "8px",
                  border: "1px solid #000",
                  borderRadius: "4px",
                }}
              />
            </label>
            <label style={{ flex: 1 }}>
              Zip Code:
              <input
                type="text"
                value={zipCode}
                onChange={(e) => setZipCode(e.target.value)}
                placeholder="Zip Code"
                style={{
                  width: "100%",
                  padding: "8px",
                  border: "1px solid #000",
                  borderRadius: "4px",
                }}
              />
            </label>
          </div>
        </div>
        <div style={{ marginBottom: "20px" }}>
          <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
            <button
              onClick={handleSubmit}
              style={{
                flex: 1,
                padding: "10px",
                backgroundColor: "#000",
                color: "white",
                border: "none",
                borderRadius: "4px",
              }}
            >
              Checkout
            </button>
          </div>
        </div>
        <div style={{ marginBottom: "20px" }}>
          <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
            <button
              onClick={handleBackToCart}
              style={{
                flex: 1,
                padding: "10px",
                backgroundColor: "#666",
                color: "white",
                border: "none",
                borderRadius: "4px",
              }}
            >
              Back to Cart
            </button>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Shipping;
