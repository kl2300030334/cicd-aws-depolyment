import { createContext, useEffect, useState } from "react";
import { assets as staticAssets } from "../assets/assets";
import axios from "axios";

export const StoreContext = createContext(null);

const StoreContextProvider = (props) => {
  const [cartItems, setCartItems] = useState({});
  const [food_list, setFoodList] = useState([]);
  const [token, setToken] = useState("");
  const url = "http://ec2-98-84-110-225.compute-1.amazonaws.com:8085";

  // Add a single item to cart
  // Add a single item to cart
  const addToCart = async (itemId) => {
    if (!itemId) return;

    setCartItems((prev) => ({
      ...prev,
      [itemId]: (prev[itemId] || 0) + 1,
    }));

    if (!token) return;

    try {
      await axios.post(
        `${url}/api/cart/add`,
        { itemId: Number(itemId) }, // send numeric id
        { headers: { token } }
      );
    } catch (error) {
      console.error(
        "Error adding to cart:",
        error.response?.data || error.message
      );
    }
  };

  // Remove a single item from cart
  const removeFromCart = async (itemId) => {
    if (!itemId) return;

    setCartItems((prev) => {
      const updated = { ...prev };
      if (updated[itemId] > 1) {
        updated[itemId] -= 1;
      } else {
        delete updated[itemId];
      }
      return updated;
    });

    if (!token) return;

    try {
      await axios.post(
        `${url}/api/cart/remove`,
        { itemId: Number(itemId) }, // send numeric id
        { headers: { token } }
      );
    } catch (error) {
      console.error(
        "Error removing from cart:",
        error.response?.data || error.message
      );
    }
  };


  // Calculate total cart amount
  const getTotalCartAmount = () => {
    let totalAmount = 0;
    for (const itemId in cartItems) {
      if (cartItems[itemId] > 0) {
        const itemInfo = food_list.find((f) => f.id === Number(itemId));
        if (itemInfo) {
          totalAmount += itemInfo.price * cartItems[itemId];
        }
      }
    }
    return totalAmount;
  };


  // Fetch list of foods
  const fetchFoodList = async () => {
    try {
      const response = await axios.get(`${url}/api/food/list`);
      // Build an ordered list of bundled food asset keys: ["food_1", ..., "food_32"]
      const foodAssetKeys = Object.keys(staticAssets)
        .filter((k) => /^food_\d+$/i.test(k))
        .sort((a, b) => Number(a.split('_')[1]) - Number(b.split('_')[1]));

      const foods = response.data.map((item, index) => {
        let image;
        const src = item.imageUrl || item.image;
        const fallbackKey = foodAssetKeys.length
          ? foodAssetKeys[index % foodAssetKeys.length]
          : undefined;
        const fallbackImage = fallbackKey ? staticAssets[fallbackKey] : undefined;

        if (src) {
          if (src.startsWith('http://') || src.startsWith('https://')) {
            image = src;
          } else if (src.startsWith('/images/')) {
            image = `${url}${src}`;
          } else if (src.includes('.')) {
            // Looks like a filename in uploads without leading slash
            image = `${url}/images/${src}`;
          } else {
            // Looks like a local asset key, e.g., "food_1"
            const key = src.replace(/\.png$/i, "");
            image = staticAssets[key];
          }
        }

        return {
          ...item,
          _id: item.id ?? `temp-${index}`,
          image: image || fallbackImage,
          fallbackImage,
        };
      });
      setFoodList(foods);
    } catch (error) {
      console.error("Error fetching food list:", error.response?.data || error.message);
    }
  };


  // Load cart data from backend
  const loadCartData = async () => {
    if (!token) return;

    try {
      const response = await axios.post(`${url}/api/cart/get`, {}, {
        headers: { token },
      });

      if (response.data?.data) {
        setCartItems(response.data.data);
      }
    } catch (error) {
      console.error("Error fetching cart data:", error.response?.data || error.message);
    }
  };

  // Initialize data on app load
  useEffect(() => {
    const initializeData = async () => {
      await fetchFoodList();
      const savedToken = localStorage.getItem("token");
      if (savedToken) setToken(savedToken);
    };
    initializeData();
  }, []);

  // Load cart whenever token changes
  useEffect(() => {
    if (token) {
      loadCartData();
    } else {
      setCartItems({});
    }
  }, [token]);

  const contextValue = {
    food_list,
    cartItems,
    addToCart,
    removeFromCart,
    getTotalCartAmount,
    url,
    token,
    setToken,
    setCartItems,
  };

  return (
    <StoreContext.Provider value={contextValue}>
      {props.children}
    </StoreContext.Provider>
  );
};

export default StoreContextProvider;
