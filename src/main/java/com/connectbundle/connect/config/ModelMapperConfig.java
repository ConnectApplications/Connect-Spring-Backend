package com.connectbundle.connect.config;

import com.connectbundle.connect.dto.EventsDTO.EventResponseDTO;
import com.connectbundle.connect.model.Event;
import org.hibernate.collection.spi.PersistentBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Basic configuration
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STANDARD)  // Use STANDARD for more flexibility
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)  // Ignore ambiguity in property mapping
                .setCollectionsMergeEnabled(false); // Prevent merging collections
        
        // Register a custom converter for PersistentCollection to List
        // This handles all Hibernate collection types (PersistentBag, PersistentSet, etc.)
        Converter<PersistentCollection, List<?>> persistentCollectionConverter = new AbstractConverter<PersistentCollection, List<?>>() {
            @Override
            protected List<?> convert(PersistentCollection source) {
                if (source == null) {
                    return new ArrayList<>();
                }
                // Create a new disconnected list from the PersistentCollection
                return new ArrayList<>((Collection<?>) source);
            }
        };
        
        // General collection converter
        Converter<Collection<?>, List<?>> collectionConverter = new AbstractConverter<Collection<?>, List<?>>() {
            @Override
            protected List<?> convert(Collection<?> source) {
                if (source == null) {
                    return new ArrayList<>();
                }
                return new ArrayList<>(source);
            }
        };
        
        modelMapper.addConverter(persistentCollectionConverter);
        modelMapper.addConverter(collectionConverter);
        
        // Custom mapping for Event to EventResponseDTO
        modelMapper.addMappings(new PropertyMap<Event, EventResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setTitle(source.getTitle());
                map().setDescription(source.getDescription());
                map().setDate(source.getDate());
                map().setType(source.getType());
                map().setLocation(source.getLocation());
                map().setRegistrationLink(source.getRegistrationLink());
                map().setNatureOfEvent(source.getNatureOfEvent());
                map().setFundingAgency(source.getFundingAgency());
                map().setChiefGuest(source.getChiefGuest());
                map().setParticipantsCount(source.getParticipantsCount());
                map().setIsCompleted(source.getIsCompleted());
                map().setIsPublic(source.getIsPublic());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                // Don't map collections directly
                skip().setTheme(null);
                skip().setOtherSpeakers(null);
                skip().setClub(null);
            }
        });
        
        return modelMapper;
    }
}
