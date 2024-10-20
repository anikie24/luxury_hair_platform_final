import { useNavigate } from "react-router-dom";

const Checkout = () => {
  const navigate = useNavigate();

  const handleContinueShopping = () => {
    navigate("/products");
  };

  const handleLogout = () => {
   
    const userId = localStorage.getItem("userId");
    const userType = localStorage.getItem("userType");
    const token = localStorage.getItem("token");

 
    localStorage.clear();

   
    if (userId) localStorage.setItem("userId", userId);
    if (userType) localStorage.setItem("userType", userType);
    if (token) localStorage.setItem("token", token);

   
    alert("Logout Successful");

   
    navigate("/");

   
    window.location.reload();
  };

  return (
    <div className="complete-checkout">
      <div style={styles.overlay}>
        <h1 style={styles.title}>Thank You for Shopping with Luxury Hair!</h1>
        <div style={styles.buttonContainer}>
          <button style={styles.button} onClick={handleContinueShopping}>
            Continue Shopping
          </button>
          <button style={styles.button} onClick={handleLogout}>
            Log Out
          </button>
        </div>
      </div>
    </div>
  );
};

const styles = {
  overlay: {
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    padding: "20px",
    borderRadius: "10px",
    textAlign: "center",
    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
    color: "#fff",
  },
  title: {
    fontSize: "24px",
    marginBottom: "20px",
  },
  buttonContainer: {
    display: "flex",
    gap: "20px",
    justifyContent: "center",
  },
  button: {
    padding: "10px 20px",
    fontSize: "16px",
    cursor: "pointer",
    border: "none",
    borderRadius: "5px",
    backgroundColor: "#4CAF50",
    color: "white",
    transition: "background-color 0.3s",
    boxShadow: "0 2px 4px rgba(0, 0, 0, 0.2)",
  },
};

export default Checkout;
