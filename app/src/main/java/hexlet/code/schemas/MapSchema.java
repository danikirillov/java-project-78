package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        requiredRule = Objects::nonNull;
        return this;
    }

    public MapSchema sizeof(int size) {
        ruleIdToRule.put("sizeof", map -> map.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> keyNameToSchema) {
        ruleIdToRule.put("shape", map -> {
            var isValid = true;
            var entries = keyNameToSchema.entrySet();
            for (var entry : entries) {
                if (!isValid) {
                    break;
                }
                var rule = (String) map.get(entry.getKey());
                isValid = entry.getValue().isValid(rule);
            }
            return isValid;
        });
        return this;
    }
}
