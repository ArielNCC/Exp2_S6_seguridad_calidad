# Guía de Integración Frontend - Pet Adoption API

Guía para integrar la API Pet Adoption en aplicaciones frontend (React, Vue, Angular, etc.).

## 🌐 Configuración Básica

### 1. Establecer Base URL

```javascript
// config.js o constants.js
export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
export const API_VERSION = 'v1';
```

### 2. Configurar Cliente HTTP

#### Usando Fetch API (nativo)
```javascript
// api-client.js
export async function apiCall(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  // Agregar token JWT si existe y no es un login
  const token = localStorage.getItem('authToken');
  if (token && !endpoint.includes('/login')) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(url, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || `HTTP ${response.status}`);
  }

  return response.json();
}
```

#### Usando Axios
```javascript
// api-client.js
import axios from 'axios';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

// Interceptor para agregar token JWT
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default apiClient;
```

---

## 🔐 Autenticación

### Login y Guardar Token

```javascript
async function login(username, password) {
  try {
    const token = await apiCall('/login', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    });

    // Guardar token
    localStorage.setItem('authToken', token);
    localStorage.setItem('username', username);

    return { success: true, token };
  } catch (error) {
    console.error('Login failed:', error);
    return { success: false, error: error.message };
  }
}
```

### Logout

```javascript
function logout() {
  localStorage.removeItem('authToken');
  localStorage.removeItem('username');
  // Redirigir a login
}
```

### Verificar Autenticación

```javascript
function isAuthenticated() {
  return !!localStorage.getItem('authToken');
}

function getAuthToken() {
  return localStorage.getItem('authToken');
}
```

---

## 🐾 Operaciones con Mascotas

### Obtener Todas las Mascotas

```javascript
async function getAllPets() {
  try {
    const pets = await apiCall('/pets');
    return pets;
  } catch (error) {
    console.error('Error fetching pets:', error);
    return [];
  }
}
```

### Obtener Mascota por ID

```javascript
async function getPetById(id) {
  try {
    const pet = await apiCall(`/pets/${id}`);
    return pet;
  } catch (error) {
    console.error(`Error fetching pet ${id}:`, error);
    return null;
  }
}
```

### Obtener Mascotas Disponibles

```javascript
async function getAvailablePets() {
  try {
    const pets = await apiCall('/pets/available');
    return pets;
  } catch (error) {
    console.error('Error fetching available pets:', error);
    return [];
  }
}
```

### Buscar Mascotas por Criterios

```javascript
async function searchPets(filters = {}) {
  try {
    const queryString = new URLSearchParams();

    if (filters.species) queryString.append('species', filters.species);
    if (filters.gender) queryString.append('gender', filters.gender);
    if (filters.location) queryString.append('location', filters.location);
    if (filters.age) queryString.append('age', filters.age);
    if (filters.status) queryString.append('status', filters.status);

    const url = `/pets/search${queryString ? '?' + queryString : ''}`;
    const pets = await apiCall(url);
    return pets;
  } catch (error) {
    console.error('Error searching pets:', error);
    return [];
  }
}

// Uso:
const results = await searchPets({
  species: 'Perro',
  gender: 'Macho',
  location: 'Santiago'
});
```

### Crear Mascota (requiere autenticación)

```javascript
async function createPet(petData) {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const newPet = await apiCall('/pets', {
      method: 'POST',
      body: JSON.stringify(petData),
    });

    return { success: true, data: newPet };
  } catch (error) {
    console.error('Error creating pet:', error);
    return { success: false, error: error.message };
  }
}

// Uso:
const result = await createPet({
  name: 'Max',
  species: 'Perro',
  breed: 'Golden Retriever',
  age: 3,
  gender: 'Macho',
  location: 'Santiago',
  photos: ['https://example.com/max.jpg']
});
```

### Actualizar Mascota (requiere autenticación)

```javascript
async function updatePet(id, updates) {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const updatedPet = await apiCall(`/pets/${id}`, {
      method: 'PUT',
      body: JSON.stringify(updates),
    });

    return { success: true, data: updatedPet };
  } catch (error) {
    console.error(`Error updating pet ${id}:`, error);
    return { success: false, error: error.message };
  }
}

// Uso: Marcar como adoptada
await updatePet(1, { status: 'adopted' });
```

### Eliminar Mascota (requiere autenticación)

```javascript
async function deletePet(id) {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const response = await apiCall(`/pets/${id}`, {
      method: 'DELETE',
    });

    return { success: true, message: response.message };
  } catch (error) {
    console.error(`Error deleting pet ${id}:`, error);
    return { success: false, error: error.message };
  }
}
```

---

## 👨‍⚕️ Operaciones con Pacientes

### Obtener Todos los Pacientes (requiere autenticación)

```javascript
async function getAllPatients() {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const patients = await apiCall('/patients');
    return patients;
  } catch (error) {
    console.error('Error fetching patients:', error);
    return [];
  }
}
```

### Obtener Paciente por ID (requiere autenticación)

