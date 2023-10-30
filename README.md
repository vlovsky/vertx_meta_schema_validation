# Vertx metaschema validation

## Usage
Run all tests. Supposedly they all should pass.

## Cases
There are 3 tests to test an invalid schema against draft 4, 7 and 2020-12 using Repository

```java
repository =
        SchemaRepository.create(new JsonSchemaOptions().setBaseUri("https://example.com").setDraft(Draft.DRAFT7));
repository.preloadMetaSchema(fileSystem);
var schema = repository.dereference(JsonSchema.of(mySchema)).
    find("https://example.com/canonical-with-constraints");
```

There are 3 tests to test an invalid schema against draft 4, 7 and 2020-12 using Standalone

```java
var schema = JsonSchema.of(mySchema);
var metaSchema = JsonSchema.of(metaSchema);

OutputUnit result = Validator.create(
                metaSchema,
                new JsonSchemaOptions().setDraft(draft).setBaseUri("https://json-schema.org"))
        .validate(schema);
if (!result.getValid()) {
        result.checkValidity();
}
```
