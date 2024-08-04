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
