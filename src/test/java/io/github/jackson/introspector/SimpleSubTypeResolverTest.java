package io.github.jackson.introspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;

class SimpleSubTypeResolverTest {

	@Test
	void testNoRegisteredSubTypes() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();

		assertNotNull(resolver.collectAndResolveSubtypesByClass(null, null));
		assertNotNull(resolver.collectAndResolveSubtypesByClass(null, null, null));
		assertNotNull(resolver.collectAndResolveSubtypesByTypeId(null, null));
		assertNotNull(resolver.collectAndResolveSubtypesByTypeId(null, null, null));
		assertEquals(0, resolver.collectAndResolveSubtypesByClass(null, null).size());
	}

	@Test
	void testValidRegisteredSubTypesByClass() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();
		resolver.registerSubtypes(SimpleSubTypeResolverTest.class);
		Collection<NamedType> result = resolver.collectAndResolveSubtypesByClass(null, null);

		assertEquals(1, result.size());
		assertEquals(SimpleSubTypeResolverTest.class, result.iterator().next().getType());
	}

	@Test
	void testValidRegisteredSubTypesByNamedType() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();
		resolver.registerSubtypes(new NamedType(SimpleSubTypeResolverTest.class));
		Collection<NamedType> result = resolver.collectAndResolveSubtypesByClass(null, null);

		assertEquals(1, result.size());
		assertEquals(SimpleSubTypeResolverTest.class, result.iterator().next().getType());
	}

}
