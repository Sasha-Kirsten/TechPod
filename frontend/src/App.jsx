import { useState } from 'react'
import './App.css'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { connectWebSocket } from "./services/websocket";
import { useEffect } from "react";

function Login(){

  const handleSubmit = async(event) => {
    event.preventDefault();
    const username = event.target.username.value;
    const password = event.target.password.value;
    const res = await fetch('/api/login', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({email: username, password})
    })
    const data = await res.json()
    localStorage.setItem('token', data.token)
    window.location.href = '/laptop'
  }
  return(
  <section id="center">
    <div>
      <h1>Login Page</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="username">Username:</label>
        <input type="text" id="username" name="username" />
        <br />
        <label htmlFor="password">Password:</label>
        <input type="password" id="password" name="password" />
        <br />
        <button type="submit">Login</button>
        <a href="/register" className="role-btn customer">Register</a>
        {/* <button type="button" onClick={handleGoogleLogin}>Login with Google</button> */}
      </form>
    </div>
  </section>
  )
}

function Register(){
  return (
    <section id="center">
      <div>
        <h1>Register</h1>
        {/* <p>Registration form coming soon!</p> */}
        <form>
          <label htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" required />
          <br />
          <label htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" required />
          <br />
          <button type="submit">Register</button>
        </form>
      </div>
    </section>
  );
}

function Laptop(){
  return (
    <section id="center">
      <div>
        <h1>Shop Laptops</h1>
        <p>Browse our latest tech products!</p>
        <span role="img" aria-label="laptop" style={{ fontSize: "3rem" }}>💻</span>
      </div>
    </section>
  );
}

function Cart(){
  return (
    <section id="center">
      <div>
        <h1>Your Cart</h1>
        <p>Ready to checkout?</p>
        <span role="img" aria-label="cart" style={{ fontSize: "3rem" }}>🛒</span>
      </div>
    </section>
  );
}

function Checkout(){
  return (
    <section id="center">
      <div>
        <h1>Checkout</h1>
        <p>Almost there! Complete your purchase.</p>
        <span role="img" aria-label="checkout" style={{ fontSize: "3rem" }}>💳</span>
      </div>
    </section>
  );
}

function Orders(){
  return (
    <section id="center">
      <div>
        <h1>Your Orders</h1>
        <p>Review your past purchases.</p>
        <span role="img" aria-label="orders" style={{ fontSize: "3rem" }}>📦</span>
      </div>
    </section>
  );
}

function Admin(){
  return (
    <section id="center">
      <div>
        <h1>Admin</h1>
        <p>Admin panel coming soon!</p>
        <span role="img" aria-label="admin" style={{ fontSize: "3rem" }}>🛠️</span>
      </div>
    </section>
  );
}

function App() {
  const [count, setCount] = useState(0)
  useEffect(() => {
    connectWebSocket();
  }, [])

  return (
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/laptop" element={<Laptop />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
      </BrowserRouter>
  )
}

export default App
