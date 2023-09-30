# Football Scoreboard

A Java project for managing football matches, scoreboards, and live matches.

## Table of Contents

- [Introduction](#introduction)
- [Classes](#classes)
  - [Player](#player)
  - [Team](#team)
  - [FootballMatch](#footballmatch)
  - [LiveMatchManager](#livematchmanager)
- [Usage](#usage)
- [CodeOptimization](#codeoptimization)
- [ExportingLibrary](#exportinglibrary)

## Introduction

The Football Scoreboard project provides Java classes for managing football matches, teams, and players. It also includes a `LiveMatchManager` class for handling live matches. This README provides an overview of the key classes in the project.

## Classes

### Player

The `Player` class represents a football player. It includes properties such as name, number, and score. Players can increase their score.

### Team

The `Team` class represents a football team. It contains a list of players, a score, a goalkeeper, and a skipper (team captain). Teams can add and remove players, increase their score, and more.

### FootballMatch

The `FootballMatch` class represents a football match. It includes two teams, a match title, start time, match status, and a referee. Matches can have their status updated and referees assigned. Teams and players can score goals during the match.

### LiveMatchManager

The `LiveMatchManager` class is responsible for managing live football matches. It provides methods for starting, pausing, resuming, and ending matches. It also allows real-time score updates and provides match status information.
### Usage
The FootballMatchClient class serves as an example of how to use the MatchManager class to manage football matches. It demonstrates the following actions:

- Creating teams with players.
- Creating football matches.
- Adding matches to the MatchManager.
- Scoring goals.
- Getting match details.
- Getting player rankings.
- Getting player details.
- Getting a list of all players.
- Removing matches.

### CodeOptimization
The code has been optimized to reduce redundancy. Two methods have been introduced for better code organization:

### ExportingLibrary
- Clone this repository to your local machine:

   ```shell
   https://github.com/pmishratech/football-scoreboard
   ```
- mvn clean install => Will create artifact and deploy the jar on your local or remote repo(if configured)

