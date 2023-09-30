package com.scoreboard.football.service;

import com.scoreboard.football.FootballMatch;
import com.scoreboard.football.common.MatchStatus;
import com.scoreboard.football.modal.Player;
import com.scoreboard.football.modal.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchManagerTest {
    private MatchManager matchManager;

    @BeforeEach
    void setUp() {
        matchManager = new MatchManager();
    }

    @Test
    void testAddMatchSuccess() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "Player");
        matchManager.addMatch(match.getMatchId(), match);
        assertNotNull(match);
        assertEquals(MatchStatus.SCHEDULED, match.getMatchStatus());
        assertEquals(22, match.getTeamTwo ().getPlayers ().size ()+match.getTeamOne ().getPlayers ().size ());
    }

    @Test
    void testAddMatchFailedAsDuplicate() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "Player");
        matchManager.addMatch(match.getMatchId(), match);
        assertThrows(IllegalArgumentException.class, () -> matchManager.addMatch(match.getMatchId(), match));
    }

    @Test
    void testAddMatchFailingWithInvalidTeamSize() {
        Team teamA = createTeamWithPlayers("TeamA", 11, "Player");
        Team teamB = new Team("TeamB");
        Player referee = new Player("MatchReferee", 777);
        FootballMatch footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee)
                .build();
        assertThrows(IllegalArgumentException.class, () -> matchManager.addMatch(footballMatch.getMatchId(), footballMatch));
    }

    @Test
    void testUpdateMatch() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch("Match3", match);
        match.setMatchStatus(MatchStatus.IN_PROGRESS);
        matchManager.updateMatch("Match3", match);
        FootballMatch updatedMatch = matchManager.getMatchDetails("Match3");
        assertNotNull(updatedMatch);
        assertEquals(MatchStatus.IN_PROGRESS, updatedMatch.getMatchStatus());
    }

    @Test
    void testRemoveMatchSuccess() {
        FootballMatch match1 = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        FootballMatch match2 = createValidFootballMatch("TeamD", "TeamE", "PlayerDE");
        matchManager.addMatch(match1.getMatchId(), match1);
        matchManager.addMatch(match2.getMatchId(), match2);
        matchManager.removeMatch(match1.getMatchId());
        assertEquals(match2, matchManager.getMatchDetails(match2.getMatchId()));
        assertEquals(1, matchManager.getAllMatches().size());
    }

    @Test
    void testRemoveMatchFailureMatchStarted() {
        FootballMatch match1 = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        FootballMatch match2 = createValidFootballMatch("TeamD", "TeamE", "PlayerDE");
        matchManager.addMatch(match1.getMatchId(), match1);
        matchManager.addMatch(match2.getMatchId(), match2);
        match2.setMatchStatus(MatchStatus.IN_PROGRESS);
        assertThrows(IllegalArgumentException.class, () -> matchManager.removeMatch(match2.getMatchId()));
        assertEquals(2, matchManager.getAllMatches().size());
    }

    @Test
    void testRemoveMatchFailureMatchNotFound() {
        FootballMatch match1 = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        FootballMatch match2 = createValidFootballMatch("TeamD", "TeamE", "PlayerDE");
        matchManager.addMatch(match1.getMatchId(), match1);
        matchManager.addMatch(match2.getMatchId(), match2);
        assertThrows(IllegalArgumentException.class, () -> matchManager.removeMatch("Match3"));
        assertEquals(2, matchManager.getAllMatches().size());
    }

    @Test
    void testGetAllMatches() {
        FootballMatch match1 = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        FootballMatch match2 = createValidFootballMatch("TeamD", "TeamE", "PlayerDE");
        FootballMatch match3 = createValidFootballMatch("Team5", "Team9", "PlayerAKB");
        FootballMatch match4 = createValidFootballMatch("Team6", "Team2", "PlayerDPE");
        matchManager.addMatch(match1.getMatchId(), match1);
        matchManager.addMatch(match2.getMatchId(), match2);
        matchManager.addMatch(match3.getMatchId(), match3);
        matchManager.addMatch(match4.getMatchId(), match4);
        assertEquals(4, matchManager.getAllMatches().size());
        assertTrue(matchManager.getAllMatches().keySet().contains(match1.getMatchId()));
        assertTrue(matchManager.getAllMatches().keySet().contains(match2.getMatchId()));
        assertTrue(matchManager.getAllMatches().keySet().contains(match3.getMatchId()));
        assertTrue(matchManager.getAllMatches().keySet().contains(match4.getMatchId()));
    }

    @Test
    void testGetAllMatchesFailedNoMatchExist() {
        assertThrows(IllegalArgumentException.class, () -> matchManager.getAllMatches());
    }

    @Test
    void testGetMatchDetailsSuccess() {
        FootballMatch match1 = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch(match1.getMatchId(), match1);
        assertNotNull(matchManager.getMatchDetails(match1.getMatchId()));
    }

    @Test
    void testGetMatchDetailsFailedNotFound() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch(match.getMatchId(), match);
        assertThrows(IllegalArgumentException.class, () -> matchManager.getMatchDetails("xyz"));
    }

    @Test
    void testScoreGoalTeamSuccess() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch(match.getMatchId(), match);
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB2", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());
        assertEquals(4, matchManager.getMatchDetails(match.getMatchId()).getTeamOne().getScore());
    }

    @Test
    void testScoreGoalFailMatchNotExist() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        assertThrows(IllegalArgumentException.class, () ->
                matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne()));
    }

    @Test
    void testScoreGoalFailTeamNotExist() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch(match.getMatchId(), match);
        FootballMatch matchNotAdded = createValidFootballMatch("TeamY", "TeamZ", "PlayerAB");
        assertThrows(IllegalArgumentException.class, () ->
                matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", matchNotAdded.getTeamOne()));
    }

    @Test
    void testScoreGoalSuccessForSpecificPlayer() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "PlayerAB");
        matchManager.addMatch(match.getMatchId(), match);
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB2", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());
        matchManager.scoreGoal(match.getMatchId(), "PlayerAB1", match.getTeamOne());

        assertEquals(13, matchManager.getMatchDetails(match.getMatchId())
                .getTeamOne().getPlayers().stream()
                .filter (player->player.getName ().equals ("PlayerAB1")).findAny ().get ().getScore());
    }

    @Test
    void testGetPlayersRanking() {
        FootballMatch match = createValidFootballMatch("Team1", "Team2", "PlayerAB");
        matchManager.addMatch(match.getMatchId(), match);
        List<Player> rankedPlayers = matchManager.getPlayersRanking(match.getMatchId());
        assertNotNull(rankedPlayers);
        assertEquals(22, rankedPlayers.size());
        assertEquals("PlayerAB1", rankedPlayers.get(0).getName());
        assertEquals("PlayerAB3", rankedPlayers.get(1).getName());
        assertEquals("PlayerAB2", rankedPlayers.get(2).getName());
    }

    @Test
    void testGetPlayerDetails() {
        FootballMatch match = createValidFootballMatch("Team1", "Team2", "Player");
        matchManager.addMatch(match.getMatchId(), match);
        Player foundPlayer = matchManager.getPlayerDetails("Player2");
        assertNotNull(foundPlayer);
        assertEquals("Player2", foundPlayer.getName());
    }

    @Test
    void testGetPlayerDetailsPlayerNotFound() {
        FootballMatch match = createValidFootballMatch("Team1", "Team2", "Player");
        matchManager.addMatch(match.getMatchId(), match);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchManager.getPlayerDetails("NonExistingPlayer");
        });
        assertEquals("Player not found: NonExistingPlayer", exception.getMessage());
    }

    @Test
    void testGetAllPlayers() {
        FootballMatch match = createValidFootballMatch("Team1", "Team2", "Player");
        matchManager.addMatch(match.getMatchId(), match);
        List<String> allPlayers = matchManager.getAllPlayers();
        assertNotNull(allPlayers);
        assertTrue(allPlayers.contains("Player1"));
        assertTrue(allPlayers.contains("Player2"));
        assertTrue(allPlayers.contains("Player3"));
    }

    @Test
    void testGetAllPlayersNoMatchesFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            matchManager.getAllPlayers();
        });
        assertEquals("No matches found", exception.getMessage());
    }

    private FootballMatch createValidFootballMatch(String teamOne, String teamTwo, String testPlayerName) {
        Team teamA = createTeamWithPlayers(teamOne, 11, testPlayerName);
        Team teamB = createTeamWithPlayers(teamTwo, 11, testPlayerName+"X");
        Player referee = new Player("MatchReferee", 777);
        FootballMatch match = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee)
                .build();
        return match;
    }

    private Team createTeamWithPlayers(String teamName, int numberOfPlayers, String playerNamePrefix) {
        Team team = new Team(teamName);
        for (int i = 1; i <= numberOfPlayers; i++) {
            Player player = new Player(playerNamePrefix + i, i);
            team.addPlayer(player);
            if (player.getName ().equals ("PlayerAB" + 1)) {
                player.increaseScore (10);
            } else if (player.getName ().equals ("PlayerAB" + 3)) {
                player.increaseScore (8);
            } else if (player.getName ().equals ("PlayerAB" + 2)) {
                player.increaseScore (5);
            }
        }
        return team;
    }

    @Test
    void testGetMatchStartTime() {
        FootballMatch match = createValidFootballMatch("TeamA", "TeamB", "Player");
        matchManager.addMatch(match.getMatchId (), match);

        LocalDateTime retrievedStartTime = matchManager.getMatchStartTime(match.getMatchId ());
        assertNotNull(retrievedStartTime);
    }
}