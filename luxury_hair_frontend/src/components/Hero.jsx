import React from 'react';
import { useNavigate } from 'react-router-dom'; 
import '../assets/style.css';

const Hero = () => {
    const navigate = useNavigate(); 

    return (
        <section id="home" className="hero">
            <div className="hero-content">
                <h2>Elevate Your Style with Luxury Hair</h2>
                <p>_________________________________________________________________</p>
                <button className="product-button" onClick={() => navigate('/products')}>
                    SHOP NOW
                </button>
            </div>
        </section>
    );
};

export default Hero;
