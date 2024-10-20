import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Home from "./components/Home";
import Product from "./components/Product";
import SingleProduct from "./components/SingleProduct";
import Cart from "./components/Cart";
import AuthPage from "./components/AuthPage.jsx";
import Checkout from "./components/Checkout.jsx";
import AdminProduct from "./components/AdminProduct.jsx";
import Reviews from "./components/Reviews.jsx";
import Shipping from "./components/Shipping.jsx";
import Tips from "./components/Tips.jsx";

const App = () => {
  const login = window.localStorage.getItem("isLogin") === "true";
  const userType = window.localStorage.getItem("userType");

  const ProtectedRoute = ({ children, role, redirectTo }) => {
    const login = window.localStorage.getItem("isLogin") === "true";
    const userType = window.localStorage.getItem("userType");

    if (!login) {
      return <Navigate to="/login" />;
    }
    if (role && userType !== role) {
      return <Navigate to="/" />;
    }
    return children;
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/reviews" element={<Reviews />} />
        <Route path="/product/:id/reviews" element={<Reviews />} />
        <Route path="/products" element={<Product />} />
        <Route path="/shipping" element={<Shipping />} />
        <Route path="/Tips" element={<Tips />} />
        <Route path="/product/:id" element={<SingleProduct />} />
        <Route
          path="/cart"
          element={
            <ProtectedRoute role="customer" redirectTo="/">
              <Cart />
            </ProtectedRoute>
          }
        />
        <Route
          path="/login"
          element={login ? <Navigate to="/" /> : <AuthPage />}
        />
       
        <Route
          path="/checkout"
          element={
            <ProtectedRoute role="customer" redirectTo="/">
              <Checkout />
            </ProtectedRoute>
          }
        />
        <Route
          path="/AdminProduct"
          element={
            <ProtectedRoute role="admin" redirectTo="/">
              <AdminProduct />
            </ProtectedRoute>
          }
        />
      </Routes>
    </Router>
  );
};

export default App;
