package eu.networkmanager.server.managers;

import eu.networkmanager.common.enums.MinigameType;
import eu.networkmanager.common.queue.Queue;

import java.util.*;

public class QueueManager {

    private static Map<UUID, Queue> queues = new HashMap<>();

    public static void addMember(MinigameType type, UUID player) {
        List<Queue> filteredQueues = new ArrayList<>(queues.values().stream().filter(queue -> queue.getType() == type && queue.getMembers().size() < queue.getMaxPlayers()).toList());
        filteredQueues.sort(Comparator.comparing(q -> q.getMembers().size()));
        if (filteredQueues.isEmpty()) {
            Queue queue = new Queue(type);
            queue.addMember(player);
            return;
        }

        Queue queue = filteredQueues.get(0);
        queue.addMember(player);
    }

    public static void removeMember(UUID player) {
        if (!getQueue(player).isPresent()) return;
        Queue queue = getQueue(player).get();
        queue.removeMember(player);
    }

    public static Optional<Queue> getQueue(UUID player) {
        return queues.values().stream().filter(queue -> queue.hasMember(player)).findAny();
    }

}
