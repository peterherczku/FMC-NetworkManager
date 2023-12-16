package eu.networkmanager.common.redis.impl.clientnetworkmanager.party.invite;


import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PartyInviteResponse extends RedisResponse {

    private boolean success;
    private String message;

    public PartyInviteResponse() {
        super("PARTY_JOIN");
    }

    public PartyInviteResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "PARTY_JOIN", PartyInviteResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PartyInviteResponse partyInviteResponse = (PartyInviteResponse) response;
        success= partyInviteResponse.isSuccess();
        message= partyInviteResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}