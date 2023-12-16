package eu.networkmanager.common.player;

import eu.networkmanager.common.server.BungeeServer;
import eu.networkmanager.common.server.GameServer;
import eu.networkmanager.common.server.Server;

import java.util.UUID;

public class NetworkPlayer {

    private final UUID uuid;
    private String name;
    private BungeeServer currentBungee;
    private GameServer currentPaper;

    public NetworkPlayer(UUID uuid, String name, BungeeServer currentBungee, GameServer currentPaper) {
        this.uuid=uuid;
        this.name=name;
        this.currentBungee=currentBungee;
        this.currentPaper=currentPaper;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public BungeeServer getCurrentBungee() {
        return currentBungee;
    }

    public GameServer getCurrentPaper() {
        return currentPaper;
    }

    public void setCurrentPaper(GameServer currentPaper) {
        this.currentPaper = currentPaper;
    }
}
