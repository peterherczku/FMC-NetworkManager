package eu.networkmanager.common.redis.impl.party.reject;


import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PartyRejectResponse extends RedisResponse {

    private boolean success;
    private String message;

    public PartyRejectResponse() {
        super("PARTY_REJECT");
    }

    public PartyRejectResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "PARTY_REJECT", PartyRejectResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PartyRejectResponse partyRejectResponse = (PartyRejectResponse) response;
        success= partyRejectResponse.isSuccess();
        message= partyRejectResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}