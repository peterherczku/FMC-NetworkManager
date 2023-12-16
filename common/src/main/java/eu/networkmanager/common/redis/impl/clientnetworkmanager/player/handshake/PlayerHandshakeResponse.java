package eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake;

import eu.networkmanager.common.player.NetworkPlayer;
import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PlayerHandshakeResponse extends RedisResponse {

    private boolean success;
    private NetworkPlayer networkPlayer;

    public PlayerHandshakeResponse(UUID requestUuid, boolean success, NetworkPlayer networkPlayer) {
        super(requestUuid, "PLAYER_HANDSHAKE", PlayerHandshakeResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.networkPlayer=networkPlayer;
    }

    public PlayerHandshakeResponse() {
        super("PLAYER_HANDSHAKE");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PlayerHandshakeResponse playerHandshakeResponse = (PlayerHandshakeResponse) response;
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
