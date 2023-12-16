package eu.networkmanager.common.redis.listeners;

import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.impl.networkmanagerclient.message.SendMessageRequest;
import eu.networkmanager.common.redis.impl.networkmanagerclient.message.SendMessageResponse;

public interface RedisNetworkManagerClientListener extends RedisRequestListener {

    SendMessageResponse processSendMessage(SendMessageRequest request);

}
