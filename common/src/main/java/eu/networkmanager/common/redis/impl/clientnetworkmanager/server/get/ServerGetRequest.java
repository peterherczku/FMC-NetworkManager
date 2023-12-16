package eu.networkmanager.common.redis.impl.server.get;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;

import java.util.Collections;
import java.util.UUID;

public class ServerGetRequest extends RedisRequest<ServerGetResponse<?>> {

    private UUID uuid;

    public ServerGetRequest() {
        super("SERVER_GET");
    }

    public ServerGetRequest(UUID uuid) {
        super("SERVER_GET", ServerGetRequest.class.getName(), Collections.emptyList());
        this.uuid=uuid;
    }

    @Override
    public void loadRequestData(RedisRequest<ServerGetResponse<?>> request) {
        ServerGetRequest serverGetRequest = (ServerGetRequest) request;
        uuid = serverGetRequest.getUuid();
    }

    @Override
    public ServerGetResponse<?> processRequest(RedisRequestListener listener) {
        return listener.processServerGet(this);
    }

    public UUID getUuid() {
        return uuid;
    }
}
