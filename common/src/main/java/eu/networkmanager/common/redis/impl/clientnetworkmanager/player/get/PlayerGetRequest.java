package eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;

import java.util.Collections;
import java.util.UUID;

public class PlayerGetRequest extends RedisRequest<PlayerGetResponse> {

    private UUID uuid;

    public PlayerGetRequest() {
        super("PLAYER_GET");
    }

    public PlayerGetRequest(UUID uuid) {
        super("PLAYER_GET", PlayerGetRequest.class.getName(), Collections.emptyList());
        this.uuid=uuid;
    }

    @Override
    public void loadRequestData(RedisRequest<PlayerGetResponse> request) {
        PlayerGetRequest playerGetRequest = (PlayerGetRequest) request;
        uuid = playerGetRequest.getUuid();
    }

    @Override
    public PlayerGetResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processPlayerGet(this);
    }

    public UUID getUuid() {
        return uuid;
    }
}
