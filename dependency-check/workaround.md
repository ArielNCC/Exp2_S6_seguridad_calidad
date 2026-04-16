# Workaround: Ejecutar OWASP Dependency Check localmente sin Maven instalado

> [!NOTE] Realizar una sola vez para varios proyectos.
Este procedimiento permite generar el reporte de vulnerabilidades de dependencias en tu entorno local, usando Maven Wrapper (`./mvnw`) y sin requerir Maven instalado globalmente. Incluye la descarga manual de la base de datos de vulnerabilidades.

---

## 1. Agregar el plugin al `pom.xml`

Agrega el siguiente bloque dentro de `<build><plugins>` en tu `pom.xml` (si no está ya configurado):

```xml
            <plugin>
              <groupId>org.owasp</groupId>
              <artifactId>dependency-check-maven</artifactId>
              <version>12.2.1</version>
              <executions>
                  <execution>
                      <goals>
                          <goal>check</goal>
                      </goals>
                  </execution>
              </executions>
            </plugin>
```

---

## 2. Descargar la base de datos de vulnerabilidades

### Opción A: Usando Maven Wrapper (recomendado)

En la raíz del proyecto (por ejemplo, en la carpeta `backend`), ejecuta:

```bash
./mvnw org.owasp:dependency-check-maven:update-only
```

Esto descargará la base de datos NVD en la ruta por defecto (`~/.m2/repository/org/owasp/dependency-check-data/`).

### Opción B: Descarga manual (si no tienes acceso a Maven Wrapper)

1. Descarga el binario desde [OWASP Dependency Check Releases](https://github.com/jeremylong/DependencyCheck/releases) y extrae el ZIP.
2. Ejecuta en la carpeta extraída:
   - Linux/macOS:
     ```bash
     ./dependency-check/bin/dependency-check.sh --updateonly
     ```
   - Windows:
     ```cmd
     dependency-check\bin\dependency-check.bat --updateonly
     ```
3. Copia la carpeta `data/` generada a tu máquina local.

---

## 3. Ejecutar el análisis de dependencias

En la raíz del proyecto (por ejemplo, en la carpeta `backend`), ejecuta:

```bash
./mvnw org.owasp:dependency-check-maven:check -Ddependency-check.noupdate=true
```

Si descargaste la base de datos manualmente y la tienes en una ruta personalizada, agrega el parámetro:

```bash
./mvnw org.owasp:dependency-check-maven:check -Ddependency-check.noupdate=true -Ddata.directory=/ruta/a/data
```

> Reemplaza `/ruta/a/data` por la ruta donde copiaste la carpeta `data`.

---

## 4. Ubicación del reporte

El reporte se generará en:

```
.../target/dependency-check-report.html
```

Ábrelo en tu navegador para revisar los resultados.

---

**Notas:**
- Solo necesitas tener Java instalado, no Maven global.
- Si el análisis falla por la base de datos, asegúrate de que la ruta a `data` es correcta.
- Este procedimiento es válido para entornos locales con acceso a Internet para la descarga inicial de la base de datos.
