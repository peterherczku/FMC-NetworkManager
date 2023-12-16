package eu.networkmanager.common.enums;

public enum MinigameType {

    BEDWARS_FOUR(16),
    BEDWARS_TWO(16);

    private int maxPlayers;

    MinigameType(int maxPlayers) {
        this.maxPlayers=maxPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
