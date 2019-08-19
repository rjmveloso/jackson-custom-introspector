package io.github.jackson.introspector;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

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
			Collection<NamedType> subtypes) {
		JavaType assignable = findAssignableNamedType(baseType, subtypes);
		return assignable != null ? super.buildTypeSerializer(config, baseType, subtypes) : null;
	}

	@Override
	public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType,
			Collection<NamedType> subtypes) {
		JavaType assignable = findAssignableNamedType(baseType, subtypes);
		return assignable != null ? super.buildTypeDeserializer(config, baseType, subtypes) : null;
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
