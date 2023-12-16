package eu.networkmanager.common.server;

import java.util.UUID;

public class GameServer extends Server{

    private boolean waitingForData;

    public GameServer(UUID serverUUid, String serverName, boolean allowPlayers, boolean waitingForData) {
        super(serverUUid, serverName, allowPlayers);
        this.waitingForData=waitingForData;
    }

    public boolean isWaitingForData() {
        return waitingForData;
    }
}
