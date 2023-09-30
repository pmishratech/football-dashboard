package com.scoreboard.football.service;

import com.scoreboard.football.FootballMatch;
import com.scoreboard.football.common.MatchValidator;
import com.scoreboard.football.modal.Team;
import com.scoreboard.football.modal.Player;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchManager {
    private Map<String, FootballMatch> matches;

    public MatchManager() {
        this.matches = new HashMap<>();
    }

    public void addMatch(String matchId, FootballMatch match) {
        MatchValidator.validateMatchId(matchId, matches);
        MatchValidator.validateTeamSize(match.getTeamOne());
        MatchValidator.validateTeamSize(match.getTeamTwo());
        matches.put(matchId, match);
    }

    public void updateMatch(String matchId, FootballMatch updatedMatch) {
        MatchValidator.validateMatchExists(matchId, matches);
        matches.put(matchId, updatedMatch);
    }

    public void removeMatch(String matchId) {
        MatchValidator.validateMatchExists(matchId, matches);
        MatchValidator.validateMatchNotInProgress(matchId, matches);
        matches.remove(matchId);
    }

    public Map<String, FootballMatch> getAllMatches() {
        MatchValidator.validateMatchesNotEmpty(matches);
        return matches;
    }

    public FootballMatch getMatchDetails(String matchId) {
        MatchValidator.validateMatchExists(matchId, matches);
        return matches.get(matchId);
    }

    public void scoreGoal(String matchId, String playerName, Team team) {
        MatchValidator.validateMatchExists(matchId, matches);
        FootballMatch match = matches.get(matchId);
        MatchValidator.validateTeamExistsInMatch(match, team);
        int points = 1;
        team.increaseScore(points, playerName);
    }

    public List<Player> getPlayersRanking(String matchId) {
        MatchValidator.validateMatchExists(matchId, matches);
        Comparator<Player> byScoreComparator = Comparator.comparing(Player::getScore).reversed();
        return Stream.concat(
                        matches.get(matchId).getTeamOne().getPlayers().stream(),
                        matches.get(matchId).getTeamTwo().getPlayers().stream()
                ).sorted(byScoreComparator)
                .collect(Collectors.toList());
    }

    public Player getPlayerDetails(String playerName) {
        MatchValidator.validateMatchesNotEmpty(matches);
        for (FootballMatch match : matches.values()) {
            for (Player player : match.getAllPlayers()) {
                if (player.getName().equals(playerName)) {
                    return player;
                }
            }
        }
        throw new IllegalArgumentException("Player not found: " + playerName);
    }

    public List<String> getAllPlayers() {
        MatchValidator.validateMatchesNotEmpty(matches);
        return matches.values().stream()
                .flatMap(match -> match.getAllPlayerNames().stream())
                .collect(Collectors.toList());
    }

    public LocalDateTime getMatchStartTime(String matchId) {
        MatchValidator.validateMatchExists(matchId, matches);
        return matches.get(matchId).getStartTime();
    }
}
