package hexlet.code.schemas;

import java.util.Objects;

public class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        requiredRule = Objects::nonNull;
        return this;
    }

    public NumberSchema positive() {
        ruleIdToRule.put("positive", number -> number > 0);
        return this;
    }

    public NumberSchema range(Integer from, Integer to) {
        ruleIdToRule.put("range", number -> from <= number && number <= to);
        return this;
    }
}
