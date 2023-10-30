package org.example;

import io.vertx.json.schema.Draft;
import io.vertx.json.schema.JsonSchemaValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.VertxMetaSchemaValidation.readAllLines;


public class ValidatorTest {
    @Test
    public void testRepoValidationDraft4() {
        System.out.println("Repo validation draft 4");
        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    VertxMetaSchemaValidation.validateSchemaViaRepo(readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                        Draft.DRAFT4);
                }
        );
    }

    @Test
    public void testRepoValidationDraft7() {
        System.out.println("Repo validation draft 4");
        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    VertxMetaSchemaValidation.validateSchemaViaRepo(readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                            Draft.DRAFT7);
                }
        );
    }

    @Test
    public void testRepoValidationDraft202012() {
        System.out.println("Repo validation draft 2020-12");
        // Does not fail with JsonSchema Validation error
        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    VertxMetaSchemaValidation.validateSchemaViaRepo(readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                            Draft.DRAFT202012);
                }
        );
    }

    @Test
    public void testStandaloneValidationDraft4() {
        System.out.println("Standalone validation draft 4");
        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    VertxMetaSchemaValidation.validateSchemaViaStandalone(
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/draft4-meta-schema.json")), Draft.DRAFT4);
                }
        );
    }

    @Test
    public void testStandaloneValidationDraft7() {
        System.out.println("Standalone validation draft 7");
        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    VertxMetaSchemaValidation.validateSchemaViaStandalone(
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/draft7-meta-schema.json")), Draft.DRAFT7);
                }
        );
    }

    @Test
    public void testStandaloneValidationDraft202012() {
        System.out.println("Standalone validation draft 2020-12");

        Assertions.assertThrows(JsonSchemaValidationException.class,
                () -> {
                    // This fails with SchemaException{message='Unresolved $ref meta/core
                    // possibly due to external references, but not due to the actual schema issue
                    VertxMetaSchemaValidation.validateSchemaViaStandalone(
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/derived-with-constraints-schema.json")),
                            readAllLines(VertxMetaSchemaValidation.class.getResourceAsStream("/2020-12-meta-schema.json")), Draft.DRAFT202012);
                }
        );
    }
}
