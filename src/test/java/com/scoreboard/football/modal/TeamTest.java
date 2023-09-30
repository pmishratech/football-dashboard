package com.scoreboard.football.modal;

import com.scoreboard.football.modal.Player;
import com.scoreboard.football.modal.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class TeamTest {
    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("TeamA");
    }

    @Test
    void testAddPlayer() {
        Player player = new Player("Player1", 10);
        team.addPlayer(player);
        assertTrue(team.getPlayers().contains(player));
    }

    @Test
    void testAddMaxPlayers() {
        for (int i = 1; i <= 11; i++) {
            Player player = new Player("Player" + i, i);
            assertTrue(team.addPlayer(player));
        }

        assertEquals(11, team.getPlayers().size());
    }

    @Test
    void testAddMoreThanMaxPlayers() {
        for (int i = 1; i <= 12; i++) {
            Player player = new Player("Player" + i, i);
            boolean added = team.addPlayer(player);
            if (i <= 11) {
                assertTrue(added); // Adding the first 11 players should succeed
            } else {
                assertFalse(added); // Adding the 12th player should fail
            }
        }

        assertEquals(11, team.getPlayers().size());
    }

    @Test
    void testAddPlayerWithExistingNumber() {
        Player player1 = new Player("Player1", 1);
        Player player2 = new Player("Player2", 1); // Duplicate player number

        assertTrue(team.addPlayer(player1));
        assertFalse(team.addPlayer(player2)); // Adding a player with the same number should fail
        assertEquals(1, team.getPlayers().size());
    }

    @Test
    void testRemovePlayer() {
        Player player = new Player("Player1", 10);
        team.addPlayer(player);
        team.removePlayer(player);
        assertFalse(team.getPlayers().contains(player));
    }

    @Test
    void testAddDuplicatePlayer() {
        Player player1 = new Player("Player1", 10);
        team.addPlayer(player1);
        assertFalse(team.addPlayer(player1)); // Adding the same player again should fail
    }

    @Test
    void testMustHaveGoalKeeperAndSkipper() {
        Player goalKeeper = new Player("Goli", 1);
        Player skipper = new Player("Skipper", 2);

        assertFalse(team.mustHaveGoalKeeperAndSkipper()); // Both "Goli" and "Skipper" are not added yet

        assertTrue(team.addPlayer(goalKeeper)); // Adding Goli
        assertFalse(team.mustHaveGoalKeeperAndSkipper()); // Still missing Skipper

        assertTrue(team.addPlayer(skipper)); // Adding Skipper
        assertTrue(team.mustHaveGoalKeeperAndSkipper()); // Both "Goli" and "Skipper" are present
    }

    @Test
    void testRemoveNonExistentPlayer() {
        Player player1 = new Player("Player1", 10);
        assertFalse(team.removePlayer(player1)); // Removing a player that doesn't exist should fail
    }

    @Test
    void testGetPlayers() {
        Player player1 = new Player("Player1", 10);
        Player player2 = new Player("Player2", 11);
        team.addPlayer(player1);
        team.addPlayer(player2);

        assertEquals(2, team.getPlayers().size());
        assertTrue(team.getPlayers().contains(player1));
        assertTrue(team.getPlayers().contains(player2));
    }

    @Test
    void testGetGoalKeeper() {
        Player goalKeeper = new Player("Goli", 1);
        team.addPlayer(goalKeeper);

        assertEquals(goalKeeper, team.getGoalKeeper());
    }

    @Test
    void testGetSkipper() {
        Player skipper = new Player("Skipper", 2);
        team.addPlayer(skipper);

        assertEquals(skipper, team.getSkipper());
    }

    @Test
    void testGetGoalKeeperWhenNoGoliInTeam() {
        // No GoalKeeper added to the team
        assertNull(team.getGoalKeeper());
    }

    @Test
    void testGetSkipperWhenNoSkipperInTeam() {
        // No Skipper added to the team
        assertNull(team.getSkipper());
    }

    @Test
    void testGetGoalKeeperWithRegularPlayers() {
        Player regularPlayer1 = new Player("Player1", 3);
        Player regularPlayer2 = new Player("Player2", 4);
        team.addPlayer(regularPlayer1);
        team.addPlayer(regularPlayer2);

        assertNull(team.getGoalKeeper()); // No GoalKeeper added to the team
    }

    @Test
    void testGetSkipperWithRegularPlayers() {
        Player regularPlayer1 = new Player("Player1", 3);
        Player regularPlayer2 = new Player("Player2", 4);
        team.addPlayer(regularPlayer1);
        team.addPlayer(regularPlayer2);

        assertNull(team.getSkipper()); // No Skipper added to the team
    }
}