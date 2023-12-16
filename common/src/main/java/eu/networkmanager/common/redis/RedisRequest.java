package eu.networkmanager.common.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.networkmanager.common.utils.GsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class RedisRequest<T extends RedisResponse> {

    private UUID requestUuid;
    private String requestName;

    private String requestClassName;
    private List<String> requestGenericClassesName;

    public RedisRequest(String requestName) {
        this.requestUuid=UUID.randomUUID();
        this.requestName=requestName;
    }

    public RedisRequest(String requestName, String requestClassName, List<String> requestGenericClassesName) {
        this.requestUuid=UUID.randomUUID();
        this.requestName=requestName;
        this.requestClassName=requestClassName;
        this.requestGenericClassesName=requestGenericClassesName;
    }

    public JSONObject writeRequest() {
        return new JSONObject(GsonUtil.INSTANCE.toJson(this));
    }

    public void readRequest(JSONObject jsonObject) {
        this.requestClassName = jsonObject.getString("requestClassName");
        this.requestGenericClassesName = new ArrayList<>();
        for (Object genericClassesName : jsonObject.getJSONArray("requestGenericClassesName")) {
            this.requestGenericClassesName.add((String) genericClassesName);
        }

        Class<?> requestClass;
        List<Class<?>> requestGenericClasses;
        try {
            requestClass = Class.forName(requestClassName);
            requestGenericClasses = requestGenericClassesName.stream().map(className -> {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TypeToken<?> typeToken = TypeToken.getParameterized(requestClass, requestGenericClasses.toArray(new Class[0]));
        RedisRequest<T> request = (RedisRequest<T>) GsonUtil.INSTANCE.fromJson(jsonObject.toString(), typeToken);
        this.requestUuid=request.getRequestUuid();
        this.requestClassName=request.getRequestClassName();
        this.requestGenericClassesName=request.getRequestGenericClassesName();
        loadRequestData(request);
    }

    public abstract void loadRequestData(RedisRequest<T> request);

    public abstract T processRequest(RedisRequestListener listener);

    public UUID getRequestUuid() {
        return requestUuid;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public List<String> getRequestGenericClassesName() {
        return requestGenericClassesName;
    }
}
