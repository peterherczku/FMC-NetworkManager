package eu.networkmanager.common.redis;

import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisServer {

    private String channelName;
    private ConcurrentMap<String, RedisRequest> requests = new ConcurrentHashMap<>();
    private RedisRequestListener listener;

    public RedisServer(String channelName, RedisRequestListener listener) {
        this.channelName=channelName;
        this.listener=listener;
    }

    public void register(RedisRequest request) {
        this.requests.put(request.getRequestName(), request);
    }

    public void init() {
        System.out.println("(Server) Connecting to channel...");
        listenToRequests();
        try {
            System.out.println("Sleeping 1000ms");
            Thread.sleep(1000);
            System.out.println("Server initialized.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleIncomingRequest(RedisRequest request) {
        RedisResponse response = request.processRequest(listener);
        Jedis jedis = new Jedis();
        jedis.publish(channelName, response.writeResponse().toString());
    }

    public void listenToRequests() {
        Thread thread = new Thread(() -> {
            Jedis jedis = new Jedis();
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    JSONObject jsonObject = new JSONObject(message);
                    if (jsonObject.has("response")) return;
                    if (!jsonObject.has("requestName")) return;
                    if (!requests.containsKey(jsonObject.getString("requestName"))) return;

                    System.out.println(jsonObject.toString());
                    RedisRequest request = requests.get(jsonObject.getString("requestName"));
                    request.readRequest(jsonObject);
                    handleIncomingRequest(request);
                }
            }, channelName);
        });
        thread.start();
    }

}
