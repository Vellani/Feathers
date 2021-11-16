package com.example.feathers.service.impl;

import com.example.feathers.model.entity.AerodromeEntity;
import com.example.feathers.model.seed.AerodromeSeed;
import com.example.feathers.repository.AerodromeRepository;
import com.example.feathers.service.AerodromeService;
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
        return aerodromeRepository.findByName(aerodrome).orElse(null);
    }

    @Override
    public boolean existsByName(String aerodrome) {
        return aerodromeRepository.existsByName(aerodrome);
    }

    @Override
    public String findAerodromeData() throws IOException {
        return Files.readString(Path.of("src/main/resources/data/airportsEU.json"));
    }

    @Override
    public void initialize() throws IOException {
        // A few concessions had to be made as the whole database was close to 100k entries.
        // Only EU aerodromes a left here and only ones with ICAO code length of 4 characters.
        if (aerodromeRepository.count() == 0) {
            System.out.println("---------- Initializing Database | Stand by as it may take up to a minute! -------------");

            Set<AerodromeSeed> collect = Arrays.stream(gson.fromJson(findAerodromeData(), AerodromeSeed[].class)).collect(Collectors.toSet());
            collect.forEach(e -> {
                AerodromeEntity aerodrome = modelMapper.map(e, AerodromeEntity.class);
                if (aerodrome.getIcaoCode().length() == 4) aerodromeRepository.save(aerodrome);
            });
            System.out.println("Aerodromes in the Database: " + aerodromeRepository.count() + " added.");
        }
    }

}
