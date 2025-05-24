package com.chugs.chugs.converter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if (joined == null || joined.isEmpty()) return Collections.emptyList();
        return Arrays.stream(joined.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
