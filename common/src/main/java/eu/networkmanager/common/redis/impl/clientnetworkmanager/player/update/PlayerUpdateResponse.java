package eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update;

import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PlayerUpdateResponse extends RedisResponse {

    private boolean success;

    public PlayerUpdateResponse(UUID requestUuid, boolean success) {
        super(requestUuid, "PLAYER_UPDATE", PlayerUpdateResponse.class.getName(), Collections.emptyList());
        this.success=success;
    }

    public PlayerUpdateResponse() {
        super("PLAYER_UPDATE");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PlayerUpdateResponse playerUpdateResponse = (PlayerUpdateResponse) response;
        this.success=playerUpdateResponse.isSuccess();
    }

    public boolean isSuccess() {
        return success;
    }

}
