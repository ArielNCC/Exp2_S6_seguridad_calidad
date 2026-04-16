# Especificações da API - Pet Adoption System

Arquivos de especificación de la API generados en formatos legibles para IA e integración con herramientas frontend.

## 📁 Contenido

Este directorio contiene las siguientes especificaciones:

### 1. **openapi-spec.yaml**
Especificación completa en formato OpenAPI 3.0.0 (YAML)

**Uso**:
- Compatible con Swagger UI
- Compatible con Postman (importar como URL)
- Compatible con ReDoc
- Ideal para documentación automática

**Para importar en Postman**:
1. Abre Postman
2. File → Import
3. Selecciona "Link" y pega: `file:///ruta/a/openapi-spec.yaml`

### 2. **api-spec.json**
Especificación en formato JSON estructurado para parseo automático

**Uso**:
- Fácil de parsear con JavaScript/Node.js
- Compatible con herramientas de generación de código
- Ideal para automatización en CI/CD
- Estructura clara con componentes reutilizables

**Ejemplo de parseo en JavaScript**:
```javascript
fetch('docs/api-spec.json')
  .then(res => res.json())
  .then(spec => {
    spec.endpoints.forEach(endpoint => {
      console.log(`${endpoint.method} ${endpoint.path}`);
    });
  });
```

### 3. **API_DOCUMENTATION.md**
Documentación legible en Markdown para humanos

**Uso**:
- Lectura directa en GitHub
- Generación de documentación sitioweb
- Ejemplos de curl listos para copiar/pegar
- Explicaciones detalladas

---

## 🚀 Acceso a través de la API en vivo

Una vez que el servidor está ejecutándose, puedes acceder a:

### Swagger UI (Interfaz Web Interactiva)
```
http://localhost:8080/swagger-ui.html
```
- Interfaz gráfica para explorar endpoints
- Prueba endpoints directamente desde el navegador
- Visualización de esquemas JSON

### JSON API Docs
```
http://localhost:8080/v3/api-docs
```
Especificación en formato JSON. Útil para herramientas automáticas.

### YAML API Docs
```
http://localhost:8080/v3/api-docs.yaml
```
Especificación en formato YAML. Compatible con la mayoría de herramientas.

---

## 📊 Resumen de Endpoints

| Método | Ruta | Protegido | Descripción |
|--------|------|-----------|-------------|
| POST | /login | No | Autenticarse y obtener token |
| POST | /users/register | Sí | Registrar usuario con contraseña hasheada |
| GET | /pets | No | Listar todas las mascotas |
| POST | /pets | Sí | Crear nueva mascota |
| GET | /pets/{id} | No | Obtener mascota por ID |
| PUT | /pets/{id} | Sí | Actualizar mascota |
| DELETE | /pets/{id} | Sí | Eliminar mascota |
| GET | /pets/available | No | Listar mascotas disponibles |
| GET | /pets/search | No | Buscar mascotas por criterios |
| GET | /patients | Sí | Listar todos los pacientes |
| POST | /patients | Sí | Crear nuevo paciente |
| GET | /patients/{id} | Sí | Obtener paciente por ID |
| GET | /invoices | Sí | Listar facturas |
| GET | /invoices/{id} | Sí | Obtener factura por ID |
| GET | /invoices/appointment/{appointmentId} | Sí | Obtener factura por visita |
| POST | /invoices/appointments/{appointmentId} | Sí | Generar factura por visita |

---

## 🔐 Autenticación

Todos los endpoints protegidos requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

**Para obtener un token**:
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

**Para registrar un usuario**:
```bash
TOKEN="<token_jwt>"

curl -X POST http://localhost:8080/users/register \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "recepcionista01",
    "email": "recepcion@duocvet.cl",
    "password": "MiClaveSegura2026!"
  }'
```

---

## 🛠️ Herramientas Recomendadas para el Frontend

### Para inspeccionar la API:
- **Postman**: Importa `openapi-spec.yaml`
- **Insomnia**: Importa `openapi-spec.yaml`
- **Thunder Client** (VS Code): Importa `openapi-spec.yaml`

### Para generar código cliente:
- **OpenAPI Generator**: Genera SDKs en múltiples lenguajes
- **Swagger Codegen**: Similar a OpenAPI Generator
- **graphql-code-generator**: Si necesitas GraphQL

### Para documentación web:
- **ReDoc**: Visualización hermosa de OpenAPI
- **Swagger UI**: Interfaz interactiva
- **Slate**: Documentación estilo markdown

---

## 📝 Ejemplos Rápidos

### Buscar mascotas por especie
```bash
curl -s "http://localhost:8080/pets/search?species=Perro" | jq
```

### Buscar perros machos en Santiago
```bash
curl -s "http://localhost:8080/pets/search?species=Perro&gender=Macho&location=Santiago" | jq
```

### Obtener mascotas disponibles
```bash
curl -s "http://localhost:8080/pets/available" | jq
```

### Crear una mascota (requiere autenticación)
```bash
TOKEN="<token_jwt>"

curl -X POST http://localhost:8080/pets \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Luna",
    "species": "Gato",
    "breed": "Persa",
    "age": 2,
    "gender": "Hembra",
    "location": "Valparaíso",
    "photos": ["https://example.com/luna.jpg"]
  }'
```

---

## 🔄 Flujo de Integración Frontend

1. **Login**: Obtener token JWT
2. **Guardar token**: En localStorage o sessionStorage
3. **Usar token**: En header `Authorization: Bearer <token>` para operaciones protegidas
4. **Buscar mascotas**: Sin autenticación (GET /pets/search)
5. **Administrar mascotas**: Con autenticación (POST, PUT, DELETE)

---

## 📦 Tipos de Datos

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
  "photos": ["https://example.com/max.jpg"],
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

### Invoice (Factura)
```json
{
  "id": 1,
  "appointmentId": 10,
  "issueDate": "2026-03-28",
  "vatRate": 0.19,
  "subtotal": 43000,
  "vatAmount": 8170,
  "total": 51170,
  "notes": "Paciente estable. Control en 10 dias.",
  "items": [
    {
      "id": 1,
      "type": "SERVICE",
      "description": "Consulta general",
      "quantity": 1,
      "unitPrice": 25000,
      "lineTotal": 25000
    }
  ]
}
```

---

## 📞 Soporte

Para dudas sobre la API:
- Revisa API_DOCUMENTATION.md para detalles completos
- Accede a /swagger-ui.html para exploración interactiva
- Consulta los ejemplos en curl en este documento

---

Generado: 2026-03-28
Versión API: 1.0.0
Proyecto: DUOC UC - CDY2203
