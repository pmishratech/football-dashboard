package com.scoreboard.football.modal;

import java.util.Objects;

public class Player {
    private String name;
    private int number;
    private int score;

    public Player(String name, int number) {
        validateNumber(number);
        this.name = name;
        this.number = number;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        score += points;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return number == player.number && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, score);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", score=" + score +
                '}';
    }

    private void validateNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Player number cannot be negative");
        }
    }
}
