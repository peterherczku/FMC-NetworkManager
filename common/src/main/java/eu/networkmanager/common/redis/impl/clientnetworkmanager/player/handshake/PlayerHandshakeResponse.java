package eu.networkmanager.common.redis.impl.player.handshake;

import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PlayerHandshakeResponse extends RedisResponse {

    private boolean success;

    public PlayerHandshakeResponse(UUID requestUuid, boolean success) {
        super(requestUuid, "PLAYER_HANDSHAKE", PlayerHandshakeResponse.class.getName(), Collections.emptyList());
        this.success=success;
    }

    public PlayerHandshakeResponse() {
        super("PLAYER_HANDSHAKE");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PlayerHandshakeResponse playerHandshakeResponse = (PlayerHandshakeResponse) response;
        this.success=playerHandshakeResponse.isSuccess();
    }

    public boolean isSuccess() {
        return success;
    }
}
