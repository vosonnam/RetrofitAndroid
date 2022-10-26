-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3306
-- Thời gian đã tạo: Th9 02, 2021 lúc 09:46 AM
-- Phiên bản máy phục vụ: 10.3.20-MariaDB
-- Phiên bản PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sinhvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `masv` int(11) NOT NULL AUTO_INCREMENT,
  `tensv` varchar(255) NOT NULL,
  `gt` varchar(255) NOT NULL,
  `lop` varchar(255) NOT NULL,
  `anhsv` text NOT NULL,
  PRIMARY KEY (`masv`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `student`
--

INSERT INTO `student` (`masv`, `tensv`, `gt`, `lop`, `anhsv`) VALUES
(3, 'HÃ²a ThÃ¢n', 'Ná»¯', 'KhÃ³a 62', 'http://192.168.1.48/sinhvien/upload/file/1384525571.jpg'),
(5, 'Khang Hy', 'Nam', 'KhÃ³a 59', 'http://192.168.1.48/sinhvien/upload/file/1719042795.jpg'),
(7, 'LÆ°u GÃ¹', 'Nam', 'KhÃ³a 60', 'http://192.168.1.48/sinhvien/upload/file/1688026359.jpg'),
(8, 'thÃ¢n', 'Nam', 'KhÃ³a 59', 'http://192.168.1.48/sinhvien/upload/file/267240640.jpg'),
(10, 'on', 'Nam', 'KhÃ³a 59', 'http://192.168.1.48/sinhvien/upload/file/1518785170.jpg'),
(11, 'hoa', 'Ná»¯', 'KhÃ³a 59', 'http://192.168.1.48/sinhvien/upload/file/917763215.jpg'),
(12, 'khÃª', 'Nam', 'KhÃ³a 62', 'http://192.168.1.48/sinhvien/upload/file/8316431.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`username`, `password`) VALUES
('nam', '1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `userlog`
--

DROP TABLE IF EXISTS `userlog`;
CREATE TABLE IF NOT EXISTS `userlog` (
  `username` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `logout` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `userlog`
--

INSERT INTO `userlog` (`username`, `login`, `logout`, `status`) VALUES
('nam', '18 08 2021 20:22 CH', '', 'Login Success'),
('nam', '18 08 2021 20:22 CH', '', 'Login Success'),
('nam', '23 08 2021 22:07 CH', '', 'Login Success'),
('nam', '30 08 2021 23:12 CH', '30 08 2021 23:12 CH', 'Login Success'),
('nam', '31 08 2021 11:49 SA', '', 'Login Success'),
('nam', '31 08 2021 11:52 SA', '', 'Login Success'),
('nam', '02 09 2021 14:41 CH', '', 'Login Success'),
('nam', '02 09 2021 14:51 CH', '', 'Login Success'),
('nam', '02 09 2021 14:51 CH', '', 'Login Success'),
('nam', '02 09 2021 14:51 CH', '', 'Login Success'),
('nam', '02 09 2021 15:05 CH', '', 'Login Success'),
('nam', '02 09 2021 15:14 CH', '', 'Login Success'),
('nam', '02 09 2021 15:14 CH', '', 'Login Success'),
('nam', '02 09 2021 15:19 CH', '', 'Login Success'),
('nam', '02 09 2021 15:22 CH', '', 'Login Success'),
('nam', '02 09 2021 16:26 CH', '', 'Login Success'),
('nam', '02 09 2021 16:26 CH', '', 'Login Success'),
('nam', '02 09 2021 16:33 CH', '', 'Login Success'),
('nam', '02 09 2021 16:35 CH', '', 'Login Success');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
