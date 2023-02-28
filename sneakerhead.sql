-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 15, 2022 lúc 09:38 AM
-- Phiên bản máy phục vụ: 10.4.22-MariaDB
-- Phiên bản PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sneakerhead`
--
CREATE DATABASE IF NOT EXISTS `sneakerhead` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sneakerhead`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `brand`
--

CREATE TABLE IF NOT EXISTS `brand` (
  `brand_id` int(11) NOT NULL AUTO_INCREMENT,
  `brand_name` text NOT NULL,
  `brand_code` varchar(12) NOT NULL,
  `brand_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0: Ẩn\r\n1: Hiện',
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `brand`
--

INSERT INTO `brand` (`brand_id`, `brand_name`, `brand_code`, `brand_status`) VALUES
(1, 'Nike', 'NIKE', 1),
(2, 'Adidas', 'ADIDAS', 1),
(3, 'Converse', 'CONVERSE', 1),
(4, 'Reebok', 'REEBOK', 1),
(5, 'Vans', 'VANS', 1),
(6, 'Puma', 'PUMA', 1),
(7, 'Jordan', 'JORDAN', 1),
(8, 'Veja', 'VEJA', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` text NOT NULL,
  `category_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0: ẩn\r\n1: hiện',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_status`) VALUES
(1, 'Chạy bộ', 1),
(2, 'Bóng đá', 1),
(3, 'Bóng rổ', 1),
(4, 'Sandals', 1),
(5, 'Thời trang', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `gender`
--

CREATE TABLE IF NOT EXISTS `gender` (
  `gender_id` int(11) NOT NULL AUTO_INCREMENT,
  `gender_name` text NOT NULL,
  `gender_status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `gender`
--

INSERT INTO `gender` (`gender_id`, `gender_name`, `gender_status`) VALUES
(1, 'Nam', 1),
(2, 'Nữ', 1),
(3, 'Tất cả', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order`
--

CREATE TABLE IF NOT EXISTS `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `username_buyer` text NOT NULL,
  `address_buyer` text NOT NULL,
  `phone_buyer` varchar(11) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT current_timestamp(),
  `total_price` int(11) NOT NULL,
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: chờ xử lý\r\n1: đang xử lý\r\n2: hủy\r\n3: thành công',
  `order_note` text NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE IF NOT EXISTS `order_detail` (
  `order_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` text NOT NULL,
  `brand_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `gender_id` int(11) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_price_original` int(11) NOT NULL,
  `discount` float DEFAULT NULL,
  `product_desc` text NOT NULL,
  `product_images` text NOT NULL,
  `product_date` date NOT NULL DEFAULT current_timestamp(),
  `product_status` tinyint(4) NOT NULL DEFAULT 1,
  `product_code` varchar(12) NOT NULL,
  PRIMARY KEY (`product_id`),
  KEY `brand_id` (`brand_id`),
  KEY `category_id` (`category_id`),
  KEY `gender_id` (`gender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_detail`
--

CREATE TABLE IF NOT EXISTS `product_detail` (
  `product_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `product_detail_size` int(11) NOT NULL,
  `product_detail_quantity` int(11) NOT NULL,
  `product_detail_status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`product_detail_id`),
  UNIQUE KEY `product_detail_id` (`product_id`,`product_detail_size`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_user`
--

CREATE TABLE IF NOT EXISTS `role_user` (
  `role_user_id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `role_user_name` varchar(20) NOT NULL COMMENT 'admin/user',
  `role_user_status` tinyint(4) NOT NULL COMMENT '1: activated\r\n0: deactivated',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `role_user`
--

INSERT INTO `role_user` (`role_user_id`, `role_user_name`, `role_user_status`) VALUES
(1, 'admin', 1),
(2, 'user', 1),
(3, 'user', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` text NOT NULL,
  `user_email` varchar(30) NOT NULL,
  `user_status` tinyint(1) NOT NULL DEFAULT 0,
  `role_user_id` tinyint(4) NOT NULL DEFAULT 2 COMMENT '1: admin\r\n2: user\r\n3: user đã bị deactivated',
  `user_password` varchar(100) NOT NULL,
  `user_phone` varchar(15) NOT NULL,
  `user_address` text NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `role_user_id` (`role_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_email`, `user_status`, `role_user_id`, `user_password`, `user_phone`, `user_address`) VALUES
(1, 'Quản trị Viên', 'admin@gmail.com', 1, 1, '$2a$12$5k7sQcCPq5kYEkh/BtQ23uLTQwa.rC8jRz2.4V/aY6P.u998saU9i', '0123456789', '123 Con chim cúc cu');

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `product_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `product_ibfk_3` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`gender_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  ADD CONSTRAINT `product_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_user_id`) REFERENCES `role_user` (`role_user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
