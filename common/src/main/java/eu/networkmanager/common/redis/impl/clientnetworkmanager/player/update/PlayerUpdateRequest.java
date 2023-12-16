package eu.networkmanager.common.redis.impl.player.update;

import eu.networkmanager.common.player.NetworkPlayer;
import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.impl.player.get.PlayerGetResponse;

import java.util.Collections;
import java.util.UUID;

public class PlayerUpdateRequest extends RedisRequest<PlayerUpdateResponse> {

    private NetworkPlayer updatedPlayer;

    public PlayerUpdateRequest() {
        super("PLAYER_UPDATE");
    }

    public PlayerUpdateRequest(NetworkPlayer updatedPlayer) {
        super("PLAYER_GET", PlayerUpdateRequest.class.getName(), Collections.emptyList());
        this.updatedPlayer=updatedPlayer;
    }

    @Override
    public void loadRequestData(RedisRequest<PlayerUpdateResponse> request) {
        PlayerUpdateRequest playerGetRequest = (PlayerUpdateRequest) request;
        updatedPlayer = playerGetRequest.getUpdatedPlayer();
    }

    @Override
    public PlayerUpdateResponse processRequest(RedisRequestListener listener) {
        return listener.proccessPlayerUpdate(this);
    }

    public NetworkPlayer getUpdatedPlayer() {
        return updatedPlayer;
    }
}
