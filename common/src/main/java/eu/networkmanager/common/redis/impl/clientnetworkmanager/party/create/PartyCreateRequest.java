package eu.networkmanager.common.redis.impl.clientnetworkmanager.party.create;


import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;

import java.util.Collections;
import java.util.UUID;

public class PartyCreateRequest extends RedisRequest<PartyCreateResponse> {

    private UUID leader;

    public PartyCreateRequest() {
        super("PARTY_CREATE");
    }

    public PartyCreateRequest(UUID leader) {
        super("PARTY_CREATE", PartyCreateRequest.class.getName(), Collections.emptyList());
        this.leader=leader;
    }

    @Override
    public void loadRequestData(RedisRequest<PartyCreateResponse> request) {
        PartyCreateRequest partyCreateRequest = (PartyCreateRequest) request;
        leader=partyCreateRequest.getLeader();
    }

    @Override
    public PartyCreateResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processPartyCreate(this);
    }

    public UUID getLeader() {
        return leader;
    }
}
