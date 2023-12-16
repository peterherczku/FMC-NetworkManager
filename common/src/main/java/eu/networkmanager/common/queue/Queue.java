package eu.networkmanager.common.queue;

import eu.networkmanager.common.enums.MinigameType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Queue {

    private final UUID id;
    private final MinigameType type;
    private final List<UUID> members;
    private final Integer maxPlayers;

    public Queue(MinigameType type) {
        this.id=UUID.randomUUID();
        this.type=type;
        this.members=new ArrayList<>();
        this.maxPlayers=type.getMaxPlayers();
    }

    public UUID getId() {
        return id;
    }

    public MinigameType getType() {
        return type;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID uuid) {
        this.members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        this.members.remove(uuid);
    }

    public boolean hasMember(UUID uuid) {
        return this.members.contains(uuid);
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }
}
