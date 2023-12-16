package eu.networkmanager.common.redis.impl.server.get;

import eu.networkmanager.common.redis.RedisResponse;
import eu.networkmanager.common.server.Server;

import java.util.Collections;
import java.util.UUID;

public class ServerGetResponse<T extends Server> extends RedisResponse {

    private boolean success;
    private T server;

    public ServerGetResponse(UUID requestUuid, boolean success, T server) {
        super(requestUuid, "SERVER_GET", ServerGetResponse.class.getName(), Collections.singletonList(server.getClass().getName()));
        this.success=success;
        this.server=server;
    }

    public ServerGetResponse() {
        super("SERVER_GET");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        ServerGetResponse<T> serverUpdateResponse = (ServerGetResponse<T>) response;
        this.success=serverUpdateResponse.isSuccess();
        this.server=serverUpdateResponse.getServer();
    }

    public boolean isSuccess() {
        return success;
    }

    public T getServer() {
        return server;
    }
}
