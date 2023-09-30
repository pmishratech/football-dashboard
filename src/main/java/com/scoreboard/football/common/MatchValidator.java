package com.scoreboard.football.common;

import com.scoreboard.football.FootballMatch;
import com.scoreboard.football.modal.Team;

import java.util.List;
import java.util.Map;

public class MatchValidator {
    public static void validateMatchId(String matchId, Map<String, FootballMatch> matches) {
        if (!matches.isEmpty() && matches.containsKey(matchId)) {
            throw new IllegalArgumentException("Match id duplicate: " + matchId);
        }
    }

    public static void validateTeamSize(Team team) {
        if (team.getPlayers().size() != 11) {
            throw new IllegalArgumentException("Match can not be added as player count in a team is not valid");
        }
    }

    public static void validateMatchExists(String matchId, Map<String, FootballMatch> matches) {
        if (!matches.containsKey(matchId)) {
            throw new IllegalArgumentException("Match not found: " + matchId);
        }
    }

    public static void validateMatchNotInProgress(String matchId, Map<String, FootballMatch> matches) {
        if (matches.get(matchId).getMatchStatus() == MatchStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Match can not be removed as it's live: " + matchId);
        }
    }

    public static void validateTeamExistsInMatch(FootballMatch match, Team team) {
        if (!match.getTeamOne().equals(team) && !match.getTeamTwo().equals(team)) {
            throw new IllegalArgumentException("Team does not exist in the match for scoring: " + team.getName());
        }
    }

    public static void validateMatchesNotEmpty(Map<String, FootballMatch> matches) {
        if (matches.isEmpty()) {
            throw new IllegalArgumentException("No matches found");
        }
    }
}
