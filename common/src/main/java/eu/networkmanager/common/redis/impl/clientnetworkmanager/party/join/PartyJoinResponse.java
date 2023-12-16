package eu.networkmanager.common.redis.impl.party.join;


import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PartyJoinResponse extends RedisResponse {

    private boolean success;
    private String message;

    public PartyJoinResponse() {
        super("PARTY_JOIN");
    }

    public PartyJoinResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "PARTY_JOIN", PartyJoinResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PartyJoinResponse partyJoinResponse = (PartyJoinResponse) response;
        success= partyJoinResponse.isSuccess();
        message= partyJoinResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}