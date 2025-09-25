import React, { useContext, useEffect, useState } from 'react'
import './MyOrders.css'
import { StoreContext } from '../../context/StoreContext';
import axios from 'axios';
import { assets } from '../../assets/assets';

const MyOrders = () => {

    const {url,token} = useContext(StoreContext);
    const [data,setData] = useState([]);

    const fetchOrders = async () => {
        const response = await axios.get(url+"/api/order/my-orders",{headers:{token}});
        setData(response.data?.data || []);
        
    }

    useEffect(()=>{
        if (token) {
            fetchOrders();
        }
    },[token])


  return (
    <div className='my-orders'>
        <h2>My Orders</h2>
        <div className="container">
            {data.map((order,index)=>{
                const items = order.orderItems || [];
                return (
                    <div key={index} className='my-orders-order'>
                        <img src={assets.parcel_icon} alt="" />
                        <p>{items.map((item,idx)=>{
                            const line = `${item.food?.name || 'Item'} x ${item.quantity}`;
                            return idx === items.length-1 ? line : line + ", ";
                        })}</p>
                        <p>${order.totalAmount?.toFixed ? order.totalAmount.toFixed(2) : order.totalAmount}</p>
                        <p>Items: {items.length}</p>
                        <p><span>&#x25cf;</span><b>{order.status}</b></p>
                        <button onClick={fetchOrders}>Track Order</button>
                    </div>
                )
            })}
        </div>
        
    </div>
  )
}

export default MyOrders