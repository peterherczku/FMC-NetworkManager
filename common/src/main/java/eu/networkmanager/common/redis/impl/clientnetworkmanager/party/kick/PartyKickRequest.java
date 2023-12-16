package eu.networkmanager.common.redis.impl.party.kick;


import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;

import java.util.Collections;
import java.util.UUID;

public class PartyKickRequest extends RedisRequest<PartyKickResponse> {

    private UUID leader;
    private String target;

    public PartyKickRequest() {
        super("PARTY_KICK");
    }

    public PartyKickRequest(UUID leader, String target) {
        super("PARTY_KICK", PartyKickRequest.class.getName(), Collections.emptyList());
        this.leader=leader;
        this.target=target;
    }

    @Override
    public void loadRequestData(RedisRequest<PartyKickResponse> request) {
        PartyKickRequest partyKickRequest = (PartyKickRequest) request;
        leader= partyKickRequest.getLeader();
        target= partyKickRequest.getTarget();
    }

    @Override
    public PartyKickResponse processRequest(RedisRequestListener listener) {
        return listener.processPartyKick(this);
    }

    public UUID getLeader() {
        return leader;
    }

    public String getTarget() {
        return target;
    }
}
