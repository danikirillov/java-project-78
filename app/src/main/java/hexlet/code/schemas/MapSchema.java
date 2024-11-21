package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        requiredRule = Objects::nonNull;
        return this;
    }

    public MapSchema sizeof(int size) {
        ruleIdToRule.put("sizeof", map -> map.size() == size);
        return this;
    }
}
