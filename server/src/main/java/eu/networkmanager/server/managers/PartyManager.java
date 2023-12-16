package eu.networkmanager.server.managers;

import eu.networkmanager.common.party.Party;

import java.util.*;

public class PartyManager {

    private static List<Party> parties = new ArrayList<>();
    private static Map<UUID, Party> pendingInvites = new HashMap<>();

    public static Party createParty(UUID leader) {
        Party party = new Party(leader);
        parties.add(party);
        return party;
    }

    public static void deleteParty(Party party) {
        parties.remove(party);
    }

    public static Optional<Party> getParty(UUID uuid) {
        return parties.stream().filter(party -> party.isParticipant(uuid)).findAny();
    }

    public static boolean isLeader(UUID uuid) {
        return parties.stream().anyMatch(party -> party.isLeader(uuid));
    }

    public static void sendInvitation(Party party, UUID target) {
        if (pendingInvites.containsKey(target)) {
            pendingInvites.replace(target, party);
            return;
        }

        pendingInvites.put(target, party);
    }

    public static void removeInvitation(UUID player) {
        pendingInvites.remove(player);
    }

    public static Party getPendingInvitation(UUID player) {
        if (!pendingInvites.containsKey(player)) return null;
        return pendingInvites.get(player);
    }

}
