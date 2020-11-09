/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rabbitbbq.exception.Exceptions;

/**
 *
 * @author rizal
 */
public class Beautify {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    
    /**
     * 
     * @param encoded
     * @return beautify string json
     * @throws Exception 
     */
    public String format(String encoded) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper .readTree(encoded);
        return objectMapper.writeValueAsString(tree);
    }
    
    /**
     * encode class
     * @param T
     * @return String
     */
    public String toString(Object T){
        ObjectMapper objectMapper = new ObjectMapper();
        String convert = null;
        try {
            convert = objectMapper.writeValueAsString(T);
        } catch (JsonProcessingException ex) {
            exceptions.log(ex);
        }
        return convert;
    }
    
    /**
     * decode class
     * @param json
     * @param T
     * @return T
     */
    public Object toClass(String json, Class T){
        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = null;
        try {
            obj =  objectMapper.readValue(json, T);
        } catch (JsonProcessingException ex) {
            exceptions.log(ex);
        }
        return obj;
    }
}
