package io.github.jackson.introspector;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.TypeDeserializer;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.map.jsontype.impl.StdTypeResolverBuilder;
import org.codehaus.jackson.type.JavaType;

/**
 * 
 * @author rveloso
 *
 */
public class NamedTypeResolverBuilder extends StdTypeResolverBuilder {

	public NamedTypeResolverBuilder() {
		this(Id.NAME, Id.NAME.getDefaultPropertyName());
	}

	public NamedTypeResolverBuilder(Id type, String property) {
		init(type, null).inclusion(As.PROPERTY).typeProperty(property);
	}

	@Override
	public TypeSerializer buildTypeSerializer(SerializationConfig config, JavaType baseType,
			Collection<NamedType> subtypes, BeanProperty property) {
		JavaType assignable = findAssignableNamedType(baseType, subtypes);
		return assignable != null ? super.buildTypeSerializer(config, baseType, subtypes, property) : null;
	}

	@Override
	public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType,
			Collection<NamedType> subtypes, BeanProperty property) {
		JavaType assignable = findAssignableNamedType(baseType, subtypes);
		return assignable != null ? super.buildTypeDeserializer(config, baseType, subtypes, property) : null;
	}

	private JavaType findAssignableNamedType(JavaType baseType, Collection<NamedType> subtypes) {
		if (subtypes != null) {
			for (NamedType type : subtypes) {
				if (baseType.getRawClass().isAssignableFrom(type.getType())) {
					return baseType;
				}
			}
		}
		return null;
	}

}
