package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void testStringSchema() {
        var validator = new Validator();
        var schema = validator.string();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("asfd;ljh"));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("asfd;ljh"));

        schema.minLength(2);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("a"));
        assertTrue(schema.isValid("as"));
        assertTrue(schema.isValid("asfd;ljh"));

        schema.minLength(4);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("a"));
        assertFalse(schema.isValid("as"));
        assertTrue(schema.isValid("asdf"));
        assertTrue(schema.isValid("asfd;ljh"));

        schema.contains("asdf");

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("a"));
        assertTrue(schema.isValid("asdf"));
        assertTrue(schema.isValid("asdf;ljh"));

        schema.contains("");

        assertFalse(schema.isValid("a"));

        schema.contains("hello");

        assertFalse(schema.isValid("asdf"));
        assertTrue(schema.isValid("world, hello!"));
    }

    @Test
    void testChainingForStringSchema() {
        var validator = new Validator();
        var schema = validator
            .string()
            .required()
            .minLength(10)
            .minLength(3)
            .contains("qwer");

        var expectedIsValid = schema.isValid("}-|-{qwerty}-|-{");

        assertTrue(expectedIsValid);
    }

}
