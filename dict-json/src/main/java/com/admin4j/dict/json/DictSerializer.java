package com.admin4j.dict.json;

import com.admin4j.dict.anno.Dict;
import com.admin4j.dict.core.DictProviderManager;
import com.admin4j.dict.json.anno.DictJson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * - 和 @JsonProperty 不兼容
 * - 序列化字段名称不能改 后期打算使用 BeanPropertyWriter 解决
 *
 * @author andanyang
 * @since 2022/8/10 15:45
 */
@RequiredArgsConstructor
public class DictSerializer extends JsonSerializer<Object> implements ContextualSerializer {
    private final DictProviderManager dictProviderManager;
    private Dict dict;
    private DictJson dictJson;
    private BeanProperty property;
    private Field field; // 当前字段在对象中的Field


    /**
     * ## createContextual
     * - createContextual 可以获得字段的类型以及注解。
     * - createContextual 方法只会在第一次序列化字段时调用（因为字段的上下文信息在运行期不会改变），所以不用担心影响性能。
     * - 代码中逻辑可编写为，当字段为Duoble类型并且拥有@DoubleFormat注解时，就取出注解中的value值，并创建定制的DoubleFormatSerialize，这样在serialize方法中便可以得到这个value值了。
     *
     * @param prov     Serializer provider to use for accessing config, other serializers
     * @param property Method or field that represents the property
     *                 (and is used to access value to serialize).
     *                 Should be available; but there may be cases where caller cannot provide it and
     *                 null is passed instead (in which case impls usually pass 'this' serializer as is)
     * @return
     * @throws JsonMappingException
     */
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Dict annotation = property.getAnnotation(Dict.class);

        this.property = property;

        if (Objects.nonNull(annotation)) {
            this.dict = annotation;
            dictJson = property.getAnnotation(DictJson.class);
            Class<?> handledType = property.getMember().getDeclaringClass();
            try {
                field = handledType.getDeclaredField(property.getName());
            } catch (NoSuchFieldException e) {
                return prov.findValueSerializer(property.getType(), property);
            }

            return this;
        } else {
            return prov.findValueSerializer(property.getType(), property);
        }
    }

    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     *                    serializing Objects value contains, if any.
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {


        String label = dictProviderManager.dictLabel(field, dict.dictStrategy(), dict.dictType(), value);
        // gen.writeFieldName();
        if (dictJson == null || Objects.equals(dictJson.fieldName(), "")) {
            gen.writeString(label);
        } else {

            JsonSerializer<Object> contentValueSerializer = serializers.findContentValueSerializer(value.getClass(), property);
            contentValueSerializer.serialize(value, gen, serializers);

            gen.writeStringField(dictJson.fieldName(), label);
        }
    }
}
