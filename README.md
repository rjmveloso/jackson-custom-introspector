# Jackson Introspector
No Annotation Introspector for inheritance and polymorphism

Jackson introspection mechanism is based on annotations that are tied to the model.  
In order to decouple Jackson from the model this library provides an alternative implementation.  

All the support is based on Jackson components for that matter.  
Providing a custom serializer/deserializer for the same purpose should be avoided.  

**IMPORTANT NOTE:**  
Custom serializer/deserializer factories will not work with a customized ObjectMapper.  
This is due to the immutable behavior of BeanSerializerFactory/BeanDeserializerFactory.  
`@see BeanSerializerFactory#withConfig(org.codehaus.jackson.map.SerializerFactory.Config)`  
`@see BeanDeserializerFactory#withConfig(org.codehaus.jackson.map.DeserializerFactory.Config)`  