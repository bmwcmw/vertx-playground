package playground.repository;

import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommunicationRepository {
    public JsonObject getResult() {
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("status", "OK");
        }};
        return new JsonObject(resultMap);
    }
}
