package eu.networkmanager.common.redis;

import eu.networkmanager.common.server.Server;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class RedisClient {

    private Server server;
    private String channelName;
    private ConcurrentMap<String, RedisResponse> respones = new ConcurrentHashMap<>();
    private ConcurrentMap<UUID, Consumer<RedisResponse>> callbacks = new ConcurrentHashMap<>();

    public RedisClient(Server server, String channelName) {
        this.server=server;
        this.channelName=channelName;
    }

    public <T extends RedisResponse> T send(RedisRequest request) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        callbacks.put(request.getRequestUuid(), (redisResponse -> {
            if (callbacks.containsKey(request.getRequestUuid())) completableFuture.complete((T) redisResponse);
        }));

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            if (callbacks.containsKey(request.getRequestUuid())) {
                completableFuture.complete(null);
                callbacks.remove(request.getRequestUuid());
            }
        }, 10, TimeUnit.SECONDS);

        JSONObject jsonObject = request.writeRequest();
        Jedis jedis = new Jedis();
        jedis.publish(channelName, jsonObject.toString());
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends RedisResponse> T send(Server server, RedisRequest request) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        callbacks.put(request.getRequestUuid(), (redisResponse -> {
            if (callbacks.containsKey(request.getRequestUuid())) completableFuture.complete((T) redisResponse);
        }));

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            if (callbacks.containsKey(request.getRequestUuid())) {
                completableFuture.complete(null);
                callbacks.remove(request.getRequestUuid());
            }
        }, 10, TimeUnit.SECONDS);

        JSONObject jsonObject = request.writeRequest();
        Jedis jedis = new Jedis();
        jedis.publish(channelName, jsonObject.toString());
        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(RedisResponse response) {
        this.respones.put(response.getRequestName(), response);
    }

    public void init() {
        System.out.println("(Client) Connecting to channel...");
        listenToResponses();
        try {
            System.out.println("Sleeping 1000ms");
            Thread.sleep(1000);
            System.out.println("Client initialized.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenToResponses() {
        Thread thread = new Thread(() -> {
            Jedis jedis = new Jedis();
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    JSONObject jsonObject = new JSONObject(message);
                    if (!jsonObject.has("response")) return;
                    if (!jsonObject.getBoolean("response")) return;
                    if (!jsonObject.has("requestUuid")) return;
                    UUID requestUuid = UUID.fromString(jsonObject.getString("requestUuid"));
                    if (!callbacks.containsKey(requestUuid)) return;

                    RedisResponse response = respones.get(jsonObject.getString("requestName"));
                    response.readResponse(jsonObject);
                    callbacks.get(requestUuid).accept(response);
                    callbacks.remove(requestUuid);
                }
            }, channelName);
        });
        thread.start();
    }

}
