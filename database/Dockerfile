
# Imagen base oficial de MySQL
FROM mysql:8.0

# Variables de entorno para la configuración de la base de datos
ENV MYSQL_DATABASE=mydatabase \
        MYSQL_USER=myuser \
        MYSQL_PASSWORD=password \
        MYSQL_ROOT_PASSWORD=rootpassword

# Copiar el script de inicialización
COPY init.sql /docker-entrypoint-initdb.d/

# Exponer el puerto estándar de MySQL
EXPOSE 3306

# Mejorar robustez con check de salud y permisos
HEALTHCHECK --interval=10s --timeout=5s --start-period=30s --retries=3 \
    CMD mysqladmin ping -h 127.0.0.1 -u$MYSQL_USER -p$MYSQL_PASSWORD || exit 1
