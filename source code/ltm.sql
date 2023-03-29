-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 29, 2023 at 06:24 PM
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
('phony', 'MH9zRfh5kuxs18l1JV0ETw=='),
('uwu', 'PtFBl3tdAKb5mqENkErO5A==');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `username` varchar(50) DEFAULT NULL,
  `messagetext` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`username`, `messagetext`) VALUES
('52100987', 'Xin chào'),
('52100987', 'Hello'),
('MinhPhong', 'a: 1\nb: 0\nc: 0\nd: 1\ne: 0\nf: 0\ng: 2\nh: 2\ni: 1\nj: 0\nk: 0\nl: 0\nm: 1\nn: 3\no: 1\np: 1\nq: 0\nr: 0\ns: 0\nt: 0\nu: 0\nv: 0\nw: 0\nx: 0\ny: 0\nz: 0\n'),
('MinhPhong', 'a: 0\nb: 0\nc: 0\nd: 1\ne: 1\nf: 0\ng: 0\nh: 1\ni: 0\nj: 0\nk: 0\nl: 3\nm: 0\nn: 0\no: 2\np: 0\nq: 0\nr: 1\ns: 0\nt: 0\nu: 0\nv: 0\nw: 1\nx: 0\ny: 0\nz: 0\n'),
('MinhPhong', 'a: 4\nb: 0\nc: 4\nd: 2\ne: 0\nf: 0\ng: 0\nh: 1\ni: 5\nj: 3\nk: 0\nl: 0\nm: 0\nn: 3\no: 0\np: 0\nq: 0\nr: 0\ns: 4\nt: 0\nu: 0\nv: 0\nw: 0\nx: 0\ny: 0\nz: 0\n'),
('MinhPhong', 'a:1\nd:1\ng:2\nh:2\ni:1\nm:1\nn:3\no:1\np:1\n'),
('uwu', 'd:1\ne:1\nh:1\nl:3\no:2\nr:1\nw:1\n'),
('uwu', 'g:1\nh:2\ni:1\nm:1\nn:2\no:1\np:1\n'),
('MinhPhong', 'a:1\nd:1\ng:2\nh:2\ni:1\nm:1\nn:3\no:1\np:1\n');

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
