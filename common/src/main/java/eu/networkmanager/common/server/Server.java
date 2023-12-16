package eu.networkmanager.common.server;

import java.util.UUID;

public abstract class Server {

    private UUID serverUUid;
    private String serverName;
    private boolean allowPlayers;

    public Server(UUID serverUUid, String serverName, boolean allowPlayers) {
        this.serverUUid=serverUUid;
        this.serverName=serverName;
        this.allowPlayers=allowPlayers;
    }

    public boolean isAllowPlayers() {
        return allowPlayers;
    }

    public String getServerName() {
        return serverName;
    }

    public UUID getServerUUid() {
        return serverUUid;
    }

}
