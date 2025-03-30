package com.connectbundle.connect.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        

        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT) ; // Use STANDARD for more flexibility; // Prevent merging collections

        modelMapper.addConverter(new Converter<Collection<?>, List<?>>() {
            @Override
            public List<?> convert(MappingContext<Collection<?>, List<?>> context) {
                Collection<?> source = context.getSource();
                return source == null ? null : new ArrayList<>(source);
            }
        });


        return modelMapper;
    }
}
