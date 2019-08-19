package io.github.jackson.introspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

class NamedTypeResolverBuilderTest {

	private static final JavaType BASE_TYPE = TypeFactory.defaultInstance()
			.constructSimpleType(NamedTypeResolverBuilder.class, null);

	private static final List<NamedType> SUB_TYPES = Arrays.asList(new NamedType(NamedTypeResolverBuilder.class));

	private static final BaseSettings SETTINGS = new BaseSettings(null, null, null, TypeFactory.defaultInstance(), null,
			null, null, null, null, null);

	@Test
	void testNullSubtypesProvided() {
		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(null, null, Collections.emptyList());
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(null, null, Collections.emptyList());

		assertNull(serializer);
		assertNull(deserializer);
	}

	@Test
	void testEmptySubtypesProvided() {
		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(null, null, Collections.emptyList());
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(null, null, Collections.emptyList());

		assertNull(serializer);
		assertNull(deserializer);
	}

	@Test
	void testValidTypeSerializer() {
		SerializationConfig config = new SerializationConfig(SETTINGS, null, null, null, null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeSerializer serializer = resolver.buildTypeSerializer(config, BASE_TYPE, SUB_TYPES);

		assertNotNull(serializer);
		assertSame(AsPropertyTypeSerializer.class, serializer.getClass());
	}

	@Test
	void testValidTypeDeserializer() {
		DeserializationConfig config = new DeserializationConfig(SETTINGS, null, null, null, null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();
		TypeDeserializer deserializer = resolver.buildTypeDeserializer(config, BASE_TYPE, SUB_TYPES);

		assertNotNull(deserializer);
		assertSame(AsPropertyTypeDeserializer.class, deserializer.getClass());
	}

	@Test
	void testCustomTypeIdResolver() {
		SerializationConfig config = new SerializationConfig(SETTINGS, null, null, null, null);

		TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder(Id.CLASS, "custom");
		TypeSerializer serializer = resolver.buildTypeSerializer(config, BASE_TYPE, SUB_TYPES);

		assertNotNull(serializer);
		assertEquals(Id.CLASS, serializer.getTypeIdResolver().getMechanism());
		assertEquals(As.PROPERTY, serializer.getTypeInclusion());
		assertEquals("custom", serializer.getPropertyName());
	}

}
