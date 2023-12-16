package eu.networkmanager.common.redis.impl.clientnetworkmanager.party.invite;


import eu.networkmanager.common.redis.RedisRequest;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;

import java.util.Collections;
import java.util.UUID;

public class PartyInviteRequest extends RedisRequest<PartyInviteResponse> {

    private UUID leader;
    private String target;

    public PartyInviteRequest() {
        super("PARTY_INVITE");
    }

    public PartyInviteRequest(UUID leader, String target) {
        super("PARTY_INVITE", PartyInviteRequest.class.getName(), Collections.emptyList());
        this.leader=leader;
        this.target=target;
    }

    @Override
    public void loadRequestData(RedisRequest<PartyInviteResponse> request) {
        PartyInviteRequest partyInviteRequest = (PartyInviteRequest) request;
        leader = partyInviteRequest.getLeader();
        target = partyInviteRequest.getTarget();
    }

    @Override
    public PartyInviteResponse processRequest(RedisRequestListener listener) {
        return ((RedisClientNetworkManagerListener) listener).processPartyInvite(this);
    }

    public UUID getLeader() {
        return leader;
    }

    public String getTarget() {
        return target;
    }
}
