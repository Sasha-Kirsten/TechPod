import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from './assets/vite.svg'
// import heroImg from './assets/hero.png'
import './App.css'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
// import { GoogleAuthProvider } from 'firebase/auth/web-extension'
import { connectWebSocket } from "./services/websocket";
// frontend/src/services/websocket.jsx
import { useEffect } from "react";

function Login(){
  // return <h1>Login</h1>;
  // const handleGoogleLogin = () => {
  //   const provider = new GoogleAuthProvider();
  //   // Implement the logic to sign in with Google using Firebase Authentication
  // }

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


// describe('Login Component', () => {
//   beforeEach(() => {
//     localStorage.clear()
//     vi.restoreAllMocks()
//   })

//   it('renders the login form correctly', () => {
//     render(
//       <MemoryRouter initialEntries={['/login']}>
//         <Routes>
//           <Route path="/login" element={<Login />} />
//         </Routes>
//       </MemoryRouter>
//     )

//     expect(screen.getByText('Login Page')).toBeInTheDocument()
//     expect(screen.getByLabelText('Username:')).toBeInTheDocument()
//     expect(screen.getByLabelText('Password:')).toBeInTheDocument()
//     expect(screen.getByRole('button', { name: 'Login' })).toBeInTheDocument()
//   })

//   it('submits credentials and stores token in localStorage', async () => {
//     const mockToken = 'mock-jwt-token-123'

//     global.fetch = vi.fn().mockResolvedValue({
//       json: vi.fn().mockResolvedValue({ token: mockToken }),
//     })

//     render(
//       <MemoryRouter initialEntries={['/login']}>
//         <Routes>
//           <Route path="/login" element={<Login />} />
//         </Routes>
//       </MemoryRouter>
//     )

//     fireEvent.change(screen.getByLabelText('Username:'), {
//       target: { value: 'testuser@example.com' },
//     })
//     fireEvent.change(screen.getByLabelText('Password:'), {
//       target: { value: 'password123' },
//     })
//     fireEvent.click(screen.getByRole('button', { name: 'Login' }))

//     await waitFor(() => {
//       expect(global.fetch).toHaveBeenCalledWith('/api/login', {
//         method: 'POST',
//         headers: { 'Content-Type': 'application/json' },
//         body: JSON.stringify({ email: 'testuser@example.com', password: 'password123' }),
//       })
//       expect(localStorage.getItem('token')).toBe(mockToken)
//     })
//   })
// })


function Register(){
  // return <h1>Register</h1>;
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
  // return <h1>Laptop</h1>;
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
  // return <h1>Cart</h1>;
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
  // return <h1>Checkout</h1>;
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
  // return <h1>Orders</h1>;
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
