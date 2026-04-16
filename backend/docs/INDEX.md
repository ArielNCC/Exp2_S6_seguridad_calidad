# 📚 Especificaciones de API - Pet Adoption System

Documentación completa e integrada de la API, generada en múltiples formatos para facilitar la integración con el frontend y herramientas automáticas.

## 📋 Archivos Disponibles

### 1. **📖 API_DOCUMENTATION.md** (10 KB)
Documentación completa en Markdown legible para humanos.

**Contenido**:
- Descripción de cada endpoint con ejemplos
- Parámetros requeridos y opcionales
- Respuestas esperadas con ejemplos JSON
- Códigos de error HTTP
- Ejemplos de curl listos para copiar/pegar
- Flujo completo de login → crear mascota

**Usar cuando**: Necesites entender cómo funciona cada endpoint

---

### 2. **🔧 api-spec.json** (13 KB)
Especificación estructurada en JSON, optimizada para parseo automático.

**Características**:
- Estructura jerárquica clara
- Componentes reutilizables
- Fácil de parsear con JavaScript/Python
- Compatible con herramientas de generación de código

**Usar cuando**: Necesites generar código cliente automáticamente

**Ejemplo de uso**:
```javascript
fetch('docs/api-spec.json')
  .then(r => r.json())
  .then(spec => {
    spec.endpoints.forEach(ep => {
      console.log(`${ep.method} ${ep.path}: ${ep.summary}`);
    });
  });
```

---

### 3. **📡 openapi-spec.yaml** (13 KB)
Especificación OpenAPI 3.0.0 en formato YAML.

**Características**:
- Estándar de industria para APIs REST
- Compatible con Swagger/Swagger UI
- Compatible con Postman, Insomnia
- Compatible con generadores de código
- Documentación interactiva automática

**Usar cuando**: Quieras integración con herramientas estándar

**Importar en Postman**:
1. File → Import → Link
2. Pega: `file:///backend/docs/openapi-spec.yaml`

---

### 4. **🚀 FRONTEND_INTEGRATION.md** (12 KB)
Guía paso a paso para integrar la API en aplicaciones frontend.

**Contenido**:
- Configuración de cliente HTTP (Fetch, Axios)
- Manejo de autenticación JWT
- Funciones listas para copiar/pegar
- Hooks y componentes React
- Interceptores de errores
- Ejemplos de pruebas

**Usar cuando**: Estés desarrollando el frontend

---

### 5. **📍 README.md** (5.3 KB)
Índice y guía de inicio rápido.

**Contenido**:
- Resumen de todos los archivos
- Instrucciones de acceso a Swagger UI
- Tabla de endpoints
- Herramientas recomendadas
- Ejemplos rápidos

**Usar cuando**: Necesites orientación general

---

## 🔗 Acceso en Vivo

Una vez que el servidor esté ejecutándose:

| Recurso | URL |
|---------|-----|
| **Swagger UI (Interfaz)** | http://localhost:8080/swagger-ui.html |
| **JSON API Docs** | http://localhost:8080/v3/api-docs |
| **YAML API Docs** | http://localhost:8080/v3/api-docs.yaml |

---

## 📊 Resumen de Endpoints

### 🔐 Autenticación
- `POST /login` - Obtener token JWT
- `POST /users/register` - Registrar usuario protegido

### 🐾 Mascotas
- `GET /pets` - Listar todas
- `GET /pets/{id}` - Obtener por ID
- `GET /pets/available` - Solo disponibles
- `GET /pets/search` - Búsqueda avanzada
- `POST /pets` - Crear (protegido)
- `PUT /pets/{id}` - Actualizar (protegido)
- `DELETE /pets/{id}` - Eliminar (protegido)

### 👨‍⚕️ Pacientes
- `GET /patients` - Listar todos (protegido)
- `GET /patients/{id}` - Obtener por ID (protegido)
- `POST /patients` - Crear (protegido)

### 🧾 Facturas
- `GET /invoices` - Listar facturas (protegido)
- `GET /invoices/{id}` - Obtener por ID (protegido)
- `GET /invoices/appointment/{appointmentId}` - Obtener por visita (protegido)
- `POST /invoices/appointments/{appointmentId}` - Generar factura por visita (protegido)

