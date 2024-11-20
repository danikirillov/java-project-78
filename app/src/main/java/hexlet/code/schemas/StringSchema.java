package hexlet.code.schemas;

public class StringSchema {
    private boolean isRequired = false;
    private int minLength = -1;
    private String textForContainsCheck = null;

    public StringSchema required() {
        isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        isRequired = true;
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String piece) {
        isRequired = true;
        this.textForContainsCheck = piece;
        return this;
    }

    public boolean isValid(String text) {
        var isValid = true;

        if (isRequired) {
            isValid = text != null && !text.isBlank();
        }

        if (isValid && minLength > 0) {
            isValid = text.length() >= minLength;
        }

        if (isValid && textForContainsCheck != null) {
            isValid = text.contains(textForContainsCheck);
        }

        return isValid;
    }
}
