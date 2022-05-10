package com.douyasi.tutorial.blog.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * ObjectUtil
 *
 * @author raoyc
 */
public class ObjectUtil {

    /**
     * transObjectToJson
     * using jackson lib, the result property/field will trans from lowerCamelCase to snake_case
     *
     * @param object serializable object
     * @return json string
     * @throws JsonProcessingException
     */
     public static String transObjectToJson(Object object) throws JsonProcessingException {
         ObjectMapper mapper = getCustomizedJacksonObjectMapper();
         String jsonInString = null;
         jsonInString = mapper.writeValueAsString(object);
         return jsonInString;
     }

    /**
     * getCustomizedJacksonObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getCustomizedJacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }
}
