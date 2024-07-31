-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 31-07-2024 a las 17:38:51
-- Versión del servidor: 8.0.30
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `switch_store_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games`
--

CREATE TABLE `games` (
                         `id` int NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `genre` varchar(100) DEFAULT NULL,
                         `price` decimal(10,2) NOT NULL,
                         `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `games`
--

INSERT INTO `games` (`id`, `title`, `genre`, `price`, `created_at`, `updated_at`) VALUES
                                                                                      (1, 'The Legend of Zelda: Breath of the Wild', 'Action-adventure', 59.99, '2017-03-03 00:00:00', '2017-03-03 00:00:00'),
                                                                                      (2, 'Super Mario Odyssey', 'Platform', 59.99, '2017-10-27 00:00:00', '2017-10-27 00:00:00'),
                                                                                      (3, 'Animal Crossing: New Horizons', 'Simulation', 59.99, '2020-03-20 00:00:00', '2020-03-20 00:00:00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `games`
--
ALTER TABLE `games`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `games`
--
ALTER TABLE `games`
    MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
