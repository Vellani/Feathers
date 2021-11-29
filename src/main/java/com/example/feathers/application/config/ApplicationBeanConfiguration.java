package com.example.feathers.application.config;

import com.cloudinary.Cloudinary;
import com.example.feathers.database.model.entity.UserRoleEntity;
import com.example.feathers.database.repository.UserRoleRepository;
import com.example.feathers.util.ObjectConverter;
import com.example.feathers.util.UserRoleUtil;
import com.google.gson.*;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import static com.example.feathers.global.Constants.FLIGHT_LOG_DATA;

@Configuration
public class ApplicationBeanConfiguration {

    private final UserRoleRepository userRoleRepository;
    private final CloudinaryConfiguration cloudinaryConfiguration;

    public ApplicationBeanConfiguration(UserRoleRepository userRoleRepository, CloudinaryConfiguration cloudinaryConfiguration) {
        this.userRoleRepository = userRoleRepository;
        this.cloudinaryConfiguration = cloudinaryConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

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
        // This one is for DataSeed-To-ServiceModel
        Converter<String, Byte[]> seedByteConverter = new Converter<>() {
            @Override
            public Byte[] convert(MappingContext<String, Byte[]> mappingContext) {
                String s = null;
                try {
                    s = Files.readString(Path.of(mappingContext.getSource()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return objectConverter().toObjects(s.getBytes());

            }
        };
        // TODO Figure out how to use this shit
        /*Condition<LogBindingModel, LogEntity> empty = new Condition<LogBindingModel, LogEntity>() {
            @Override
            public boolean applies(MappingContext<LogBindingModel, LogEntity> context) {
                return context.getSource().getGpxLog().isEmpty();
            }
        };

        ExpressionMap<LogBindingModel, LogEntity> map = new ExpressionMap<LogBindingModel, LogEntity>() {
            @Override
            public void configure(ConfigurableConditionExpression<LogBindingModel, LogEntity> mapping) {
                mapping.when(empty).skip(LogBindingModel::getGpxLog, LogEntity::setGpxLog);
            }
        };

        modelMapper.typeMap(LogBindingModel.class, LogEntity.class).addMappings(map);*/


        Converter<Set<UserRoleEntity>, String> rolesToString = new Converter<>() {
            @Override
            public String convert(MappingContext<Set<UserRoleEntity>, String> mappingContext) {
                return userRoleUtil().findHighestRole(mappingContext.getSource()).getRole().name();
            }
        };

        Converter<String, String> stringToNull = new Converter<>() {
            @Override
            public String convert(MappingContext<String, String> mappingContext) {
                if (mappingContext.getSource() == null || mappingContext.getSource().trim().equals("")) return null;
                return mappingContext.getSource().trim();
            }
        };

        // Safety measure to prevent attempts to map
        modelMapper.addConverter(byteConverter);
        modelMapper.addConverter(seedByteConverter);
        modelMapper.addConverter(rolesToString);
        modelMapper.addConverter(stringToNull);
        //modelMapper.addConverter(nullConverter);

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
                        return LocalDate.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")); }
                })
                .registerTypeAdapter(LocalTime.class, new JsonDeserializer<LocalTime>() {
                    @Override
                    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
                        return LocalTime.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("HH:mm")); }
                })
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

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudinaryConfiguration.getCloudName(),
                        "api_key", cloudinaryConfiguration.getApiKey(),
                        "api_secret", cloudinaryConfiguration.getApiSecret()
                )
        );
    }

}

