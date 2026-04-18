import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from './assets/vite.svg'
// import heroImg from './assets/hero.png'
import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
// import { GoogleAuthProvider } from 'firebase/auth/web-extension'

function login(){
  // return <h1>Login</h1>;
  const handleGoogleLogin = () => {
    const provider = new GoogleAuthProvider();
    // Implement the logic to sign in with Google using Firebase Authentication
  }

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
      </form>
    </div>
  </section>
  )
}

function register(){
  // return <h1>Register</h1>;

}

function laptop(){
  return <h1>Laptop</h1>;
}

function cart(){
  return <h1>Cart</h1>;
}

function checkout(){
  return <h1>Checkout</h1>;
}

function orders(){
  return <h1>Orders</h1>;
}

function admin(){
  return <h1>Admin</h1>;
}

function App() {
  const [count, setCount] = useState(0)

  return (
      <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/laptop" element={<Laptop />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
        {/* <div className="App">
          <h1>TechPod</h1>
          <p>Welcome to TechPod, your one-stop shop for all things tech! We offer a wide range of laptops, accessories, and more. Whether you're a student, a professional, or just a tech enthusiast, we've got something for everyone. Explore our collection and find the perfect tech products to suit your needs.</p>
          <nav>
            <ul>
              <li><a href="/login">Login</a></li>
              <li><a href="/register">Register</a></li>
              <li><a href="/laptop">Laptop</a></li>
              <li><a href="/cart">Cart</a></li>
              <li><a href="/checkout">Checkout</a></li>
              <li><a href="/orders">Orders</a></li>
              <li><a href="/admin">Admin</a></li>
            </ul>
          </nav>
        </div> */}
      </BrowserRouter>
  )
}

export default App
