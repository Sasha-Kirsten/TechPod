import { use, useState } from 'react'
import './App.css'
import { BrowserRouter, Routes, Route, Navigate, Link } from 'react-router-dom'
import { connectWebSocket } from "./services/websocket";
import { useEffect } from "react";
import DriverMap from './services/DriverMap';


function Home() {
  return (
    <section id="center">
      <div style={{
        background: "linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)",
        borderRadius: "16px",
        padding: "2rem",
        boxShadow: "0 4px 24px rgba(160, 44, 255, 0.15)",
        maxWidth: 400,
        margin: "2rem auto"
      }}>
        <h1 style={{ fontFamily: "var(--heading)", color: "#6b21a8" }}>Welcome to TechPod</h1>
        <p style={{ marginBottom: "1.5rem" }}>Who are you?</p>
        <div style={{ display: "flex", flexDirection: "column", gap: "1rem" }}>
          <Link className="role-btn customer" to="/laptop">Customer</Link>
          <Link className="role-btn driver" to="/driver">Delivery Driver</Link>
          <Link className="role-btn admin" to="/admin">Administrator</Link>
        </div>
      </div>
    </section>
  );
}

function Login(){
  const handleSubmit = async(event) => {
    event.preventDefault();
    const username = event.target.username.value;
    const password = event.target.password.value;
    try{
      const res = await fetch('/api/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email: username, password})
      })
      if (!res.ok) {
        throw new Error('Login failed');
      }
      const data = await res.json()
      localStorage.setItem('token', data.token)
      window.location.href = '/laptop'
    } catch (error) {
      console.error(error);
      alert('Login failed. Please try again.');
    }
    // const res = await fetch('/api/login', {
    //   method: 'POST',
    //   headers: {'Content-Type': 'application/json'},
    //   body: JSON.stringify({email: username, password})
    // })
    // const data = await res.json()
    // localStorage.setItem('token', data.token)
    // window.location.href = '/laptop'
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
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
          <Link to="/cart" className="role-btn customer">View Cart</Link>
          {/* <Link to="/checkout" className="role-btn customer">Proceed to Checkout</Link> */}
        </div>
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
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
          <Link to="/checkout" className="role-btn customer">Proceed to Checkout</Link>
        </div>
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
        <div>
          <Link to="/orders" className="role-btn customer">View Orders</Link>
          <Link to="/" className="role-btn customer">Back to Home</Link>
        </div>
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
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
          <Link to="/laptop" className="role-btn customer">View Laptops</Link>
        </div>
      </div>
    </section>
  );
}

function Driver(){
  const [driverLocation, setDriverLocation] = useState({ lat: 37.7749, lng: -122.4194 }); // Example location
  const [deliveryPoints, setDeliveryPoints] = useState([
    { lat: 37.7849, lng: -122.4094 },
    { lat: 37.7649, lng: -122.4294 },
  ]);
  useEffect(() => {
    // Simulate real-time location updates
    const interval = setInterval(() => {
      setDriverLocation((prev) => ({
        lat: prev.lat + (Math.random() - 0.5) * 0.01,
        lng: prev.lng + (Math.random() - 0.5) * 0.01,
      }));
    }, 2000);
    return () => clearInterval(interval);
  }, []);
  return (
    <section id="center">
      <div>
        <h1>Delivery Driver Dashboard</h1>
        <p>Track your deliveries and manage your schedule.</p>
        <span role="img" aria-label="driver" style={{ fontSize: "3rem" }}>🚚</span>
        <DriverMap
          deliveryPoints={deliveryPoints}
          driverLocations={[driverLocation]}
        />
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/delivery-route" className="role-btn driver">View Delivery Route</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
        </div>
      </div>
    </section>
  )
}

function Admin(){
  const [driverLocations, setDriverLocations] = useState([
    { lat: 37.7749, lng: -122.4194 }, // Example driver locations
    { lat: 37.7849, lng: -122.4294 },
  ]);
  const [deliveryPoints, setDeliveryPoints] = useState([
    { lat: 37.7949, lng: -122.4394 },
    { lat: 37.7649, lng: -122.4094 },
  ]);
  return (
    <section id="center">
      <div>
        <h1>Admin</h1>
        <p>Admin panel coming soon!</p>
        <DriverMap
          deliveryPoints={deliveryPoints}
          driverLocations={driverLocations}
        />
        <span role="img" aria-label="admin" style={{ fontSize: "3rem" }}>🛠️</span>
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
        </div>
      </div>
    </section>
  );
}

function NotFound() {
  return (
    <section id="center">
      <div>
        <h1>404 - Not Found</h1>
        <p>Sorry, the page you're looking for doesn't exist.</p>
        <span role="img" aria-label="not-found" style={{ fontSize: "3rem" }}>❓</span>
        <div>
          <Link to="/" className="role-btn driver">Back to Home</Link>
        </div>
      </div>
    </section>
  );
}

function DeliveryRoute(){
  return (
    <section id="center">
      <div>
        <h1>Delivery Route</h1>
        <p>Track your delivery route in real-time.</p>
        <span role="img" aria-label="route" style={{ fontSize: "3rem" }}>🗺️</span >
        <h1>MAP!</h1>
        <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
        </div>
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
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/laptop" element={<Laptop />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/driver" element={<Driver />} />
        <Route path="/delivery-route" element={<DeliveryRoute />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
      </BrowserRouter>
  )
}

export default App
