package io.github.jackson.introspector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import org.junit.jupiter.api.Test;

class DelegateTypeResolverIntrospectorTest {

	@Test
	void testDefaultTypeResolverBuilder() {
		DelegateTypeResolverIntrospector introspector = new DelegateTypeResolverIntrospector();

		assertNull(introspector.findTypeResolver(null, null, null));
		assertSame(NamedTypeResolverBuilder.class, introspector.findPropertyTypeResolver(null, null, null).getClass());
		assertSame(NamedTypeResolverBuilder.class, introspector.findPropertyContentTypeResolver(null, null, null).getClass());
	}

	@Test
	void testCustomPropertyName() {
		DelegateTypeResolverIntrospector introspector = new DelegateTypeResolverIntrospector("custom");
		TypeResolverBuilder<?> resolver = introspector.findPropertyTypeResolver(null, null, null);

		assertNotNull(resolver);
		assertEquals("custom", ((NamedTypeResolverBuilder) resolver).getTypeProperty());
	}

	@Test
	void testInvalidTypeResolverBuilder() {
		assertThrows(NullPointerException.class, () -> {
			new DelegateTypeResolverIntrospector((TypeResolverBuilder<?>) null);
		});
	}

}
