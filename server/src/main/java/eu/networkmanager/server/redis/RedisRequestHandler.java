package eu.networkmanager.server.redis;

import eu.networkmanager.common.party.Party;
import eu.networkmanager.common.player.NetworkPlayer;
import eu.networkmanager.common.redis.RedisRequestListener;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get.PlayerGetRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.get.PlayerGetResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake.PlayerHandshakeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.handshake.PlayerHandshakeResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.get.ServerGetRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.get.ServerGetResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype.ServerGetByTypeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.getbytype.ServerGetByTypeResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.handshake.ServerHandshakeResponse;
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
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update.PlayerUpdateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.player.update.PlayerUpdateResponse;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update.ServerUpdateRequest;
import eu.networkmanager.common.redis.impl.clientnetworkmanager.server.update.ServerUpdateResponse;
import eu.networkmanager.common.redis.listeners.RedisClientNetworkManagerListener;
import eu.networkmanager.common.server.BungeeServer;
import eu.networkmanager.common.server.Server;
import eu.networkmanager.server.managers.PartyManager;
import eu.networkmanager.server.managers.ServerManager;
import eu.networkmanager.server.player.OnlinePlayers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RedisRequestHandler implements RedisClientNetworkManagerListener {

    @Override
    public ServerHandshakeResponse processServerHandshake(ServerHandshakeRequest<?> request) {
        Server server = request.getServer();
        if (ServerManager.getServer(server.getServerUUid())!=null) {
            return new ServerHandshakeResponse(
                    request.getRequestUuid(),
                    false,
                    "This server is already registered."
            );
        }

        ServerManager.addServer(server);
        return new ServerHandshakeResponse(
                request.getRequestUuid(),
                true,
                "Server "+server.getServerUUid()+" registered."
        );
    }

    @Override
    public ServerUpdateResponse processServerUpdate(ServerUpdateRequest<?> request) {
        Server server = request.getServer();
        if(ServerManager.getServer(server.getServerUUid())==null) {
            return new ServerUpdateResponse(
                    request.getRequestUuid(),
                    false,
                    "This server is not registered."
            );
        }

        ServerManager.updateServer(server);
        return new ServerUpdateResponse(
                request.getRequestUuid(),
                true,
                "Server "+server.getServerUUid()+" updated."
        );
    }

    @Override
    public ServerGetResponse<?> processServerGet(ServerGetRequest request) {
        Server server = ServerManager.getServer(request.getRequestUuid());
        if (server==null) {
            return new ServerGetResponse<>(
                    request.getRequestUuid(),
                    false,
                    null
            );
        }

        return new ServerGetResponse<>(
                request.getRequestUuid(),
                true,
                server
        );
    }

    @Override
    public ServerGetByTypeResponse<?> processGetServerByType(ServerGetByTypeRequest<?> request) {
        List<Server> servers = ServerManager.getServers().stream().filter(server -> server.getClass().getName().equals(request.getaClass().getName())).collect(Collectors.toList());
        return new ServerGetByTypeResponse<>(
                request.getRequestUuid(),
                true,
                servers
        );
    }

    @Override
    public PlayerHandshakeResponse processPlayerHandshake(PlayerHandshakeRequest request) {
        UUID uuid = request.getUuid();
        String name = request.getName();
        UUID bungeeUuid = request.getBungeeUuid();

        if (OnlinePlayers.getPlayer(uuid)!=null) {
            return new PlayerHandshakeResponse(request.getRequestUuid(), false, null);
        }
        if (ServerManager.getServer(bungeeUuid)==null) {
            return new PlayerHandshakeResponse(request.getRequestUuid(), false, null);
        }
        if (!(ServerManager.getServer(bungeeUuid) instanceof BungeeServer)) {
            return new PlayerHandshakeResponse(request.getRequestUuid(), false, null);
        }

        NetworkPlayer networkPlayer = OnlinePlayers.addPlayer(uuid, name, bungeeUuid);
        return new PlayerHandshakeResponse(request.getRequestUuid(), true, networkPlayer);
    }

    @Override
    public PlayerGetResponse processPlayerGet(PlayerGetRequest request) {
        UUID uuid = request.getUuid();
        if (OnlinePlayers.getPlayer(uuid)==null) {
            return new PlayerGetResponse(request.getRequestUuid(), false, null);
        }

        return new PlayerGetResponse(request.getRequestUuid(), true, OnlinePlayers.getPlayer(uuid));
    }

    @Override
    public PlayerUpdateResponse proccessPlayerUpdate(PlayerUpdateRequest request) {
        NetworkPlayer updatedPlayer = request.getUpdatedPlayer();
        if (OnlinePlayers.getPlayer(updatedPlayer.getUuid())==null) {
            return new PlayerUpdateResponse(request.getRequestUuid(), false);
        }

        OnlinePlayers.updatePlayer(updatedPlayer);
        return new PlayerUpdateResponse(request.getRequestUuid(), true);
    }

    @Override
    public PartyCreateResponse processPartyCreate(PartyCreateRequest request) {
        UUID leader = request.getLeader();
        if (PartyManager.getParty(leader).isPresent()) {
            return new PartyCreateResponse(request.getRequestUuid(), false, "You must leave your party before creating a new one!");
        }

        PartyManager.createParty(leader);
        return new PartyCreateResponse(request.getRequestUuid(), true, "You have successfully created a party!");
    }

    @Override
    public PartyJoinResponse processPartyJoin(PartyJoinRequest request) {
        UUID player = request.getPlayer();
        if (PartyManager.getPendingInvitation(player)==null) {
            return new PartyJoinResponse(
                    request.getRequestUuid(),
                    false,
                    "You do not have any pending invitation!"
            );
        }

        Party party = PartyManager.getPendingInvitation(player);
        party.addMember(player);
        PartyManager.removeInvitation(player);

        // todo: notify party members

        return new PartyJoinResponse(
                request.getRequestUuid(),
                true,
                "You have successfully joined the party!"
        );
    }

    @Override
    public PartyRejectResponse processPartyReject(PartyRejectRequest request) {
        UUID player = request.getPlayer();
        if (PartyManager.getPendingInvitation(player)==null) {
            return new PartyRejectResponse(
                    request.getRequestUuid(),
                    false,
                    "You do not have any pending invitation!"
            );
        }

        // todo: notify party leader

        PartyManager.removeInvitation(player);
        return new PartyRejectResponse(
                request.getRequestUuid(),
                true,
                "You have successfully rejected the invitation!"
        );
    }

    @Override
    public PartyInviteResponse processPartyInvite(PartyInviteRequest request) {
        UUID leader = request.getLeader();
        String targetName = request.getTarget();
        if (OnlinePlayers.getPlayer(targetName).isEmpty()) {
            return new PartyInviteResponse(
                    request.getRequestUuid(),
                    false,
                    "This player is not online!"
            );
        }
        NetworkPlayer targetPlayer = OnlinePlayers.getPlayer(targetName).get();

        if (!PartyManager.isLeader(leader)) {
            return new PartyInviteResponse(
                    request.getRequestUuid(),
                    false,
                    "You are not the leader of the party!"
            );
        }
        Party party = PartyManager.getParty(leader).get();

        if (PartyManager.getParty(targetPlayer.getUuid()).isPresent()) {
            return new PartyInviteResponse(
                    request.getRequestUuid(),
                    false,
                    "This player is already a member of a party!"
            );
        }

        // todo: notify target

        PartyManager.sendInvitation(party, targetPlayer.getUuid());
        return new PartyInviteResponse(
                request.getRequestUuid(),
                true,
                "You have successfully invited "+targetName+" to the party!"
        );
    }

    @Override
    public PartyKickResponse processPartyKick(PartyKickRequest request) {
        UUID leader = request.getLeader();
        String targetName = request.getTarget();
        if (OnlinePlayers.getPlayer(targetName).isEmpty()) {
            return new PartyKickResponse(
                    request.getRequestUuid(),
                    false,
                    "This player is not online!"
            );
        }
        NetworkPlayer targetPlayer = OnlinePlayers.getPlayer(targetName).get();

        if (!PartyManager.isLeader(leader)) {
            return new PartyKickResponse(
                    request.getRequestUuid(),
                    false,
                    "You are not the leader of the party!"
            );
        }
        Party party = PartyManager.getParty(leader).get();
        if (!party.isParticipant(targetPlayer.getUuid())) {
            return new PartyKickResponse(
                    request.getRequestUuid(),
                    false,
                    "This player is not a member of this party!"
            );
        }
        party.removeMember(targetPlayer.getUuid());
        // todo: notify party members
        return new PartyKickResponse(
                request.getRequestUuid(),
                true,
                "You have successfully kicked out "+targetName+" from your party!"
        );
    }


}