package eu.networkmanager.server;

import eu.networkmanager.common.redis.RedisServer;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get.PlayerGetRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake.PlayerHandshakeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update.PlayerUpdateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeRequest;
import eu.networkmanager.server.redis.RedisRequestHandler;

public class Server {

    public static void main(String[] args) {
        RedisServer server = new RedisServer("CLIENT->SERVER", new RedisRequestHandler());
        server.register(new ServerHandshakeRequest<>());
        server.register(new PlayerGetRequest());
        server.register(new PlayerUpdateRequest());
        server.register(new PlayerHandshakeRequest());
        server.init();
    }

}
