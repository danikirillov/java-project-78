package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    protected final Map<String, Predicate<T>> ruleIdToRule = new HashMap<>();
    protected Predicate<T> requiredRule = null;

    public boolean isValid(T data) {
        var isValid = true;
        if (requiredRule != null) {
            isValid = requiredRule.test(data);
        } else {
            if (!ruleIdToRule.isEmpty()) {
                isValid = data != null;
            }
        }

        for (var rule : ruleIdToRule.values()) {
            if (!isValid) {
                break;
            }

            isValid = rule.test(data);
        }
        return isValid;
    }
}
