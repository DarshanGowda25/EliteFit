# 🏋️‍♂️ EliteFit

**EliteFit** is a full-stack web application for **gym slot booking** and **supplement shopping**. It provides users with a seamless experience to buy gym membership and purchase health supplements through an integrated platform.

---

## 🚀 Live Demo

-[https://elite-front-end-six.vercel.app/](https://elite-front-end-six.vercel.app/)


---

## 🛠️ Tech Stack

### Frontend
- **React** (with Vite)
- **Tailwind CSS** for styling
- **Formik + Yup** for forms and validation
- **TanStack Query** for data fetching
- **Axios** for API requests
- **Vercel** for deployment

### Backend
- **Java + Spring Boot**
- **Spring Security + JWT** for authentication and role-based access
- **Spring Data JPA** for persistence
- **RESTful APIs**
- **Render** for deployment

### Database
- **TiDB Cloud** (MySQL-compatible distributed SQL database)

---

## 📦 Features

### User
- 🧾 Sign up / Login with JWT authentication
- 💪 Buy gym memberships 
- 🛍 Browse and purchase supplements
- 🛒 Add to cart, view product details,add address,payment and checkout

### Admin
- 📦 Add / Edit / Delete products
- 📅 Manage queries
- 📊 View booking and purchase history

---

## 🔐 Authentication

Authentication is handled via **JWT** tokens. Upon login, the backend returns a token that the frontend stores and uses in headers for secured API calls.


