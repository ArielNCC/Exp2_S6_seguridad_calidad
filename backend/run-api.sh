#!/bin/bash

# Script para iniciar la API Pet Adoption System
# Uso: ./run-api.sh

echo "╔════════════════════════════════════════════════════════════╗"
echo "║  Pet Adoption API - Iniciando Servidor                    ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Verificar si Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Por favor instala Maven primero."
    exit 1
fi

echo "🔨 Compilando el proyecto..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Error durante la compilación"
    exit 1
fi

echo ""
echo "✅ Compilación exitosa"
echo ""
echo "🚀 Iniciando servidor..."
echo ""
echo "📍 La API estará disponible en:"
echo "   - Base URL: http://localhost:8080"
echo "   - Swagger UI: http://localhost:8080/swagger-ui.html"
echo "   - API Docs JSON: http://localhost:8080/v3/api-docs"
echo "   - API Docs YAML: http://localhost:8080/v3/api-docs.yaml"
echo ""

java -jar target/backend-0.0.1-SNAPSHOT.jar