---

## 🎯 Para el Frontend

### Paso 1: Entender la API
Leer: **API_DOCUMENTATION.md**

### Paso 2: Configurar Cliente
Seguir: **FRONTEND_INTEGRATION.md**

### Paso 3: Implementar Búsqueda
Ejemplo en **FRONTEND_INTEGRATION.md** → Sección "Buscar Mascotas por Criterios"

### Paso 4: Verificar Integración
Usar: http://localhost:8080/swagger-ui.html

---

## 🛠️ Herramientas Recomendadas

### Para Inspeccionar:
- **Postman** - Importar `openapi-spec.yaml`
- **Insomnia** - Importar `openapi-spec.yaml`
- **RestClient (VS Code)** - Usar ejemplos de curl

### Para Documentación:
- **Swagger UI** - Acceso directo en /swagger-ui.html
- **ReDoc** - Herramienta web para mejor visualización

### Para Generar Código:
- **OpenAPI Generator** - Generar SDKs en múltiples lenguajes
- **Swagger Codegen** - Alternativa a OpenAPI Generator

---

## 💡 Consejos Rápidos

### Buscar Perros Disponibles en Santiago
```bash
curl "http://localhost:8080/pets/search?species=Perro&location=Santiago"
```

### Obtener Token (Login)
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'
```

### Registrar Usuario (Con Token)
```bash
curl -X POST http://localhost:8080/users/register \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"username":"recepcionista01","email":"recepcion@duocvet.cl","password":"MiClaveSegura2026!"}'
```

### Crear Mascota (Con Token)
```bash
curl -X POST http://localhost:8080/pets \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Luna",
    "species":"Gato",
    "breed":"Persa",
    "age":2,
    "gender":"Hembra",
    "location":"Valparaiso",
    "photos":["https://example.com/luna.jpg"]
  }'
```

---

## 📦 Esquema de Datos

### Pet (Mascota)
```json
{
  "id": 1,
  "name": "Max",
  "species": "Perro",
  "breed": "Golden Retriever",
  "age": 3,
  "gender": "Macho",
  "location": "Santiago",
  "photos": ["https://example.com/foto1.jpg"],
  "status": "available"
}
```

### Patient (Paciente)
```json
{
  "id": 1,
  "name": "Firulais",
  "species": "Perro",
  "breed": "Labrador",
  "age": 5,
  "owner": "Juan Pérez"
}
```

---

## ✅ Checklist de Integración

- [ ] Leer API_DOCUMENTATION.md
- [ ] Confgurar cliente HTTP (Fetch/Axios)
- [ ] Implementar autenticación (Login/Logout)
- [ ] Copiar funciones de FRONTEND_INTEGRATION.md
- [ ] Crear componentes de búsqueda
- [ ] Probar endpoints en Swagger UI
- [ ] Implementar manejo de errores
- [ ] Agregar Tests
- [ ] Documentar en el proyecto frontend

---

## 🔐 Seguridad y Autenticación

- **Tipo**: JWT Bearer Token
- **Header**: `Authorization: Bearer <token>`
- **Obtención**: POST /login (username + password)
- **Endpoints protegidos**: POST, PUT, DELETE, GET /patients

---

## 📞 Soporte y Referencias

**Documentación completa**: Ver archivos .md en esta carpeta

**Acceso interactivo**: http://localhost:8080/swagger-ui.html

**Ejemplos de código**: FRONTEND_INTEGRATION.md

**Referencia técnica**: openapi-spec.yaml

---

## ℹ️ Información General

- **Versión API**: 1.0.0
- **Framework**: Spring Boot 4.0.3
- **Java**: 21
- **Database**: MySQL
- **Autenticación**: JWT
- **Documentación OpenAPI**: 3.0.0

---

**Generado**: 26 de marzo de 2026  
**Proyecto**: DUOC UC - CDY2203  
**Responsable**: Equipo de Backend

---

## 🎓 Próximos Pasos

1. **Para el Frontend**: Comenzar con FRONTEND_INTEGRATION.md
2. **Para QA**: Usar Postman + openapi-spec.yaml  
3. **Para DevOps**: Revisar run-api.sh y configuración

¡Listo para integrar! 🚀
