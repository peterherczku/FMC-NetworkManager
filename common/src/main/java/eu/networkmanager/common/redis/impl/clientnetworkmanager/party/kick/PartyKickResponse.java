package eu.networkmanager.common.redis.impl.clientnetworkmanager.party.kick;


import eu.networkmanager.common.redis.RedisResponse;

import java.util.Collections;
import java.util.UUID;

public class PartyKickResponse extends RedisResponse {

    private boolean success;
    private String message;

    public PartyKickResponse() {
        super("PARTY_KICK");
    }

    public PartyKickResponse(UUID requestUuid, boolean success, String message) {
        super(requestUuid, "PARTY_KICK", PartyKickResponse.class.getName(), Collections.emptyList());
        this.success=success;
        this.message=message;
    }

    @Override
    public void loadResponseData(RedisResponse response) {
        PartyKickResponse partyKickResponse = (PartyKickResponse) response;
        success= partyKickResponse.isSuccess();
        message= partyKickResponse.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}