import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import { BrowserRouter } from 'react-router-dom'

function login(){
  // return <h1>Login</h1>;
  <section id="center">
    {/* <div className="hero">
      <img src={heroImg} className="base" width="170" height="179" alt="" />
      <img src={reactLogo} className="framework" alt="React logo" />
      <img src={viteLogo} className="vite" alt="Vite logo" />
    </div> */}
    <div>
      <h1>Login Page</h1>
      <form>
        <label htmlFor="username">Username:</label>
        <input type="text" id="username" name="username" />
        <br />
        <label htmlFor="password">Password:</label>
        <input type="password" id="password" name="password" />
        <br />
        <button type="submit">Login</button>
      </form>
      {/* /* OAutheticate - how to  */} 
    </div>
    <button
      className="counter"
      onClick={() => setCount((count) => count + 1)}
    >
      Count is {count}
    </button>
  </section>
}

function register(){
  return <h1>Register</h1>;
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
      <div className="App">
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
      </div>
    </BrowserRouter>
  )
}

export default App
