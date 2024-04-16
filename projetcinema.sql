-- phpMyAdmin SQL Dump
-- version 4.9.11
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 15 avr. 2024 à 19:58
-- Version du serveur : 8.0.36
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projetcinema`
--

-- --------------------------------------------------------

--
-- Structure de la table `billets`
--

DROP TABLE IF EXISTS `billets`;
CREATE TABLE IF NOT EXISTS `billets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `seanceId` int DEFAULT NULL,
  `clientId` int DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `categorie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `billets`
--

INSERT INTO `billets` (`id`, `seanceId`, `clientId`, `prix`, `categorie`) VALUES
(4, 13, 24, 5, 'famille'),
(5, 3, 4, 10, 'famile'),
(6, 1, 2, 3, '4');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `motDePasse` varchar(255) NOT NULL,
  `etat` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id`, `nom`, `email`, `type`, `motDePasse`, `etat`) VALUES
(2, 'Hamza', 'hamza.khan@edu.ece.fr', 'membre', '12345', 'enfant');

-- --------------------------------------------------------

--
-- Structure de la table `employes`
--

DROP TABLE IF EXISTS `employes`;
CREATE TABLE IF NOT EXISTS `employes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `motDePasse` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `employes`
--

INSERT INTO `employes` (`id`, `nom`, `position`, `email`, `motDePasse`) VALUES
(2, 'ezaaze', 'aaaaaaaaa', 'aaaa', 'aaaaaa');

-- --------------------------------------------------------

--
-- Structure de la table `films`
--

DROP TABLE IF EXISTS `films`;
CREATE TABLE IF NOT EXISTS `films` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `duree` int DEFAULT NULL,
  `description` text,
  `realisateur` varchar(255) DEFAULT NULL,
    `Affiche` longblob,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `films`
--

INSERT INTO `films` (`id`, `titre`, `genre`, `duree`, `description`, `realisateur`)
VALUES
    (1, 'Inception', 'Science-fiction, Action', 148, 'Un voleur expérimenté est chargé d\implanter une idée dans l\esprit d\un PDG en utilisant l\inception.', 'Christopher Nolan'),
    (2, 'Le Parrain', 'Crime, Drame', 175, 'La saga d\une famille de la mafia italo-américaine dirigée par le patriarche Vito Corleone.', 'Francis Ford Coppola'),
(3, 'Forrest Gump', 'Drame, Romance', 142, 'La vie de Forrest Gump, un homme simple d\esprit, qui se retrouve impliqué dans certains des moments les plus marquants de l\histoire américaine.', 'Robert Zemeckis'),
(4, 'Interstellar', 'Science-fiction, Drame', 169, 'Un groupe d\explorateurs voyage à travers un trou de ver dans l\espace pour rechercher une nouvelle planète habitable pour l\humanité.', 'Christopher Nolan'),
    (5, 'La Liste de Schindler', 'Biographie, Drame, Histoire', 195, 'L\histoire vraie d\Oskar Schindler, un homme d\affaires allemand qui a sauvé plus de mille Juifs pendant l\Holocauste en les employant dans ses usines.', 'Steven Spielberg'),
    (6, 'Pulp Fiction', 'Crime, Drame', 154, 'Une série d\histoires entrelacées mettant en vedette des gangsters, des boxeurs, des revendeurs et des tueurs à gages dans le Los Angeles des années 90.', 'Quentin Tarantino'),
(7, 'Le Seigneur des Anneaux : Le Retour du Roi', 'Fantasy, Aventure, Drame', 201, 'La conclusion épique de la trilogie du Seigneur des Anneaux alors que les forces de la Terre du Milieu se préparent pour une bataille finale contre Sauron.', 'Peter Jackson'),
(8, 'Le Silence des Agneaux', 'Crime, Drame, Thriller', 118, 'Un jeune agent du FBI interroge un psychiatre cannibale emprisonné pour obtenir de l\aide sur la traque d\un tueur en série actif.', 'Jonathan Demme'),
(9, 'Fight Club', 'Drame', 139, 'Un homme anonyme souffrant d\insomnie et son nouvel ami charismatique se lancent dans une aventure de destruction à grande échelle.', 'David Fincher'),
    (10, 'Les Évadés', 'Drame, Crime', 142, 'L\histoire d\amitié entre deux prisonniers condamnés à perpétuité, Andy Dufresne et Ellis "Red" Redding, qui cherchent la liberté et la rédemption.', 'Frank Darabont');

-- --------------------------------------------------------

--
-- Structure de la table `seances`
--

DROP TABLE IF EXISTS `seances`;
CREATE TABLE IF NOT EXISTS `seances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `filmId` int NOT NULL,
  `heure` datetime NOT NULL,
  `salle` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `filmId` (`filmId`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `seances`
--

INSERT INTO `seances` (`id`, `filmId`, `heure`, `salle`)
VALUES
    (1, 1, '2024-04-16 10:00:00', 'Salle IMAX'),
    (2, 2, '2024-04-16 12:00:00', 'Salle Dolby Cinema'),
    (3, 3, '2024-04-16 14:00:00', 'Salle 3D'),
    (4, 4, '2024-04-16 16:00:00', 'Salle VIP'),
    (5, 5, '2024-04-16 18:00:00', 'Salle Standard'),
    (6, 6, '2024-04-16 20:00:00', 'Salle D-Box'),
    (7, 7, '2024-04-17 10:00:00', 'Salle THX'),
    (8, 8, '2024-04-17 12:00:00', 'Salle UltraAVX'),
    (9, 9, '2024-04-17 14:00:00', 'Salle 4DX'),
    (10, 10, '2024-04-17 16:00:00', 'Salle Gold Class'),
    (11, 1, '2024-04-17 18:00:00', 'Salle IMAX'),
    (12, 2, '2024-04-17 20:00:00', 'Salle Dolby Cinema'),
    (13, 3, '2024-04-18 10:00:00', 'Salle 3D'),
    (14, 4, '2024-04-18 12:00:00', 'Salle VIP'),
    (15, 5, '2024-04-18 14:00:00', 'Salle Standard'),
    (16, 6, '2024-04-18 16:00:00', 'Salle D-Box'),
    (17, 7, '2024-04-18 18:00:00', 'Salle THX'),
    (18, 8, '2024-04-18 20:00:00', 'Salle UltraAVX'),
    (19, 9, '2024-04-19 10:00:00', 'Salle 4DX'),
    (20, 10, '2024-04-19 12:00:00', 'Salle Gold Class'),
    (21, 1, '2024-04-19 14:00:00', 'Salle IMAX'),
    (22, 2, '2024-04-19 16:00:00', 'Salle Dolby Cinema'),
    (23, 3, '2024-04-19 18:00:00', 'Salle 3D'),
    (24, 4, '2024-04-19 20:00:00', 'Salle VIP'),
    (25, 5, '2024-04-20 10:00:00', 'Salle Standard'),
    (26, 6, '2024-04-20 12:00:00', 'Salle D-Box'),
    (27, 7, '2024-04-20 14:00:00', 'Salle THX'),
    (28, 8, '2024-04-20 16:00:00', 'Salle UltraAVX'),
    (29, 9, '2024-04-20 18:00:00', 'Salle 4DX'),
    (30, 10, '2024-04-20 20:00:00', 'Salle Gold Class'),
    (31, 1, '2024-04-21 10:00:00', 'Salle IMAX'),
    (32, 2, '2024-04-21 12:00:00', 'Salle Dolby Cinema'),
    (33, 3, '2024-04-21 14:00:00', 'Salle 3D'),
    (34, 4, '2024-04-21 16:00:00', 'Salle VIP'),
    (35, 5, '2024-04-21 18:00:00', 'Salle Standard'),
    (36, 6, '2024-04-21 20:00:00', 'Salle D-Box'),
    (37, 7, '2024-04-22 10:00:00', 'Salle THX'),
    (38, 8, '2024-04-22 12:00:00', 'Salle UltraAVX'),
    (39, 9, '2024-04-22 14:00:00', 'Salle 4DX'),
    (40, 10, '2024-04-22 16:00:00', 'Salle Gold Class'),
    (41, 1, '2024-04-22 18:00:00', 'Salle IMAX'),
    (42, 2, '2024-04-22 20:00:00', 'Salle Dolby Cinema'),
    (43, 3, '2024-04-23 10:00:00', 'Salle 3D'),
    (44, 4, '2024-04-23 12:00:00', 'Salle VIP'),
    (45, 5, '2024-04-23 14:00:00', 'Salle Standard'),
    (46, 6, '2024-04-23 16:00:00', 'Salle D-Box'),
    (47, 7, '2024-04-23 18:00:00', 'Salle THX'),
    (48, 8, '2024-04-23 20:00:00', 'Salle UltraAVX'),
    (49, 9, '2024-04-24 10:00:00', 'Salle 4DX'),
    (50, 10, '2024-04-24 12:00:00', 'Salle Gold Class');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
