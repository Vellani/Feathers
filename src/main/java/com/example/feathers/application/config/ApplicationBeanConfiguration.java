package com.example.feathers.application.config;

import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.repository.UserRoleRepository;
import com.example.feathers.util.ObjectConverter;
import com.example.feathers.util.UserRoleUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Configuration
public class ApplicationBeanConfiguration {

    private final UserRoleRepository userRoleRepository;

    public ApplicationBeanConfiguration(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<MultipartFile, Byte[]> byteConverter = new Converter<>() {
            @Override
            public Byte[] convert(MappingContext<MultipartFile, Byte[]> mappingContext) {
                try {
                    return objectConverter().toObjects(mappingContext.getSource().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Converter<Set<UserRoleEntity>, String> rolesToString = new Converter<>() {
            @Override
            public String convert(MappingContext<Set<UserRoleEntity>, String> mappingContext) {
                return userRoleUtil().findHighestRole(mappingContext.getSource()).getRole().name();
            }
        };

        // Safety measure to prevent attempts to map
        //Converter<byte[], MultipartFile> nullConverter = mappingContext -> null;

        modelMapper.addConverter(byteConverter);
        modelMapper.addConverter(rolesToString);
        //modelMapper.addConverter(nullConverter);

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public UserRoleUtil userRoleUtil() {
        return new UserRoleUtil(userRoleRepository);
    }

    @Bean
    public ObjectConverter objectConverter() {
        return new ObjectConverter();
    }

}
