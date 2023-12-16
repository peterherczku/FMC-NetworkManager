package eu.networkmanager.common.redis;

import com.google.gson.reflect.TypeToken;
import eu.networkmanager.common.utils.GsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class RedisResponse {

    private UUID requestUuid;
    private String requestName;
    private final boolean response = true;

    private String responseClassName;
    private List<String> responseGenericClassesName;

    public RedisResponse(UUID requestUuid, String requestName, String responseClassName, List<String> responseGenericClassesName) {
        this.requestUuid=requestUuid;
        this.requestName=requestName;
        this.responseClassName=responseClassName;
        this.responseGenericClassesName=responseGenericClassesName;
    }

    public RedisResponse(String requestName) {
        this.requestName=requestName;
    }

    public JSONObject writeResponse() {
        return new JSONObject(GsonUtil.INSTANCE.toJson(this));
    }

    public void readResponse(JSONObject jsonObject) {
        this.responseClassName = jsonObject.getString("responseClassName");
        this.responseGenericClassesName = new ArrayList<>();
        for (Object genericClassesName : jsonObject.getJSONArray("responseGenericClassesName")) {
            this.responseGenericClassesName.add((String) genericClassesName);
        }

        Class<?> responseClass;
        List<Class<?>> responseGenericClasses;
        try {
            responseClass = Class.forName(responseClassName);
            responseGenericClasses = responseGenericClassesName.stream().map(className -> {
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TypeToken<?> typeToken = TypeToken.getParameterized(responseClass, responseGenericClasses.toArray(new Class[0]));
        RedisResponse response = (RedisResponse) GsonUtil.INSTANCE.fromJson(jsonObject.toString(), typeToken);
        this.requestUuid=response.getRequestUuid();
        this.requestName=response.getRequestName();
        this.responseClassName=response.getResponseClassName();
        this.responseGenericClassesName=response.getResponseGenericClassesName();
        loadResponseData(response);
    }

    public abstract void loadResponseData(RedisResponse response);

    public UUID getRequestUuid() {
        return this.requestUuid;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public List<String> getResponseGenericClassesName() {
        return responseGenericClassesName;
    }
}
