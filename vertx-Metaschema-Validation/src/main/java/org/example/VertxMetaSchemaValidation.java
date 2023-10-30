package org.example;

import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VertxMetaSchemaValidation {

    public static void validateSchemaViaRepo(List<String> schemaLines, Draft draft) throws DecodeException, IOException, JsonSchemaValidationException {
        SchemaRepository repository =
                SchemaRepository.create(new JsonSchemaOptions().setBaseUri("https://example.com").setDraft(draft));
        FileSystem fileSystem = Vertx.vertx().fileSystem();
        repository.preloadMetaSchema(fileSystem);
        JsonSchema schema = repository.dereference(JsonSchema.of(new JsonObject(String.join("\n", schemaLines)))).
                find("https://example.com/canonical-with-constraints");
        System.out.println(schema.fieldNames());
   }

    public static void validateSchemaViaStandalone(List<String> schemaLines, List<String> metaSchemaLines, Draft draft) throws DecodeException, IOException, JsonSchemaValidationException {
        var schema = JsonSchema.of(new JsonObject(String.join("\n", schemaLines)));
        var metaSchema = JsonSchema.of(new JsonObject(String.join("\n", metaSchemaLines)));

        // validate against JSON draft
        try {
            OutputUnit result = Validator.create(
                            metaSchema,
                            new JsonSchemaOptions().setDraft(draft).setBaseUri("https://json-schema.org"))
                    .validate(schema);
            if (!result.getValid()) {
                result.checkValidity();
            }
        } catch (JsonSchemaValidationException e) {
            throw new JsonSchemaValidationException("Schema is invalid", e, "");
        }
    }

    public static List<String> readAllLines(InputStream resource)  {
        var oldFile = new BufferedReader(new InputStreamReader(resource));
        var list = new ArrayList<String>();

        String line;

        try {
            while ((line = oldFile.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}