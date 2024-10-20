import axios from "axios";

const baseUrl = import.meta.env.VITE_BACK_END_URL;


const getToken = () => {
  return localStorage.getItem("token");
};

const productService = {
  
  getAllProducts: async () => {
    try {
      const token = getToken();
      const response = await axios.get(`${baseUrl}/product/getall`, {
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });
      return response.data;
    } catch (error) {
      console.error("There was an error fetching the products:", error);
      throw error;
    }
  },

  
  getProductImage: (productId) => `${baseUrl}/product/image/${productId}`,

  
  createProduct: (productData) => {
    const formData = new FormData();
    Object.keys(productData).forEach((key) => {
      formData.append(key, productData[key]);
    });

    const token = getToken();
    return axios.post(`${baseUrl}/product/create`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: token ? `Bearer ${token}` : "",
      },
    });
  },

 
  getProductById: async (productId) => {
    try {
      const token = getToken();
      const response = await axios.get(`${baseUrl}/product/read/${productId}`, {
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });
      return response.data;
    } catch (error) {
      console.error(
        `There was an error fetching the product with ID ${productId}:`,
        error
      );
      throw error;
    }
  },


  updateProduct: async (product) => {
    const formData = new FormData();

    
    formData.append("productId", product.productId);

   
    Object.keys(product).forEach((key) => {
      if (key !== "productId") {
        formData.append(key, product[key]);
      }
    });

    const token = getToken();
    return axios.put(`${baseUrl}/product/update`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: token ? `Bearer ${token}` : "",
      },
    });
  },

  
  deleteProduct: async (productId) => {
    const token = getToken();
    await axios.delete(`${baseUrl}/product/delete/${productId}`, {
      headers: {
        Authorization: token ? `Bearer ${token}` : "",
      },
    });
  },

  
  getProductImageBase64: async (productId) => {
    try {
      const token = getToken();
      const response = await axios.get(`${baseUrl}/product/image/${productId}`, {
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
        responseType: "blob", 
      });
      const blob = response.data;

      
      const base64Image = await productService.convertBlobToBase64(blob);
      return base64Image;
    } catch (error) {
      console.error(
        `Error fetching or converting the image for product ${productId}:`,
        error
      );
      throw error;
    }
  },

 
  convertBlobToBase64: (blob) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result);
      reader.onerror = reject;
      reader.readAsDataURL(blob); 
    });
  },
};

export default productService;

