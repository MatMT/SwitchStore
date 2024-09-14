CREATE TABLE estudiantes (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             dui VARCHAR(10) NOT NULL UNIQUE,
                             nombre_completo VARCHAR(255) NOT NULL,
                             direccion TEXT,
                             email VARCHAR(255) NOT NULL,
                             fecha_nacimiento DATE NOT NULL,
                             telefono VARCHAR(15),
                             sexo ENUM('Masculino', 'Femenino') NOT NULL
);

INSERT INTO estudiantes (dui, nombre_completo, direccion, email, fecha_nacimiento, telefono, sexo)
VALUES
    ('12345678-9', 'Juan Pérez', '123 Calle Principal, San Salvador', 'juan.perez@example.com', '1995-08-10', '7890-1234', 'Masculino'),
    ('98765432-1', 'María López', '456 Avenida Central, Santa Tecla', 'maria.lopez@example.com', '1990-12-05', '7654-3210', 'Femenino'),
    ('45678912-3', 'Carlos Rodríguez', '789 Colonia Escalón, San Salvador', 'carlos.rodriguez@example.com', '1988-06-25', '1234-5678', 'Masculino'),
    ('32165498-7', 'Ana González', '1010 Boulevard Venezuela, San Miguel', 'ana.gonzalez@example.com', '1992-11-15', '9876-5432', 'Femenino'),
    ('65498732-5', 'José Martínez', '2020 Calle El Progreso, La Libertad', 'jose.martinez@example.com', '1985-03-30', '4321-6789', 'Masculino');
