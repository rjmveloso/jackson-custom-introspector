package io.github.jackson.introspector;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedMember;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.SubtypeResolver;

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
		NamedType[] types = new NamedType[classes.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = new NamedType(classes[i]);
		}
		registerSubtypes(types);
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember property, MapperConfig<?> config,
			AnnotationIntrospector ai) {
		return subtypes;
	}

	@Override
	public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass basetype, MapperConfig<?> config,
			AnnotationIntrospector ai) {
		return subtypes;
	}

}
