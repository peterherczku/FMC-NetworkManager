package eu.networkmanager.common.redis.impl.player.get;

import eu.networkmanager.common.player.NetworkPlayer;
import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PlayerGetResponse extends RedisResponse {

    private boolean success;
    private NetworkPlayer networkPlayer;

    public PlayerGetResponse(UUID requestUuid, boolean success, NetworkPlayer networkPlayer) {
        super(requestUuid, "PLAYER_GET", PlayerGetResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.networkPlayer=networkPlayer;
    }

    public PlayerGetResponse() {
        super("PLAYER_GET");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PlayerGetResponse playerHandshakeResponse = (PlayerGetResponse) response;
        this.success=playerHandshakeResponse.isSuccess();
        this.networkPlayer=playerHandshakeResponse.getNetworkPlayer();
    }

    public boolean isSuccess() {
        return success;
    }

    public NetworkPlayer getNetworkPlayer() {
        return networkPlayer;
    }
}
