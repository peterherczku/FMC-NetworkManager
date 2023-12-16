package eu.networkmanager.common.server;

import java.util.UUID;

public class BungeeServer extends Server{

    public BungeeServer(UUID serverUUid, String serverName, boolean allowPlayers) {
        super(serverUUid, serverName, allowPlayers);
    }
}
