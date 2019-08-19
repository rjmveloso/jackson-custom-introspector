package io.github.jackson.introspector;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;

/**
 * This class acts just as an holder for registered sub-types.<br/>
 * It does not perform any logic since it ignores any annotation mechanism.
 * 
 * @author rveloso
 *
 */
public class SimpleSubTypeResolver extends SubtypeResolver {

	protected Set<NamedType> subtypes = new LinkedHashSet<>();

	@Override
	public void registerSubtypes(NamedType... types) {
		for (NamedType type : types) {
			subtypes.add(type);
		}
	}

	@Override
	public void registerSubtypes(Class<?>... classes) {
		registerSubTypes(Stream.of(classes));
	}

	@Override
	public void registerSubtypes(Collection<Class<?>> subtypes) {
		registerSubTypes(subtypes.stream());
	}

	protected void registerSubTypes(Stream<Class<?>> stream) {
		Object[] values = stream.map(NamedType::new).toArray(size -> new NamedType[size]);
		registerSubtypes((NamedType[]) values);
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedClass baseType) {
		return subtypes;
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypesByClass(MapperConfig<?> config, AnnotatedMember property,
			JavaType baseType) {
		return subtypes;
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedClass baseType) {
		return subtypes;
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypesByTypeId(MapperConfig<?> config, AnnotatedMember property,
			JavaType baseType) {
		return subtypes;
	}

}
