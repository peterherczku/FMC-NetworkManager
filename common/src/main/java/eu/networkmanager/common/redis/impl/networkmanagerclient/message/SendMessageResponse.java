package eu.networkmanager.common.redis.impl.networkmanagerclient.message;

import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SendMessageResponse extends RedisResponse {

    private boolean success;

    public SendMessageResponse(UUID requestUuid, boolean success) {
        super(requestUuid, "SEND_MESSAGE", SendMessageResponse.class.getName(), Collections.emptyList());
        this.success=success;
    }

    public SendMessageResponse() {
        super("SEND_MESSAGE");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        SendMessageResponse sendMessageResponse = (SendMessageResponse) response;
        this.success=sendMessageResponse.isSuccess();
    }

    public boolean isSuccess() {
        return success;
    }
}
