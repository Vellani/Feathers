package com.example.feathers.database.service.impl;

import com.example.feathers.database.model.entity.AerodromeEntity;
import com.example.feathers.database.model.seed.AerodromeSeed;
import com.example.feathers.database.repository.AerodromeRepository;
import com.example.feathers.database.service.AerodromeService;
import com.example.feathers.web.exception.impl.AerodromeDoesNotExistException;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.feathers.global.Constants.AERODROMES_PATH;

@Service
public class AerodromeServiceImpl implements AerodromeService {

    private final AerodromeRepository aerodromeRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public AerodromeServiceImpl(AerodromeRepository aerodromeRepository, Gson gson, ModelMapper modelMapper) {
        this.aerodromeRepository = aerodromeRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> findAllMatchingAerodromes(String aero) {
        return aerodromeRepository.findAllMatchingAerodromes(aero);
    }

    @Override
    public AerodromeEntity findByName(String aerodrome) {
        return aerodromeRepository.findByName(aerodrome).orElseThrow(AerodromeDoesNotExistException::new);
    }

    @Override
    public boolean existsByName(String aerodrome) {
        return aerodromeRepository.existsByName(aerodrome);
    }

    @Override
    public void initialize() throws IOException {
        if (aerodromeRepository.count() == 0) {
            System.out.println("---------- Initializing Database | Stand by as it may take up to a minute! -------------");
            Set<AerodromeSeed> collect = Arrays.stream(
                    gson.fromJson(
                            Files.readString(Path.of(AERODROMES_PATH)),
                            AerodromeSeed[].class))
                    .collect(Collectors.toSet());

            collect.forEach(e -> {
                AerodromeEntity aerodrome = modelMapper.map(e, AerodromeEntity.class);
                if (aerodrome.getIcaoCode().length() == 4) aerodromeRepository.save(aerodrome);
            });
            System.out.println("Aerodromes in the Database: " + aerodromeRepository.count() + " added.");
        }
    }

}
