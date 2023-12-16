package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake;

import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class ServerHandshakeResponse extends RedisResponse {

    private boolean success;
    private String message;

    public ServerHandshakeResponse() {
        super("SERVER_HANDSHAKE");
    }

    public ServerHandshakeResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "SERVER_HANDSHAKE", ServerHandshakeResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        ServerHandshakeResponse serverHandshakeResponse = (ServerHandshakeResponse) response;
        this.success= serverHandshakeResponse.isSuccess();
        this.message= serverHandshakeResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
