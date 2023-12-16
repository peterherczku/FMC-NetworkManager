package eu.networkmanager.server.player;

import eu.networkmanager.common.player.NetworkPlayer;
import eu.networkmanager.common.server.BungeeServer;
import eu.networkmanager.server.managers.ServerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OnlinePlayers {

    private static Map<UUID, NetworkPlayer> onlinePlayers = new HashMap<>();

    public static NetworkPlayer addPlayer(UUID uuid, String name, UUID bungeeUuid) {
        NetworkPlayer networkPlayer = new NetworkPlayer(uuid, name, (BungeeServer) ServerManager.getServer(bungeeUuid), null);
        onlinePlayers.put(uuid, networkPlayer);
        return networkPlayer;
    }

    public static void removePlayer(UUID uuid) {
        onlinePlayers.remove(uuid);
    }

    public static NetworkPlayer getPlayer(UUID uuid) {
        if (!onlinePlayers.containsKey(uuid)) return null;
        return onlinePlayers.get(uuid);
    }

    public static Optional<NetworkPlayer> getPlayer(String name) {
        return onlinePlayers.values().stream().filter(player -> player.getName().equals(name)).findAny();
    }

    public static void updatePlayer(NetworkPlayer updatedPlayer) {
        onlinePlayers.replace(updatedPlayer.getUuid(), updatedPlayer);
    }

}
