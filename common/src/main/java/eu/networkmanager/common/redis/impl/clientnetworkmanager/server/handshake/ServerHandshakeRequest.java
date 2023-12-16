package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;
import eu.networkmanager.common.server.Server;

import java.util.Collections;

public class ServerHandshakeRequest<T extends Server> extends RedisRequest<ServerHandshakeResponse> {

    private T server;

    public ServerHandshakeRequest() {
        super("SERVER_HANDSHAKE");
    }

    public ServerHandshakeRequest(T server) {
        super("SERVER_HANDSHAKE", ServerHandshakeRequest.class.getName(), Collections.singletonList(server.getClass().getName()));
        this.server=server;
    }

    @Override
    public void loadRequestData(RedisRequest<ServerHandshakeResponse> request) {
        ServerHandshakeRequest<T> serverHandshakeRequest = (ServerHandshakeRequest<T>) request;
        this.server= serverHandshakeRequest.getServer();
    }

    @Override
    public ServerHandshakeResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processServerHandshake(this);
    }

    public T getServer() {
        return server;
    }
}
