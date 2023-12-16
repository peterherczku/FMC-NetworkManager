package eu.networkmanager.common.redis.impl.clientnetworkmanager.party.join;


import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;

import java.util.Collections;
import java.util.UUID;

public class PartyJoinRequest extends RedisRequest<PartyJoinResponse> {

    private UUID player;

    public PartyJoinRequest() {
        super("PARTY_JOIN");
    }

    public PartyJoinRequest(UUID player) {
        super("PARTY_JOIN", PartyJoinRequest.class.getName(), Collections.emptyList());
        this.player=player;
    }

    @Override
    public void loadRequestData(RedisRequest<PartyJoinResponse> request) {
        PartyJoinRequest partyJoinRequest = (PartyJoinRequest) request;
        player= partyJoinRequest.getPlayer();
    }

    @Override
    public PartyJoinResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processPartyJoin(this);
    }

    public UUID getPlayer() {
        return player;
    }
}
