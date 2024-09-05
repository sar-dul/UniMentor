-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 30, 2024 at 05:52 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uni_mentor`
--

-- --------------------------------------------------------

--
-- Table structure for table `mentor`
--

CREATE TABLE `mentor` (
  `mentor_username` varchar(25) NOT NULL,
  `mentor_name` varchar(35) NOT NULL,
  `mentor_phone` varchar(15) NOT NULL,
  `mentor_email` varchar(50) NOT NULL,
  `mentor_password` varchar(200) NOT NULL,
  `mentor_photo` varchar(200) NOT NULL,
  `university_name` varchar(25) NOT NULL,
  `university_country` varchar(20) NOT NULL,
  `major` varchar(25) NOT NULL,
  `tuition_fee` int(10) NOT NULL,
  `scholarship` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mentor`
--

INSERT INTO `mentor` (`mentor_username`, `mentor_name`, `mentor_phone`, `mentor_email`, `mentor_password`, `mentor_photo`, `university_name`, `university_country`, `major`, `tuition_fee`, `scholarship`) VALUES
('Hridaya_12', 'Hridaya Giri Thapa', '+913-8888888888', 'hir@hotmail.com', 'SvdnJ936W7Y1jtTDx1YmOvL8j7jD8T4pi++eDMzGsTVJvSkS84IswgX0bCaLfsF36oFeu6dq', 'default-profile.png', 'Stanford University', 'USA', 'Hotel Management', 1000, '80%'),
('Ronak_99', 'Roanak', '+900-1111222232', 'd@yaoo.com', 'zmalwmYetRqfQPH1u+JONCu01nE8I3Vc3weKpeIoWI1mnFrfY/MtfZJBfExFQy15ZFlM+w==', 'img-4.jpg', 'Yale University', 'USA', 'dd', 2, '123'),
('Sardul_11', 'Sardul Ojha', '+977-9742855801', 'sardul@gmail.com', 'lskXhYMsTWkU2UqK0EN4aE8qqLGaB1DwabUYMcZA5v0XZO455wuXsSUxS7tnizKZ9fs5Ojo=', 'img-2.jpg', 'Harvard University', 'USA', 'Computer Science', 0, '100%'),
('Sek_Md_15', 'Sek Md Abid', '+913-7777712345', 'sek@gmail.com', 'GswrmN5zDMfOh7PrZOtx4BpnumAh7ySgug9UkFDHoTbAFrX4wnglI62ZBnmZswZbfipWQ74=', 'img-3.jpg', 'MIT', 'USA', 'MBBS', 10000, '10%');

-- --------------------------------------------------------

--
-- Table structure for table `seeker`
--

CREATE TABLE `seeker` (
  `seeker_username` varchar(25) NOT NULL,
  `seeker_name` varchar(35) NOT NULL,
  `seeker_phone` varchar(15) NOT NULL,
  `seeker_email` varchar(50) NOT NULL,
  `seeker_password` varchar(200) NOT NULL,
  `seeker_photo` varchar(200) NOT NULL,
  `seeker_location` varchar(25) NOT NULL,
  `education_level` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seeker`
--

INSERT INTO `seeker` (`seeker_username`, `seeker_name`, `seeker_phone`, `seeker_email`, `seeker_password`, `seeker_photo`, `seeker_location`, `education_level`) VALUES
('Ness_16', 'Ness Shrestha', '+981-1111118890', 'ness@gmail.com', 'I3nfevWsIXDr6/Ze2ZKwPD3FCMaMCr3h5gMrtkAYsxGhtxVuFyFZsriFCS8P53h8uC1R', 'img-1.jpg', 'Bhaktapur', '+2'),
('Prithivi_10', 'Prithivi', '+977-8888887777', 'prithivi@gmail.com', 'hzqmA3TfiCHkCvBw0P7rIG7d2ZJ3U+lHKg86iM3PxmK+YfPp0ro81A8+jXI7UWgypGmzkYRPvQ==', 'default-profile.png', 'Kathmandu', 'Masters'),
('Ronak_13', 'Ronak Shrestha', '+977-8888888831', 'ronak@yahoo.com', 'ANwfR6Tc3FMqGwvBh9VQZ1DhX3dZuG1WHgClF77QF04D7gu1vUNtl5e6ckjqxytC4rwzVw==', 'img-4.jpg', 'Kathmandu, Nepal', 'Bachelors'),
('Suyash_14', 'Suyash Dhakal', '+981-1111111112', 'suyash@haha.com', 'agTdBt32HxvwirZY44UxRT8hpDNx0c/qoyz3Fc4ejpnAkdy/FTvqtpqItLDci8/S911yUFM=', 'default-profile.png', 'Bejing, China', 'Masters');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL,
  `service_title` varchar(35) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int(11) NOT NULL,
  `creation_date` date NOT NULL,
  `rating` decimal(10,1) NOT NULL,
  `service_users` int(15) NOT NULL,
  `status` varchar(10) NOT NULL,
  `mentor_username` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`service_id`, `service_title`, `description`, `price`, `creation_date`, `rating`, `service_users`, `status`, `mentor_username`) VALUES
(72, 'Interview Preparation Package', 'Nail your university interviews with my comprehensive Interview Preparation Package. Gain confidence, learn effective strategies, and practice with mock interviews tailored to your chosen institutions. Let\'s ensure your interview performance shines!', 150, '2024-05-10', 3.0, 289, 'Active', 'Hridaya_12'),
(73, 'Full Application', 'Get expert guidance for your university application journey with my Full Application Process Guide. From personal statements to interviews, I\'ll help you every step of the way. Let\'s make your application a success reach out today!', 499, '2024-05-10', 4.3, 19, 'Active', 'Sardul_11'),
(76, 'Recommendation Letter Assistance', 'Unlock the power of compelling recommendation letters with my tailored assistance. I\'ll offer feedback to ensure they showcase your achievements effectively. Let\'s secure glowing endorsements for your applications!', 99, '2024-05-10', 0.0, 0, 'Active', 'Hridaya_12'),
(77, 'a', 'a', 3, '2024-05-10', 0.0, 0, 'Active', 'Ronak_99'),
(80, 'Full Application', 'Get expert guidance for your university application journey with my Full Application Process Guide. From personal statements to interviews, I\'ll help you every step of the way. Let\'s make your application a success reach out today!', 100, '2024-05-16', 0.0, 0, 'Active', 'Sardul_11');

-- --------------------------------------------------------

--
-- Table structure for table `service_details`
--

CREATE TABLE `service_details` (
  `service_id` int(11) NOT NULL,
  `seeker_username` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mentor`
--
ALTER TABLE `mentor`
  ADD PRIMARY KEY (`mentor_username`);

--
-- Indexes for table `seeker`
--
ALTER TABLE `seeker`
  ADD PRIMARY KEY (`seeker_username`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`service_id`),
  ADD KEY `service_ibfk_1` (`mentor_username`);

--
-- Indexes for table `service_details`
--
ALTER TABLE `service_details`
  ADD KEY `seeker_username` (`seeker_username`),
  ADD KEY `service_id` (`service_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `service_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `service_ibfk_1` FOREIGN KEY (`mentor_username`) REFERENCES `mentor` (`mentor_username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `service_details`
--
ALTER TABLE `service_details`
  ADD CONSTRAINT `service_details_ibfk_1` FOREIGN KEY (`seeker_username`) REFERENCES `seeker` (`seeker_username`),
  ADD CONSTRAINT `service_details_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
