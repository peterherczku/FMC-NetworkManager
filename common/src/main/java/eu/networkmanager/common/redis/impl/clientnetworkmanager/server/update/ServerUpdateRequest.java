package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;
import eu.networkmanager.common.server.Server;

import java.util.Collections;

public class ServerUpdateRequest<T extends Server> extends RedisRequest<ServerUpdateResponse> {

    private T server;

    public ServerUpdateRequest() {
        super("SERVER_UPDATE");
    }

    public ServerUpdateRequest(T server) {
        super("SERVER_UPDATE", ServerUpdateRequest.class.getName(), Collections.singletonList(server.getClass().getName()));
        this.server=server;
    }

    @Override
    public void loadRequestData(RedisRequest<ServerUpdateResponse> request) {
        ServerUpdateRequest<T> serverUpdateRequest = (ServerUpdateRequest<T>) request;
        server = serverUpdateRequest.getServer();
    }

    @Override
    public ServerUpdateResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processServerUpdate(this);
    }

    public T getServer() {
        return server;
    }
}
