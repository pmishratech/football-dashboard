package com.scoreboard.football;

import com.scoreboard.football.common.MatchStatus;
import com.scoreboard.football.modal.Player;
import com.scoreboard.football.modal.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FootballMatch {
    private String matchId;
    private String matchTitle;
    private Team teamOne;
    private Team teamTwo;
    private LocalDateTime startTime;
    private Player referee;
    private MatchStatus matchStatus;

    private FootballMatch() {
        // Private constructor to prevent direct instantiation
    }

    public static class Builder {
        private Team teamOne;
        private Team teamTwo;
        private String matchTitle;
        private Player referee;

        public Builder teamOne(Team teamOne) {
            this.teamOne = teamOne;
            return this;
        }

        public Builder teamTwo(Team teamTwo) {
            this.teamTwo = teamTwo;
            return this;
        }

        public Builder matchTitle(String matchTitle) {
            this.matchTitle = matchTitle;
            return this;
        }

        public Builder referee(Player referee) {
            this.referee = referee;
            return this;
        }

        public FootballMatch build() {
            FootballMatch footballMatch = new FootballMatch();
            if (teamOne == null || teamTwo == null || matchTitle == null || referee == null) {
                throw new IllegalArgumentException("Parameters cannot be null");
            }
            footballMatch.matchId = UUID.randomUUID().toString();
            footballMatch.matchTitle = matchTitle;
            footballMatch.teamOne = teamOne;
            footballMatch.teamTwo = teamTwo;
            footballMatch.startTime = LocalDateTime.now();
            footballMatch.referee = referee;
            footballMatch.matchStatus = MatchStatus.SCHEDULED;
            return footballMatch;
        }
    }

    public String getMatchId() {
        return matchId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        if (matchStatus != MatchStatus.IN_PROGRESS) {
            this.matchTitle = matchTitle;
        } else {
            throw new IllegalStateException("Cannot update match title during the match");
        }
    }

    public Team getTeamOne() {
        return teamOne;
    }

    public boolean setTeamOne(Team teamOne) {
        if(this.matchStatus!=MatchStatus.IN_PROGRESS){
            this.teamOne = teamOne;
            return true;
        }
        return false;
    }

    public Team getTeamTwo() {
        return teamTwo;
    }

    public boolean setTeamTwo(Team teamTwo) {
        if(this.matchStatus!=MatchStatus.IN_PROGRESS){
            this.teamTwo = teamTwo;
            return true;
        }
        return false;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Player getReferee() {
        return referee;
    }

    public boolean assignReferee(Player referee) {
        if (this.referee == null) {
            this.referee = referee;
            return true;
        }
        return false; // Referee is already assigned
    }

    public List<String> getAllPlayerNames() {
        List<String> playerNames = new ArrayList<> ();
        playerNames.addAll(teamOne.getPlayers ().stream ().map(player->player.getName ()).collect(Collectors.toList()));
        playerNames.addAll(teamTwo.getPlayers ().stream ().map(player->player.getName ()).collect(Collectors.toList()));
        return playerNames;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<> ();
        players.addAll(teamOne.getPlayers ());
        players.addAll(teamTwo.getPlayers ());
        return players;
    }

    @Override
    public String toString() {
        return "FootballMatch{" +
                "matchId='" + matchId + '\'' +
                ", matchTitle='" + matchTitle + '\'' +
                ", teamOne=" + teamOne +
                ", teamTwo=" + teamTwo +
                ", startTime=" + startTime +
                ", matchStatus=" + matchStatus +
                ", referee=" + referee +
                '}';
    }
}
