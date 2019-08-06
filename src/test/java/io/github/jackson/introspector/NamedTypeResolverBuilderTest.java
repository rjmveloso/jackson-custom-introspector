package io.github.jackson.introspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.TypeDeserializer;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeDeserializer;
import org.codehaus.jackson.map.jsontype.impl.AsPropertyTypeSerializer;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.junit.jupiter.api.Test;

class NamedTypeResolverBuilderTest {

	private static final JavaType BASE_TYPE = SimpleType.construct(NamedTypeResolverBuilder.class);
	private static final List<NamedType> SUB_TYPES = Arrays.asList(new NamedType(NamedTypeResolverBuilder.class));

	@Test
	void testNullSubtypesProvided() {
		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(null, null, Collections.emptyList(), null);
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(null, null, Collections.emptyList(), null);

		assertNull(serializer);
		assertNull(deserializer);
	}

	@Test
	void testEmptySubtypesProvided() {
		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(null, null, Collections.emptyList(), null);
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(null, null, Collections.emptyList(), null);

		assertNull(serializer);
		assertNull(deserializer);
	}

	@Test
	void testValidTypeSerializer() {
		SerializationConfig config = new SerializationConfig(null, null, null, null, null, null, null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(config, BASE_TYPE, SUB_TYPES, null);

		assertNotNull(serializer);
		assertSame(AsPropertyTypeSerializer.class, serializer.getClass());
	}

	@Test
	void testValidTypeDeserializer() {
		DeserializationConfig config = new DeserializationConfig(
				null, null, null, null, null, TypeFactory.defaultInstance(), null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(config, BASE_TYPE, SUB_TYPES, null);

		assertNotNull(deserializer);
		assertSame(AsPropertyTypeDeserializer.class, deserializer.getClass());
	}

	@Test
	void testCustomTypeIdResolver() {
		SerializationConfig config = new SerializationConfig(null, null, null, null, null, null, null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder(Id.CLASS, "custom");
		TypeSerializer serializer = resolver.buildTypeSerializer(config, BASE_TYPE, SUB_TYPES, null);

		assertNotNull(serializer);
		assertEquals(Id.CLASS, serializer.getTypeIdResolver().getMechanism());
		assertEquals(As.PROPERTY, serializer.getTypeInclusion());
		assertEquals("custom", serializer.getPropertyName());
	}

}
