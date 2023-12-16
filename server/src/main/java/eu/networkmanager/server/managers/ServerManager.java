package eu.networkmanager.server.managers;

import eu.networkmanager.common.server.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServerManager {

    public static Map<UUID, Server> servers = new HashMap<>();

    public static void addServer(Server server) {
        servers.put(server.getServerUUid(), server);
    }

    public static void removeServer(Server server) {
        servers.remove(server.getServerUUid());
    }

    public static Server getServer(UUID uuid) {
        if (!servers.containsKey(uuid)) return null;
        return servers.get(uuid);
    }

    public static void updateServer(Server updatedServer) {
        servers.replace(updatedServer.getServerUUid(), updatedServer);
    }

    public static List<Server> getServers() {
        return servers.values().stream().toList();
    }

}
