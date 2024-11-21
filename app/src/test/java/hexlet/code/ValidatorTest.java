package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    void testCornerCasesStringSchema() {
        var validator = new Validator();
        var containsEmptySchema = validator.string().contains("");

        assertTrue(containsEmptySchema.isValid(""));
//        todo: determine this
        assertFalse(containsEmptySchema.isValid(null));

        var negativeMinLengthSchema = validator.string().minLength(-1);

        assertTrue(negativeMinLengthSchema.isValid(""));
//        todo: determine this
        assertFalse(negativeMinLengthSchema.isValid(null));

    }

    @Test
    void testNumberSchema() {
        var validator = new Validator();
        var schema = validator.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(-1));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(1));
        assertTrue(schema.isValid(-1));

        schema.positive();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(1));
        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(0));

        schema.range(2, 4);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-1));
        assertTrue(schema.isValid(2));
        assertTrue(schema.isValid(3));
        assertTrue(schema.isValid(4));
        assertFalse(schema.isValid(5));

        schema.range(10, -10);

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(2));
        assertFalse(schema.isValid(3));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(5));
        assertFalse(schema.isValid(10));
        assertFalse(schema.isValid(-10));
    }

    @Test
    void testMapSchema() {
        var v = new Validator();
        var schema = v.map();

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        schema.sizeof(2);

        assertFalse(schema.isValid(data));
        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }

    @Test
    void testMapSchemaShape() {
        var v = new Validator();
        var schema = v.map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));
    }
}
