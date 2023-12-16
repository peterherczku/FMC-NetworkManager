package eu.networkmanager.common.redis.impl.networkmanagerclient.message;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.RedisResponse;
import eu.networkmanager.common.redis.listeners.RedisNetworkManagerClientListener;

import java.util.Collections;
import java.util.UUID;

public class SendMessageRequest extends RedisRequest {

    private UUID playerUuid;
    private String message;

    public SendMessageRequest() {
        super("SEND_MESSAGE");
    }

    public SendMessageRequest(UUID playerUuid, String message) {
        super("SEND_MESSAGE", SendMessageRequest.class.getName(), Collections.emptyList());
        this.playerUuid=playerUuid;
        this.message=message;
    }

    @Override
    public void loadRequestData(RedisRequest request) {
        SendMessageRequest sendMessageRequest = (SendMessageRequest) request;
        playerUuid=sendMessageRequest.getPlayerUuid();
        message=sendMessageRequest.getMessage();
    }

    @Override
    public RedisResponse processRequest(RedisRequestListener listener) {
        return ((RedisNetworkManagerClientListener) listener).processSendMessage(this);
    }

    public String getMessage() {
        return message;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }
}
