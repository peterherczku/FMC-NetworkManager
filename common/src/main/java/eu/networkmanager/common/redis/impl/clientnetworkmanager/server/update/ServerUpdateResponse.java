package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update;

import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class ServerUpdateResponse extends RedisResponse {

    private boolean success;
    private String message;

    public ServerUpdateResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "SERVER_UPDATE", ServerUpdateResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    public ServerUpdateResponse() {
        super("SERVER_UPDATE");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        ServerUpdateResponse serverUpdateResponse = (ServerUpdateResponse) response;
        this.success=serverUpdateResponse.isSuccess();
        this.message=serverUpdateResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
