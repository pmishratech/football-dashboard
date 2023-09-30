package com.scoreboard.football;

import com.scoreboard.football.common.MatchStatus;
import com.scoreboard.football.modal.Player;
import com.scoreboard.football.modal.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FootballMatchTest {
    private FootballMatch footballMatch;

    @BeforeEach
    void setUp() {
        footballMatch = createValidFootballMatch();
    }

    @Test
    void testGetMatchTitle() {
        assertEquals("Match Football", footballMatch.getMatchTitle());
    }

    @Test
    void testSetMatchTitleSuccess() {
        footballMatch.setMatchTitle("Updated Title");
        assertEquals("Updated Title", footballMatch.getMatchTitle());
    }

    @Test
    void testSetMatchTitleFailureMatchInProgress() {
        footballMatch.setMatchStatus(MatchStatus.IN_PROGRESS);
        assertThrows(IllegalStateException.class, () -> footballMatch.setMatchTitle("Updated Title"));
    }

    private FootballMatch createValidFootballMatch() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        Player referee = new Player("Robin Referee", 777);
        FootballMatch footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee)
                .build();
        return footballMatch;
    }

    @Test
    void testTeamsAdded() {
        assertEquals("TeamA", footballMatch.getTeamOne().getName());
        assertEquals("TeamB", footballMatch.getTeamTwo().getName());
    }

    @Test
    void testAddTeamWhenScheduled() {
        assertEquals(MatchStatus.SCHEDULED, footballMatch.getMatchStatus());
    }

    @Test
    void testUpdateTeamWhenMatchInProgress() {
        footballMatch.setMatchStatus(MatchStatus.IN_PROGRESS);

        Team newTeamOne = new Team("TeamAUpdated");
        Team newTeamTwo = new Team("TeamBUpdated");

        assertFalse(footballMatch.setTeamOne (newTeamOne));
        assertFalse(footballMatch.setTeamTwo (newTeamTwo));
        assertEquals(MatchStatus.IN_PROGRESS, footballMatch.getMatchStatus());
    }

    @Test
    void testMatchWhenCompleted() {
        footballMatch.setMatchStatus(MatchStatus.COMPLETED);
        assertEquals(MatchStatus.COMPLETED, footballMatch.getMatchStatus());
    }

    @Test
    void testAddTeamWithValidSize() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        for (int i = 1; i <= 11; i++) {
            Player playerTeamA = new Player("Player" + i, i);
            Player playerTeamB = new Player("Player" + i + i, i + i);
            teamA.addPlayer(playerTeamA);
            teamB.addPlayer(playerTeamB);
        }
        Player referee = new Player("Robin Referee", 777);
        footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee)
                .build();

        assertEquals(11, footballMatch.getTeamOne().getPlayers().size());
        assertEquals(11, footballMatch.getTeamTwo().getPlayers().size());
    }

    @Test
    void testAddTeamWithTooManyPlayers() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        for (int i = 1; i <= 11; i++) {
            Player playerTeamA = new Player("Player" + i, i);
            Player playerTeamB = new Player("Player" + i + i, i + i);
            teamA.addPlayer(playerTeamA);
            teamB.addPlayer(playerTeamB);
        }

        assertFalse(teamA.addPlayer(new Player("Player12TeamA", 34)));
        assertFalse(teamB.addPlayer(new Player("Player12TeamB", 341)));
    }

    @Test
    void testAssignReferee() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        Player referee = new Player("Robin Referee", 777);
        footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee)
                .build();
        assertEquals(referee, footballMatch.getReferee());
    }

    @Test
    void testAssignRefereeTwice() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        Player referee1 = new Player("Referee1", 0);
        footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee1)
                .build();
        Player referee2 = new Player("Referee2", 1);

        assertFalse(footballMatch.assignReferee(referee2));
        assertEquals(referee1, footballMatch.getReferee());
    }

    @Test
    void testGetAllPlayerNames() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        Player referee1 = new Player("Referee1", 0);
        footballMatch = new FootballMatch.Builder()
                .teamOne(teamA)
                .teamTwo(teamB)
                .matchTitle("Match Football")
                .referee(referee1)
                .build();

        // Create players for team1 and team2
        Player player1 = new Player("Player1", 11);
        Player player2 = new Player("Player2", 23);
        Player player3 = new Player("Player3", 45);

        footballMatch.getTeamOne().addPlayer(player1);
        footballMatch.getTeamOne().addPlayer(player2);
        footballMatch.getTeamTwo().addPlayer(player3);

        List<String> playerNames = footballMatch.getAllPlayerNames();

        assertTrue(playerNames.contains("Player1"));
        assertTrue(playerNames.contains("Player2"));
        assertTrue(playerNames.contains("Player3"));
        assertEquals(3, playerNames.size());
    }

    @Test
    void testGetAllPlayerNamesNoPlayers() {
        List<String> playerNames = footballMatch.getAllPlayerNames();
        assertTrue(playerNames.isEmpty());
    }

    @Test
    void testGetFirstTeam() {
        assertEquals("TeamA", footballMatch.getTeamOne().getName());
    }

    @Test
    void testGetSecondTeam() {
        assertEquals("TeamB", footballMatch.getTeamTwo().getName());
    }
}
