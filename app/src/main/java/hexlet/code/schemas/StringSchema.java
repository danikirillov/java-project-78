package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        requiredRule = text -> text != null && !text.isBlank();
        return this;
    }

    public StringSchema minLength(int minLength) {
        ruleIdToRule.put("minLength", text -> text.length() >= minLength);
        return this;
    }

    public StringSchema contains(String piece) {
        ruleIdToRule.put("contains", text -> text.contains(piece));
        return this;
    }
}
