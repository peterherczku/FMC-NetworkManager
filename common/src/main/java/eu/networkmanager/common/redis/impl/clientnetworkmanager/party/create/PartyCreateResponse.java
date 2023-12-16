package eu.networkmanager.common.redis.impl.party.create;


import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PartyCreateResponse extends RedisResponse {

    private boolean success;
    private String message;

    public PartyCreateResponse() {
        super("PARTY_CREATE");
    }

    public PartyCreateResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "PARTY_CREATE", PartyCreateResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PartyCreateResponse partyCreateResponse = (PartyCreateResponse) response;
        success=partyCreateResponse.isSuccess();
        message=partyCreateResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}