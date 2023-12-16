package eu.networkmanager.common.party;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    private final UUID uuid;
    private UUID leader;
    private final List<UUID> members;

    public Party(UUID leader) {
        this.uuid=UUID.randomUUID();
        this.leader=leader;
        this.members=new ArrayList<>();
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID uuid) {
        this.leader=uuid;
    }

    public List<UUID> getParticipants() {
        List<UUID> participants = new ArrayList<>(members);
        participants.add(leader);
        return participants;
    }

    public boolean isParticipant(UUID uuid) {
        return this.getParticipants().contains(uuid);
    }

    public boolean isLeader(UUID uuid) {
        return this.leader.equals(uuid);
    }

    public void addMember(UUID uuid) {
        this.members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        this.members.remove(uuid);
    }

}
