package com.example.demo.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, T> T map(D source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <D, T> List<T> mapAll(List<D> source, Class<T> targetClass) {
        return source.stream().map(obj -> map(obj, targetClass)).toList();
    }
}
