import React, { useEffect, useState } from "react";
import { Link, NavLink, useLocation, useNavigate } from "react-router-dom";
import "../assets/style.css";
import "../assets/AuthPage.css";

const Navbar = () => {
  const [cartItemsCount, setCartItemsCount] = useState(0);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [redirectPath, setRedirectPath] = useState("/");
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const cart = localStorage.getItem("cart");
    if (cart) {
      const cartItems = JSON.parse(cart);
      const totalItems = cartItems.reduce(
        (total, item) => total + item.quantity,
        0
      );
      setCartItemsCount(totalItems);
    }

    const loginStatus = localStorage.getItem("isLogin");
    setIsLoggedIn(!!loginStatus);

    setRedirectPath(location.pathname);
  }, [location.pathname]);

  const Logout = () => {
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
    <nav>
      <h1>Luxury Hair</h1>
      <ul>
        <li>
          <NavLink to="/">Home</NavLink>
        </li>
        <li>
          <NavLink to="/products">Products</NavLink>
        </li>
        <li>
          <NavLink to="/Tips">Tips</NavLink>
        </li>
        <li>
          <NavLink to="/cart">
            Cart <span>({cartItemsCount})</span>
          </NavLink>
        </li>
       

        {isLoggedIn ? (
          <li>
            <NavLink to="/" onClick={Logout}>
              Logout
            </NavLink>
          </li>
        ) : (
          <li>
            <NavLink to="/login">Login</NavLink>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
