package io.github.jackson.introspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedMember;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.SubtypeResolver;
import org.junit.jupiter.api.Test;

class SimpleSubTypeResolverTest {

	@Test
	void testNoRegisteredSubTypes() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();

		assertNotNull(resolver.collectAndResolveSubtypes((AnnotatedClass) null, null, null));
		assertNotNull(resolver.collectAndResolveSubtypes((AnnotatedMember) null, null, null));
		assertEquals(0, resolver.collectAndResolveSubtypes((AnnotatedMember) null, null, null).size());
	}

	@Test
	void testValidRegisteredSubTypesByClass() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();
		resolver.registerSubtypes(SimpleSubTypeResolverTest.class);
		Collection<NamedType> result = resolver.collectAndResolveSubtypes((AnnotatedClass) null, null, null);

		assertEquals(1, result.size());
		assertEquals(SimpleSubTypeResolverTest.class, result.iterator().next().getType());
	}

	@Test
	void testValidRegisteredSubTypesByNamedType() {
		SubtypeResolver resolver = new SimpleSubTypeResolver();
		resolver.registerSubtypes(new NamedType(SimpleSubTypeResolverTest.class));
		Collection<NamedType> result = resolver.collectAndResolveSubtypes((AnnotatedClass) null, null, null);

		assertEquals(1, result.size());
		assertEquals(SimpleSubTypeResolverTest.class, result.iterator().next().getType());
	}

}
