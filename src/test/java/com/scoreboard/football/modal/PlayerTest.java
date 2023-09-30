package com.scoreboard.football.modal;

import com.scoreboard.football.modal.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerInitialization() {
        Player player = new Player("John", 10);

        assertEquals("John", player.getName());
        assertEquals(10, player.getNumber());
    }

    @Test
    void testPlayerInitializationWithNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player("Alice", -5);
        });
    }

    @Test
    void testPlayerEquals() {
        Player player1 = new Player("John", 10);
        Player player2 = new Player("John", 10);
        Player player3 = new Player("Alice", 7);

        assertEquals(player1, player2); // Players with the same name and number should be equal
        assertNotEquals(player1, player3); // Players with different names or numbers should not be equal
    }

    @Test
    void testPlayerHashCode() {
        Player player1 = new Player("John", 10);
        Player player2 = new Player("John", 10);
        Player player3 = new Player("Alice", 7);

        assertEquals(player1.hashCode(), player2.hashCode()); // Hash codes should be the same for equal players
        assertNotEquals(player1.hashCode(), player3.hashCode()); // Hash codes should be different for different players
    }

    @Test
    void testPlayerToString() {
        Player player = new Player("John", 10);
        String expectedString = "Player{name='John', number=10, score=0}";

        assertEquals(expectedString, player.toString());
    }
}
