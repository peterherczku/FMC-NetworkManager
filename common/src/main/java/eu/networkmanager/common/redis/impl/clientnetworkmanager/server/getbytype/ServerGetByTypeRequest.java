package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype;

import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;
import eu.networkmanager.common.server.Server;

import java.util.Collections;

public class ServerGetByTypeRequest<T extends Server> extends RedisRequest<ServerGetByTypeResponse<T>> {

    private Class<T> aClass;

    public ServerGetByTypeRequest() {
        super("SERVER_GET");
    }

    public ServerGetByTypeRequest(Class<T> aCLass) {
        super("SERVER_GET", ServerGetByTypeRequest.class.getName(), Collections.singletonList(aCLass.getName()));
        this.aClass=aCLass;
    }

    public Class<T> getaClass() {
        return aClass;
    }

    @Override
    public void loadRequestData(RedisRequest<ServerGetByTypeResponse<T>> request) {
        ServerGetByTypeRequest<T> serverGetByTypeRequest = (ServerGetByTypeRequest<T>) request;
        aClass=serverGetByTypeRequest.getaClass();
    }

    @Override
    public ServerGetByTypeResponse<T> processRequest(RedisRequestListener listener) {
        return (ServerGetByTypeResponse<T>) ((RedisClientNetworkManagerListener) listener).processGetServerByType(this);
    }
}
