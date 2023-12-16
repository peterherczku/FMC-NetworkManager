package eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeRequest;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;

import java.util.Collections;
import java.util.UUID;

public class PlayerHandshakeRequest extends RedisRequest<PlayerHandshakeResponse> {

    private UUID uuid;
    private String name;
    private UUID bungeeUuid;

    public PlayerHandshakeRequest() {
        super("PLAYER_HANDSHAKE");
    }

    public PlayerHandshakeRequest(UUID uuid, String name, UUID bungeeUuid) {
        super("PLAYER_HANDSHAKE", PlayerHandshakeRequest.class.getName(), Collections.emptyList());
        this.uuid=uuid;
        this.name=name;
        this.bungeeUuid=bungeeUuid;
    }

    @Override
    public void loadRequestData(RedisRequest<PlayerHandshakeResponse> request) {
        PlayerHandshakeRequest playerHandshakeRequest = (PlayerHandshakeRequest) request;
        uuid = playerHandshakeRequest.getUuid();
        name = playerHandshakeRequest.getName();
        bungeeUuid = playerHandshakeRequest.getBungeeUuid();
    }

    @Override
    public PlayerHandshakeResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processPlayerHandshake(this);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getBungeeUuid() {
        return bungeeUuid;
    }
}
