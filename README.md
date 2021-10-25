# SistemaBancario
# El programa de sistema bancario. mysql
SistemaBanca

-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 25, 2021 at 03:28 PM
-- Server version: 5.7.24
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistemabancario_nombreadonis`
--

-- --------------------------------------------------------

--
-- Table structure for table `cuenta`
--

CREATE TABLE `cuenta` (
  `id` int(11) NOT NULL,
  `noCuenta` varchar(200) NOT NULL,
  `titular` varchar(300) NOT NULL,
  `balance` double NOT NULL,
  `activa` tinyint(1) NOT NULL,
  `moneda` varchar(100) NOT NULL,
  `tipo` enum('Cuenta de Ahorros','Cuenta Corriente') NOT NULL,
  `balanceMaximo` double NOT NULL,
  `intereses` double NOT NULL,
  `tieneSeguro` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cuenta`
--

INSERT INTO `cuenta` (`id`, `noCuenta`, `titular`, `balance`, `activa`, `moneda`, `tipo`, `balanceMaximo`, `intereses`, `tieneSeguro`) VALUES
(1, '100-000-000-1', 'Dele Ali', 2000, 1, 'Dolares', 'Cuenta de Ahorros', 3000, 1.45, 1),
(2, '100-000-000-2', 'Harry Kane', 3000, 1, 'Dolares', 'Cuenta de Ahorros', 4500, 2.78, 1),
(3, '100-000-000-3', 'Min Son', 4000, 1, 'Dolares', 'Cuenta de Ahorros', 6500, 4.56, 1),
(4, '100-000-000-4', 'Fidel perez', 3000, 1, 'dolares', 'Cuenta Corriente', 4000, 1.23, 1),
(5, '100-000-000-5', 'Pamela Membreño', 2300, 1, 'dolares', 'Cuenta Corriente', 5000, 2.23, 1),
(8, '100-000-000-6', 'Danna Valentina', 1300, 1, 'dolares', 'Cuenta Corriente', 3000, 4.23, 1);

-- --------------------------------------------------------

--
-- Table structure for table `transaccion`
--

CREATE TABLE `transaccion` (
  `id` int(11) NOT NULL,
  `monto` double NOT NULL,
  `cuenta` varchar(200) NOT NULL,
  `detalle` varchar(300) NOT NULL,
  `tipoTransaccion` varchar(300) NOT NULL,
  `cuentaDeTransaccion` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transaccion`
--

INSERT INTO `transaccion` (`id`, `monto`, `cuenta`, `detalle`, `tipoTransaccion`, `cuentaDeTransaccion`) VALUES
(1, 2500, '100-000-000-1', 'pago de luz', 'por medio de una transferencia sinpe', '100-000-000-2'),
(2, 1300, '100-000-000-3', 'pago de trabajo de la semana', 'por medio de una transferencia sinpe', '100-000-000-4'),
(3, 23000, '100-000-000-5', 'pago de servicios profesionales', 'tranferencia bancaria', '100-000-000-1'),
(4, 900, '100-000-000-4', 'pago deapuestas', 'tipo tranferencia sinpe', '100-000-000-5');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaccion`
--
ALTER TABLE `transaccion`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cuenta`
--
ALTER TABLE `cuenta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transaccion`
--
ALTER TABLE `transaccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
