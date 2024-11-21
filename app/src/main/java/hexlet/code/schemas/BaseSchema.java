package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    protected final Map<String, Predicate<T>> ruleIdToRule = new HashMap<>();
    protected Predicate<T> requiredRule = data -> ruleIdToRule.isEmpty() || data != null;

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