```javascript
async function getPatientById(id) {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const patient = await apiCall(`/patients/${id}`);
    return patient;
  } catch (error) {
    console.error(`Error fetching patient ${id}:`, error);
    return null;
  }
}
```

### Crear Paciente (requiere autenticación)

```javascript
async function createPatient(patientData) {
  try {
    if (!isAuthenticated()) {
      throw new Error('Se requiere autenticación');
    }

    const newPatient = await apiCall('/patients', {
      method: 'POST',
      body: JSON.stringify(patientData),
    });

    return { success: true, data: newPatient };
  } catch (error) {
    console.error('Error creating patient:', error);
    return { success: false, error: error.message };
  }
}

// Uso:
await createPatient({
  name: 'Firulais',
  species: 'Perro',
  breed: 'Labrador',
  age: 5,
  owner: 'Juan Pérez'
});
```

---

## ⚙️ Ejemplos de Componentes (React)

### Hook para Autenticación

```javascript
// useAuth.js
import { useState, useCallback } from 'react';

export function useAuth() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem('authToken')
  );
  const [user, setUser] = useState(localStorage.getItem('username'));

  const login = useCallback(async (username, password) => {
    try {
      const token = await apiCall('/login', {
        method: 'POST',
        body: JSON.stringify({ username, password }),
      });

      localStorage.setItem('authToken', token);
      localStorage.setItem('username', username);
      setIsAuthenticated(true);
      setUser(username);
      return { success: true };
    } catch (error) {
      return { success: false, error: error.message };
    }
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('username');
    setIsAuthenticated(false);
    setUser(null);
  }, []);

  return { isAuthenticated, user, login, logout };
}
```

### Componente de Búsqueda de Mascotas

```jsx
// PetSearch.jsx
import React, { useState, useEffect } from 'react';

export function PetSearch() {
  const [filters, setFilters] = useState({
    species: '',
    gender: '',
    location: '',
    age: '',
  });
  const [pets, setPets] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    setLoading(true);
    try {
      const results = await searchPets(filters);
      setPets(results);
    } catch (error) {
      console.error('Search failed:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Buscar Mascotas</h2>

      <input
        type="text"
        placeholder="Especie"
        value={filters.species}
        onChange={(e) => setFilters({ ...filters, species: e.target.value })}
      />

      <select
        value={filters.gender}
        onChange={(e) => setFilters({ ...filters, gender: e.target.value })}
      >
        <option value="">Seleccionar género</option>
        <option value="Macho">Macho</option>
        <option value="Hembra">Hembra</option>
      </select>

      <input
        type="text"
        placeholder="Ubicación"
        value={filters.location}
        onChange={(e) => setFilters({ ...filters, location: e.target.value })}
      />

      <input
        type="number"
        placeholder="Edad"
        value={filters.age}
        onChange={(e) => setFilters({ ...filters, age: e.target.value })}
      />

      <button onClick={handleSearch} disabled={loading}>
        {loading ? 'Buscando...' : 'Buscar'}
      </button>

      <div>
        {pets.map((pet) => (
          <div key={pet.id} className="pet-card">
            <h3>{pet.name}</h3>
            <p>Especie: {pet.species}</p>
            <p>Raza: {pet.breed}</p>
            <p>Edad: {pet.age} años</p>
            <p>Sexo: {pet.gender}</p>
            <p>Ubicación: {pet.location}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
```

---

## 📝 Manejo de Errores

### Interceptor Global de Errores

```javascript
async function apiCall(endpoint, options = {}) {
  try {
    // ... hacer la llamada
  } catch (error) {
    // Manejar diferentes tipos de errores
    if (error.response?.status === 401) {
      // Token expirado - redirigir a login
      logout();
      window.location.href = '/login';
    } else if (error.response?.status === 403) {
      // Acceso denegado
      console.error('Acceso denegado');
    } else if (error.response?.status === 404) {
      // No encontrado
      console.error('Recurso no encontrado');
    } else {
      // Otros errores
      console.error('Error:', error.message);
    }
    throw error;
  }
}
```

---

## 🧪 Pruebas

### Ejemplo de prueba con Jest + Fetch

```javascript
// api.test.js
describe('API Calls', () => {
  test('should fetch pets', async () => {
    const pets = await getAllPets();
    expect(Array.isArray(pets)).toBe(true);
  });

  test('should search pets', async () => {
    const pets = await searchPets({ species: 'Perro' });
    expect(Array.isArray(pets)).toBe(true);
  });

  test('should require auth for create', async () => {
    localStorage.removeItem('authToken');
    const result = await createPet({ name: 'Test' });
    expect(result.success).toBe(false);
  });
});
```

---

## 🔗 Links Útiles

- [Documentación API](./API_DOCUMENTATION.md)
- [OpenAPI Spec](./openapi-spec.yaml)
- [JSON Spec](./api-spec.json)
- Swagger UI: http://localhost:8080/swagger-ui.html

---

Generado: 2026-03-26
Versión: 1.0.0
