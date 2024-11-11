CREATE DATABASE  IF NOT EXISTS `svil_awesomepizza` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `svil_awesomepizza`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: svil_awesomepizza
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `as_pizzeorders`
--

DROP TABLE IF EXISTS `as_pizzeorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `as_pizzeorders` (
  `id_pizza_orders` int NOT NULL AUTO_INCREMENT,
  `id_pizza` int DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  PRIMARY KEY (`id_pizza_orders`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `as_pizzeorders`
--

LOCK TABLES `as_pizzeorders` WRITE;
/*!40000 ALTER TABLE `as_pizzeorders` DISABLE KEYS */;
/*!40000 ALTER TABLE `as_pizzeorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fu_orders`
--

DROP TABLE IF EXISTS `fu_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fu_orders` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `id_status` int DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fu_orders`
--

LOCK TABLES `fu_orders` WRITE;
/*!40000 ALTER TABLE `fu_orders` DISABLE KEYS */;
INSERT INTO `fu_orders` VALUES (2,3,'2DE19C1','sugo extra','2024-11-10 14:00:00','string'),(3,2,'1111','test','2024-11-10 14:00:00',NULL);
/*!40000 ALTER TABLE `fu_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fu_pizza`
--

DROP TABLE IF EXISTS `fu_pizza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fu_pizza` (
  `id_pizza` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_pizza`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fu_pizza`
--

LOCK TABLES `fu_pizza` WRITE;
/*!40000 ALTER TABLE `fu_pizza` DISABLE KEYS */;
INSERT INTO `fu_pizza` VALUES (1,'Margherita','Pomodoro mozzarella',NULL),(2,'Boscaiola','Funghi salsiccia mozzarella',NULL),(3,'Patate','patate mozzarella',NULL);
/*!40000 ALTER TABLE `fu_pizza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ty_order_status`
--

DROP TABLE IF EXISTS `ty_order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ty_order_status` (
  `id_order_status` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_order_status`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ty_order_status`
--

LOCK TABLES `ty_order_status` WRITE;
/*!40000 ALTER TABLE `ty_order_status` DISABLE KEYS */;
INSERT INTO `ty_order_status` VALUES (1,'inviato','test'),(2,'Preso in carico',NULL),(3,'Spedito',NULL),(4,'Consegnato',NULL);
/*!40000 ALTER TABLE `ty_order_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-11 10:34:10
