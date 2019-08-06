package io.github.jackson.introspector;

import java.util.Objects;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedMember;
import org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;
import org.codehaus.jackson.map.jsontype.TypeResolverBuilder;
import org.codehaus.jackson.type.JavaType;

/**
 * Introspection mechanism that delegates all the logic to a TypeResolverBuilder.<br/>
 * Performs no action based on annotations and returns the defined resolver.
 * 
 * @author rveloso
 *
 */
public class DelegateTypeResolverIntrospector extends NopAnnotationIntrospector {

	private TypeResolverBuilder<?> resolver = new NamedTypeResolverBuilder();

	public DelegateTypeResolverIntrospector() {
	}

	/**
	 * Delegate to DelegateTypeResolverBuilder with the defined property name
	 * 
	 * @param property type property name
	 */
	public DelegateTypeResolverIntrospector(String property) {
		resolver.typeProperty(property);
	}

	/**
	 * Set a custom TypeResolverBuilder
	 * 
	 * @param resolver
	 */
	public DelegateTypeResolverIntrospector(TypeResolverBuilder<?> resolver) {
		Objects.requireNonNull(resolver);
		this.resolver = resolver;
	}

	@Override
	public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
		// No need to provide a type resolver builder for a class level type
		return null;
	}

	@Override
	public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
		return resolver;
	}

	@Override
	public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
		return resolver;
	}

}
