-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2023 at 03:11 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ltm`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`username`, `password`) VALUES
('52100123', 'MH9zRfh5kuxs18l1JV0ETw=='),
('52100987', 'MH9zRfh5kuxs18l1JV0ETw=='),
('abc', 'O58i9BFEzwdPDpc7R3UJWGazyawG3WwElVXyUr+LqDM='),
('abcd', 'nXQrCp+4dQ3ID4SOSUMgDA=='),
('MinhPhong', '7Qik19gpzyZL1wokK/Jd4w=='),
('newuser', 'HKQm/XQ1/FXddy/3NR+c8w=='),
('phony', 'MH9zRfh5kuxs18l1JV0ETw=='),
('user', 'HKQm/XQ1/FXddy/3NR+c8w=='),
('uwu', 'PtFBl3tdAKb5mqENkErO5A==');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `username` varchar(50) DEFAULT NULL,
  `messagetext` varchar(255) DEFAULT NULL,
  `secretKey` varchar(255) DEFAULT NULL,
  `timemessage` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`username`, `messagetext`, `secretKey`, `timemessage`) VALUES
('user', 'nv+mFCqUcyLDrZDleDfwW606/tAycdbBnuKejNAXDKE=', 'FDCF1D811244CE2CC92AFBD0B4A972F3', ''),
('MinhPhong', '5mo5ZsJOpcXPVoKAUDAPxhC/N20K6aQHrfmFM16w5pM=', '4A29089425332BEB17EFE7FBDD459DEE', '30/03/2023 07:56:34'),
('MinhPhong', '8ZjKRaIfJA4jRgE/AjwsXDUZTwQVOYcgF55NwNr8GpA=', '4A29089425332BEB17EFE7FBDD459DEE', '30/03/2023 07:56:58'),
('MinhPhong', 'hgeOnP4Pdel7DEBo4rWOr/eWeKJgEpieKPkcYkjX1yo=', '4A29089425332BEB17EFE7FBDD459DEE', '30/03/2023 07:57:04'),
('MinhPhong', 'lzFhYP6vef1rWWkShp+PidVICIaApHN9EmM6/UqwoHo=', '4A29089425332BEB17EFE7FBDD459DEE', '30/03/2023 07:57:14'),
('MinhPhong', 'z5V5bLeI6jXOUI4DNrT0sN3ADMSbRZGZtV40txtaK+s=', 'EA96BA95924A118558C3925D421CE76C', '30/03/2023 08:25:35'),
('MinhPhong', 'uBz+DnpYhLP31ndWNQe1uNBOkSqOPl7PZKdGDP5QnE0=', '343478D0927519191EBC9A36CA16D639', '30/03/2023 08:29:19'),
('MinhPhong', 'cCxwQx4Q/m07AuHTVI51UQ==', '15B105BF0858A087CA04CE4FDD2440FF', '30/03/2023 08:29:33'),
('newuser', 'XQM3pFSz9Q7cqIq5yr+EshHW3h+xzY7STYWVSrXBJUp4W+UxjwGPU/+QtHx1h2ON', 'B9C617CD6DB8594EEDDB01069C8FD680', '31/03/2023 04:45:47'),
('newuser', 'PhXAtKp794VS1nibfibRj7Jkq2Bi39d88PPHUGyp3XFEBcqsAPUKBQ7z6GVKzcef', 'B9C617CD6DB8594EEDDB01069C8FD680', '31/03/2023 04:45:55');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD KEY `username` (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`username`) REFERENCES `client` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
