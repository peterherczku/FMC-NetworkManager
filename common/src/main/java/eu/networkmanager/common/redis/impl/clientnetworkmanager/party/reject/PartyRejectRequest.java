package eu.networkmanager.common.redis.impl.party.reject;


import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;

import java.util.Collections;
import java.util.UUID;

public class PartyRejectRequest extends RedisRequest<PartyRejectResponse> {

    private UUID player;

    public PartyRejectRequest() {
        super("PARTY_REJECT");
    }

    public PartyRejectRequest(UUID player) {
        super("PARTY_REJECT", PartyRejectRequest.class.getName(), Collections.emptyList());
        this.player=player;
    }

    @Override
    public void loadRequestData(RedisRequest<PartyRejectResponse> request) {
        PartyRejectRequest partyRejectRequest = (PartyRejectRequest) request;
        player= partyRejectRequest.getPlayer();
    }

    @Override
    public PartyRejectResponse processRequest(RedisRequestListener listener) {
        return listener.processPartyReject(this);
    }

    public UUID getPlayer() {
        return player;
    }
}
