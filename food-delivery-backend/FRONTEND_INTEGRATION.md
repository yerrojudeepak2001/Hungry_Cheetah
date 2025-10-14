# Frontend Integration Guide for Food Delivery Backend

## Backend Services and Ports

The following services are running and available for your frontend (port 3000):

### Core Services
- **Service Registry (Eureka)**: http://localhost:8761
- **Config Server**: http://localhost:8888
- **Auth Service**: http://localhost:8082
- **User Service**: http://localhost:8083
- **Admin Service**: http://localhost:8099

## API Endpoints for Frontend Integration

### Authentication Service (http://localhost:8082)
```javascript
// Registration
POST http://localhost:8082/api/auth/register
Content-Type: application/json
{
  "username": "john_doe",
  "password": "SecurePass123!",
  "email": "john@example.com", 
  "firstName": "John",
  "lastName": "Doe"
}

// Login
POST http://localhost:8082/api/auth/login
Content-Type: application/json
{
  "username": "john_doe",
  "password": "SecurePass123!"
}

// Get User Info (requires Bearer token)
GET http://localhost:8082/api/auth/userinfo
Authorization: Bearer <jwt_token>

// Logout (requires Bearer token)
POST http://localhost:8082/api/auth/logout
Authorization: Bearer <jwt_token>
```

### User Service (http://localhost:8083)
```javascript
// Get User Profile (requires Bearer token)
GET http://localhost:8083/api/users/profile
Authorization: Bearer <jwt_token>

// Update User Profile (requires Bearer token)
PUT http://localhost:8083/api/users/profile
Authorization: Bearer <jwt_token>
Content-Type: application/json
{
  "firstName": "Updated Name",
  "lastName": "Updated Last Name",
  "email": "updated@example.com"
}

// Get User Addresses (requires Bearer token)
GET http://localhost:8083/api/users/addresses
Authorization: Bearer <jwt_token>

// Add User Address (requires Bearer token)
POST http://localhost:8083/api/users/addresses
Authorization: Bearer <jwt_token>
Content-Type: application/json
{
  "street": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "country": "USA"
}
```

### Admin Service (http://localhost:8099)
```javascript
// Admin Dashboard (requires Bearer token with admin role)
GET http://localhost:8099/api/v1/admin/dashboard
Authorization: Bearer <admin_jwt_token>

// System Health (requires Bearer token with admin role)
GET http://localhost:8099/api/v1/admin/system/health
Authorization: Bearer <admin_jwt_token>
```

## Frontend JavaScript Integration Example

### Authentication Helper
```javascript
class AuthService {
  constructor() {
    this.baseURL = 'http://localhost:8082/api/auth';
    this.token = localStorage.getItem('authToken');
  }

  async register(userData) {
    const response = await fetch(`${this.baseURL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify(userData)
    });
    
    if (response.ok) {
      const data = await response.json();
      this.token = data.data; // JWT token
      localStorage.setItem('authToken', this.token);
      return data;
    }
    throw new Error('Registration failed');
  }

  async login(username, password) {
    const response = await fetch(`${this.baseURL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({ username, password })
    });
    
    if (response.ok) {
      const data = await response.json();
      this.token = data.data; // JWT token
      localStorage.setItem('authToken', this.token);
      return data;
    }
    throw new Error('Login failed');
  }

  async getUserInfo() {
    const response = await fetch(`${this.baseURL}/userinfo`, {
      headers: {
        'Authorization': `Bearer ${this.token}`,
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    });
    
    if (response.ok) {
      return await response.json();
    }
    throw new Error('Failed to get user info');
  }

  async logout() {
    const response = await fetch(`${this.baseURL}/logout`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${this.token}`,
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    });
    
    localStorage.removeItem('authToken');
    this.token = null;
    return response.ok;
  }

  isAuthenticated() {
    return !!this.token;
  }

  getAuthHeaders() {
    return {
      'Authorization': `Bearer ${this.token}`,
      'Content-Type': 'application/json',
    };
  }
}

// Usage in your React/Vue/Angular components
const authService = new AuthService();

// Registration example
async function handleRegister(formData) {
  try {
    const result = await authService.register(formData);
    console.log('Registration successful:', result);
    // Redirect to dashboard or login page
  } catch (error) {
    console.error('Registration error:', error.message);
    // Show error message to user
  }
}

// Login example  
async function handleLogin(username, password) {
  try {
    const result = await authService.login(username, password);
    console.log('Login successful:', result);
    // Redirect to dashboard
  } catch (error) {
    console.error('Login error:', error.message);
    // Show error message to user
  }
}
```

### API Service Helper
```javascript
class ApiService {
  constructor() {
    this.authService = new AuthService();
  }

  async makeRequest(url, options = {}) {
    const defaultOptions = {
      headers: {
        'Content-Type': 'application/json',
        ...this.authService.getAuthHeaders(),
      },
      credentials: 'include',
    };

    const response = await fetch(url, { ...defaultOptions, ...options });
    
    if (response.status === 401) {
      // Token expired or invalid
      this.authService.logout();
      window.location.href = '/login';
      return;
    }

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
  }

  // User Service APIs
  async getUserProfile() {
    return this.makeRequest('http://localhost:8083/api/users/profile');
  }

  async updateUserProfile(profileData) {
    return this.makeRequest('http://localhost:8083/api/users/profile', {
      method: 'PUT',
      body: JSON.stringify(profileData),
    });
  }

  async getUserAddresses() {
    return this.makeRequest('http://localhost:8083/api/users/addresses');
  }
}
```

## CORS Configuration
The backend is configured to allow requests from:
- http://localhost:3000 (your frontend)
- http://127.0.0.1:3000
- http://localhost:3001 (alternative port)

## Security Notes
1. **JWT Tokens**: Store JWT tokens securely (localStorage or httpOnly cookies)
2. **HTTPS**: In production, use HTTPS for all communications
3. **Token Expiry**: Handle token expiration gracefully (currently set to 24 hours)
4. **Error Handling**: Always handle authentication errors and redirect to login

## Environment Variables for Frontend
Create a `.env` file in your frontend project:

```env
# Development
REACT_APP_API_BASE_URL=http://localhost:8082
REACT_APP_USER_SERVICE_URL=http://localhost:8083
REACT_APP_ADMIN_SERVICE_URL=http://localhost:8099

# Or for Vue.js
VUE_APP_API_BASE_URL=http://localhost:8082
VUE_APP_USER_SERVICE_URL=http://localhost:8083
VUE_APP_ADMIN_SERVICE_URL=http://localhost:8099

# Or for Angular
NG_APP_API_BASE_URL=http://localhost:8082
NG_APP_USER_SERVICE_URL=http://localhost:8083
NG_APP_ADMIN_SERVICE_URL=http://localhost:8099
```

## Testing Endpoints
You can test the endpoints using:
1. **Postman**: Import the API collection
2. **curl**: Command line testing
3. **Browser DevTools**: Network tab to inspect requests
4. **Thunder Client** (VS Code extension)

## Next Steps
1. Start your frontend on port 3000
2. Test authentication endpoints first
3. Implement user registration/login flow
4. Add protected routes that require authentication
5. Test user profile and address management
6. Integrate with other services as needed

The backend services should now be properly configured to work with your frontend on port 3000!