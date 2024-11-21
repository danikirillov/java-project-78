package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public sealed class BaseSchema<T> permits MapSchema, NumberSchema, StringSchema {
    protected final Map<String, Predicate<T>> ruleIdToRule = new HashMap<>();
    protected Predicate<T> requiredRule = data -> ruleIdToRule.isEmpty() || data != null;

    /**
     * Validates an object using rules from the ruleIdToRule map.
     *
     * @param data object to validate
     * @return true if all checks from the preconfigured schema passed
     */
    public boolean isValid(T data) {
        var isValid = requiredRule.test(data);

        for (var rule : ruleIdToRule.values()) {
            if (!isValid) {
                break;
            }

            isValid = rule.test(data);
        }
        return isValid;
    }
}
