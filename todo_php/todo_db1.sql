-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 04 sep. 2021 à 22:49
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `todo_db1`
--

-- --------------------------------------------------------

--
-- Structure de la table `accounts_table`
--

CREATE TABLE `accounts_table` (
  `account_id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `accounts_table`
--

INSERT INTO `accounts_table` (`account_id`, `email`, `password`) VALUES
(1, 'diam@gmail.com', 'diam'),
(2, 'ama@gmail.com', 'ama'),
(3, 'ama1@gmail.com', 'ama1'),
(4, 'diam1@gmail.com', 'diam'),
(5, 'diam1gmail.com', 'diam'),
(6, 'diam11@gmail.com', 'diam'),
(7, 'diam21@gmail.com', 'diam2'),
(8, 'diam213@gmail.com', 'diam2'),
(9, 'rdhd@gmail.com', 'sfsdvff'),
(10, 'dia@gmail.com', 'hbcff'),
(11, 'amadou@gmail.com', 'diallo'),
(12, 'diamil@gmail.com', 'diamil'),
(13, 'yacouba@gmail.com', 'yacoub'),
(14, 'benjamin@gmail.com', 'Binjamin'),
(15, 'test@gmail.com', 'test'),
(16, 'test1@gmail.com', 'test'),
(17, 'test2@gmail.com', 'test2'),
(18, 'test3@gmail.com', 'test3'),
(19, 'test4@gmail.com', 'test3'),
(20, 'test66@gmail.com', 'test6'),
(21, 'test7@gmail.com', 'test6'),
(22, 'teat8@gmail.com', 'test8'),
(23, 'test9@gmail.com', 'test3'),
(24, 'final@final.com', 'final'),
(25, 'finalend@gmail.com', 'final');

-- --------------------------------------------------------

--
-- Structure de la table `todo_tables`
--

CREATE TABLE `todo_tables` (
  `todo_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `todo` varchar(500) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `todo_tables`
--

INSERT INTO `todo_tables` (`todo_id`, `account_id`, `date`, `todo`, `done`) VALUES
(1, 1, '0000-00-00', 'Sport', 0),
(2, 1, '0000-00-00', 'Sport', 0),
(5, 2, '0000-00-00', 'Sport', 0),
(6, 2, '0000-00-00', 'Sport', 0),
(7, 2, '0000-00-00', 'Sport', 0),
(8, 2, '0000-00-00', 'Sport', 0),
(9, 3, '2021-09-05', 'oh', 0),
(10, 2, '2021-09-02', 'bonjour', 0),
(11, 2, '2021-09-02', 'bonjour', 0),
(12, 2, '2021-09-02', 'bonjour', 0),
(13, 2, '2021-09-02', 'bonjour', 0),
(14, 2, '2021-09-02', 'bonjour', 0),
(15, 2, '2021-09-02', 'bonjour', 0),
(16, 2, '2021-04-03', 'test', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `accounts_table`
--
ALTER TABLE `accounts_table`
  ADD PRIMARY KEY (`account_id`);

--
-- Index pour la table `todo_tables`
--
ALTER TABLE `todo_tables`
  ADD PRIMARY KEY (`todo_id`),
  ADD KEY `fk_account_id` (`account_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `accounts_table`
--
ALTER TABLE `accounts_table`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `todo_tables`
--
ALTER TABLE `todo_tables`
  MODIFY `todo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `todo_tables`
--
ALTER TABLE `todo_tables`
  ADD CONSTRAINT `fk_account_id` FOREIGN KEY (`account_id`) REFERENCES `accounts_table` (`account_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
