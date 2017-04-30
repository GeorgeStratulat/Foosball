/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 100116
Source Host           : localhost:3306
Source Database       : dat16j

Target Server Type    : MYSQL
Target Server Version : 100116
File Encoding         : 65001

Date: 2017-04-30 15:42:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tournaments
-- ----------------------------
DROP TABLE IF EXISTS `tournaments`;
CREATE TABLE `tournaments` (
  `tournament_id` int(55) NOT NULL AUTO_INCREMENT,
  `tournament_name` varchar(255) NOT NULL,
  `tournament_prize` varchar(255) NOT NULL,
  `tournament_dateToStart` varchar(255) NOT NULL,
  `tournament_dateToFinish` varchar(255) NOT NULL,
  PRIMARY KEY (`tournament_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for tournament_games
-- ----------------------------
DROP TABLE IF EXISTS `tournament_games`;
CREATE TABLE `tournament_games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_team1` int(11) DEFAULT NULL,
  `game_team2` int(11) DEFAULT NULL,
  `game_team1_score` int(11) DEFAULT '0',
  `game_team2_score` int(255) DEFAULT '0',
  `tournament_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for tournament_players
-- ----------------------------
DROP TABLE IF EXISTS `tournament_players`;
CREATE TABLE `tournament_players` (
  `player_id` int(10) NOT NULL AUTO_INCREMENT,
  `player_name` varchar(255) NOT NULL DEFAULT 'Default Player Name',
  `player_team` varchar(255) NOT NULL DEFAULT 'Default Player Name',
  `player_birthday` varchar(255) NOT NULL DEFAULT '06.06.1666',
  `player_email` varchar(255) NOT NULL DEFAULT 'boss@boss.boss',
  `player_goals` int(55) NOT NULL DEFAULT '0',
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for tournament_teams
-- ----------------------------
DROP TABLE IF EXISTS `tournament_teams`;
CREATE TABLE `tournament_teams` (
  `team_id` int(10) NOT NULL AUTO_INCREMENT,
  `tournament_id` int(10) NOT NULL,
  `team_name` varchar(255) NOT NULL DEFAULT 'Default Team Name',
  `team_goals` int(10) NOT NULL DEFAULT '0',
  `team_winnings` int(10) NOT NULL DEFAULT '0',
  `team_played_games` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
