package eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype;

import eu.networkmanager.common.redis.RedisResponse;
import eu.networkmanager.common.server.Server;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerGetByTypeResponse<T extends Server> extends RedisResponse {

    private boolean success;
    private List<T> servers;

    public ServerGetByTypeResponse(UUID requestUuid, boolean success, List<T> servers) {
        super(requestUuid, "SERVER_GET", ServerGetByTypeResponse.class.getName(), Collections.singletonList(servers.get(0).getClass().getName()));
        this.success=success;
        this.servers=servers;
    }

    public ServerGetByTypeResponse() {
        super("SERVER_GET");
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        ServerGetByTypeResponse<T> serverUpdateResponse = (ServerGetByTypeResponse<T>) response;
        this.success=serverUpdateResponse.isSuccess();
        this.servers=serverUpdateResponse.getServers();
    }

    public boolean isSuccess() {
        return success;
    }

    public List<T> getServers() {
        return servers;
    }
}
