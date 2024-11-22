package com.example.invoiceProject.Mapper;

import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.User;
import org.modelmapper.Converter;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();

        //Using this configuration for nested obj // Eg:  Department
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }
}