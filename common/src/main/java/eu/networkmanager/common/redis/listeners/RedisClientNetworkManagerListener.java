package eu.networkmanager.common.redis.listeners;

import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.create.PartyCreateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.create.PartyCreateResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.invite.PartyInviteRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.invite.PartyInviteResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.join.PartyJoinRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.join.PartyJoinResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.kick.PartyKickRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.kick.PartyKickResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.reject.PartyRejectRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.party.reject.PartyRejectResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get.PlayerGetRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get.PlayerGetResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake.PlayerHandshakeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake.PlayerHandshakeResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update.PlayerUpdateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update.PlayerUpdateResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.get.ServerGetRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.get.ServerGetResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype.ServerGetByTypeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype.ServerGetByTypeResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update.ServerUpdateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update.ServerUpdateResponse;

public interface RedisClientNetworkManagerListener extends RedisRequestListener {

    ServerHandshakeResponse processServerHandshake(ServerHandshakeRequest<?> request);
    ServerUpdateResponse processServerUpdate(ServerUpdateRequest<?> request);
    ServerGetResponse<?> processServerGet(ServerGetRequest request);
    ServerGetByTypeResponse<?> processGetServerByType(ServerGetByTypeRequest<?> request);

    PlayerHandshakeResponse processPlayerHandshake(PlayerHandshakeRequest request);
    PlayerGetResponse processPlayerGet(PlayerGetRequest request);
    PlayerUpdateResponse proccessPlayerUpdate(PlayerUpdateRequest request);

    PartyCreateResponse processPartyCreate(PartyCreateRequest request);
    PartyJoinResponse processPartyJoin(PartyJoinRequest request);
    PartyRejectResponse processPartyReject(PartyRejectRequest request);
    PartyInviteResponse processPartyInvite(PartyInviteRequest request);
    PartyKickResponse processPartyKick(PartyKickRequest request);

}
