package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema() {
        requiredRule = data -> true;
    }

    public NumberSchema required() {
        requiredRule = Objects::nonNull;
        return this;
    }

    public NumberSchema positive() {
        ruleIdToRule.put("positive", number -> number == null || number > 0);
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        ruleIdToRule.put("range", number -> from <= number && number <= to);
        return this;
    }
}
