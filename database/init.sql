USE mydatabase;

-- ============================================
-- 1. TABLA DE USUARIOS
-- ============================================
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO user (username, email, password) VALUES
('admin', 'admin@ejemplo.com', 'password123'),
('user1', 'user1@ejemplo.com', 'password123'),
('user2', 'user2@ejemplo.com', 'password123');

-- ============================================
-- 2. TABLA DE PACIENTES
-- ============================================
CREATE TABLE IF NOT EXISTS patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(50),
    age INT,
    owner VARCHAR(100)
);

INSERT INTO patient (name, species, breed, age, owner) VALUES
('Bobby', 'Perro', 'Labrador', 5, 'Juan Perez'),
('Mishi', 'Gato', 'Siames', 3, 'Ana Lopez');

-- ============================================
-- 3. TABLA DE MASCOTAS (ADOPCIÓN)
-- ============================================
CREATE TABLE IF NOT EXISTS pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(50),
    breed VARCHAR(50),
    age INT,
    gender VARCHAR(20),
    location VARCHAR(100),
    status VARCHAR(50) DEFAULT 'available'
);

INSERT INTO pet (name, species, breed, age, gender, location, status) VALUES
('Max', 'Perro', 'Golden Retriever', 3, 'Macho', 'Refugio Central', 'available'),
('Luna', 'Gato', 'Persa', 2, 'Hembra', 'Refugio Centro', 'available'),
('Rocky', 'Perro', 'Shepherd', 4, 'Macho', 'Refugio Sur', 'adopted');

-- ============================================
-- 4. TABLA DE FOTOS DE MASCOTAS
-- ============================================
CREATE TABLE IF NOT EXISTS pet_photos (
    pet_id INT NOT NULL,
    photo_url VARCHAR(255),
    FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);

INSERT INTO pet_photos (pet_id, photo_url) VALUES
-- Max - Golden Retriever
(1, 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Golde33443.jpg/1024px-Golde33443.jpg'),
(1, 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Kitten_in_Pikeston_closeup.jpg/1200px-Kitten_in_Pikeston_closeup.jpg'),
-- Luna - Gato Persa
(2, 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1200px-Cat03.jpg'),
(2, 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Felis_catus_-_2010-0387.jpg/1024px-Felis_catus_-_2010-0387.jpg'),
-- Rocky - Shepherd
(3, 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Full_Collie_Rough.JPG/1200px-Full_Collie_Rough.JPG'),
(3, 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/YellowLabradorLooking_new.jpg/1024px-YellowLabradorLooking_new.jpg');

-- ============================================
-- 5. TABLA DE CITAS
-- ============================================
CREATE TABLE IF NOT EXISTS appointment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    date DATE,
    time TIME,
    reason VARCHAR(255),
    veterinarian VARCHAR(100),
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

INSERT INTO appointment (patient_id, date, time, reason, veterinarian) VALUES
(1, '2026-04-10', '10:00:00', 'Control anual', 'Dr. House'),
(2, '2026-04-11', '11:30:00', 'Vacunación', 'Dra. Smith');

-- ============================================
-- 6. TABLA DE FACTURAS
-- ============================================
CREATE TABLE IF NOT EXISTS invoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    issue_date DATE,
    vat_rate DECIMAL(5, 4),
    subtotal DECIMAL(10, 2),
    vat_amount DECIMAL(10, 2),
    total DECIMAL(10, 2),
    notes VARCHAR(255),
    FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE
);

INSERT INTO invoice (appointment_id, issue_date, vat_rate, subtotal, vat_amount, total, notes) VALUES
(1, '2026-04-10', 0.19, 100000.00, 19000.00, 119000.00, 'Pago recibido'),
(2, '2026-04-11', 0.19, 50000.00, 9500.00, 59500.00, 'Factura abierta');

-- ============================================
-- 7. TABLA DE LÍNEAS DE FACTURA
-- ============================================
CREATE TABLE IF NOT EXISTS invoice_line_item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    type VARCHAR(50),
    description VARCHAR(255),
    quantity INT,
    unit_price DECIMAL(10, 2),
    line_total DECIMAL(10, 2),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id) ON DELETE CASCADE
);

INSERT INTO invoice_line_item (invoice_id, type, description, quantity, unit_price, line_total) VALUES
(1, 'SERVICE', 'Consulta Veterinaria', 1, 50000.00, 50000.00),
(1, 'SERVICE', 'Examen Clínico', 1, 50000.00, 50000.00),
(2, 'MEDICATION', 'Vacuna Antirrábica', 1, 30000.00, 30000.00),
(2, 'SERVICE', 'Aplicación de Vacuna', 1, 20000.00, 20000.00);