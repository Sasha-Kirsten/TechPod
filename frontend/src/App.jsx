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
      // const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/register`,
      const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/login`, {
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
      // console.error(error); 
      alert('Login failed. Please try again.');
    }
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
  const handleSubmit = async (event) => {
    event.preventDefault();
    const firstName = event.target.firstName.value;
    const lastName = event.target.lastName.value;
    const email = event.target.email.value;
    const password = event.target.password.value;

    try {
      const res = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ firstName, lastName, email, password }),
      });

      if (!res.ok) {
        throw new Error('Registration failed');
      }

      alert('Registration successful! Please log in.');
      window.location.href = '/login';
    } catch (error) {
      // console.error(error);
      alert('Registration failed. Please try again.');
    }
  };

  return (
    <section id="center">
      <div>
        <h1>Register</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor="firstName">First Name:</label>
          <input type="text" id="firstName" name="firstName" required />
          <br />
          <label htmlFor="lastName">Last Name:</label>
          <input type="text" id="lastName" name="lastName" required />
          <br />
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

function Driver() {
  const [driverLocation, setDriverLocation] = useState([]);
  const [deliveryPoints, setDeliveryPoints] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found, redirecting to login");
      window.location.href = "/login";
      return;
    }
    fetch("/api/driver-locations", {
      headers:{
        Authorization: `Bearer ${token}`,
      }
    })
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => setDriverLocation(data))
      .catch((err) => console.error("Error fetching driver locations:", err));

    fetch("/api/delivery-points", {
      headers:{
        Authorization: `Bearer ${token}`,
      }
    })
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => setDeliveryPoints(data))
      .catch((err) => console.error("Error fetching delivery points:", err));
  }, []);

  return (
    <section id="center">
      <div>
        <h1>Delivery Driver Dashboard</h1>
        <DriverMap
          deliveryPoints={deliveryPoints}
          driverLocation={driverLocation}
        />
      </div>
      <div>
          <Link to="/orders" className="role-btn driver">View Orders</Link>
          <Link to="/delivery-route" className="role-btn driver">View Delivery Route</Link>
          <Link to="/" className="role-btn driver">Back to Home</Link>
        </div>
    </section>
  );
}

function Admin(){
  const [driverLocations, setDriverLocations] = useState([]);
  const [deliveryPoints, setDeliveryPoints] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found, redirecting to login");
      window.location.href = "/login";
      return;
    }
    // Fetch driver locations
    fetch("/api/driver-locations", {
      headers:{
        Authorization: `Bearer ${token}`,
      }
    })
      .then((res) => res.json())
      .then((data) => setDriverLocations(data))
      .catch((err) => console.error("Error fetching driver locations:", err));

    // Fetch delivery points
    fetch("/api/delivery-points", {
      headers:{
        Authorization: `Bearer ${token}`,
      }
    })
      .then((res) => res.json())
      .then((data) => setDeliveryPoints(data))
      .catch((err) => console.error("Error fetching delivery points:", err));
  }, []);

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
  const [route, setRoute] = useState([]);
  const [selectedRoute, setSelectedRoute] = useState(null);
  const [deliveryPoints, setDeliveryPoints] = useState([]);
  useEffect(() => {
    fetch("/api/routes")
    .then((res) => res.json())
    .then((data) => setRoute(data))
    .catch((err) => console.error("Error fetching routes:", err));
  }, []);
  const handleRouteSelect = (routeId) => {
    fetch(`/api/routes/${routeId}/points`)
      .then((res) => res.json())
      .then((data) => {
        setSelectedRoute(routeId);
        setDeliveryPoints(data);
      })
      .catch((err) => console.error("Error fetching route details:", err));
  }
  return (
    <section id="center">
      <div>
        <h1>Delivery Route</h1>
        <p>Track your delivery route in real-time.</p>
        {/* <span role="img" aria-label="route" style={{ fontSize: "3rem" }}>🗺️</span >
        <h1>MAP!</h1> */}
        <ul>
          {routes.map((route) => (
            <li key={route.id}>
              <button onClick={() => handleRouteSelect(route.id)}>
                {route.routeName}
              </button>
            </li>
          ))}
        </ul>
        {selectedRoute && (
          <div>
            <h2>Selected Route: {selectedRoute}</h2>
            <ul>
              {deliveryPoints.map((point) => (
                <li key={point.id}>
                  {point.name} - {point.address}
                </li>
              ))}
            </ul>
          </div>
        )}
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
