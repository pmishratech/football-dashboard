package com.scoreboard.football.modal;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;
    private int score;
    private Player goalKeeper;
    private Player skipper;

    private static final int MAX_PLAYERS = 11;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public boolean addPlayer(Player player) {
        if (isFull() || players.contains(player) || !isPlayerNumberUnique(player)) {
            return false;
        }

        if ("Goli".equals(player.getName()) && goalKeeper == null) {
            goalKeeper = player;
        } else if ("Skipper".equals(player.getName()) && skipper == null) {
            skipper = player;
        }

        players.add(player);
        updateMustHaveGoalKeeperAndSkipperFlag();
        return true;
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getGoalKeeper() {
        return goalKeeper;
    }

    public Player getSkipper() {
        return skipper;
    }

    public boolean mustHaveGoalKeeperAndSkipper() {
        return goalKeeper != null && skipper != null;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points, String playerName) {
        score += points;
        players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findAny()
                .ifPresent(player -> player.increaseScore(points));
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", goalKeeper=" + goalKeeper +
                ", skipper=" + skipper +
                ", score=" + score +
                ", mustHaveGoalKeeperAndSkipper=" + mustHaveGoalKeeperAndSkipper() +
                '}';
    }

    private boolean isFull() {
        return players.size() >= MAX_PLAYERS;
    }

    private boolean isPlayerNumberUnique(Player player) {
        return players.stream()
                .noneMatch(existingPlayer -> existingPlayer.getNumber() == player.getNumber());
    }

    private void updateMustHaveGoalKeeperAndSkipperFlag() {
        if (goalKeeper != null && skipper != null) {
            goalKeeper.setName("Goli");
            skipper.setName("Skipper");
        }
    }
}
