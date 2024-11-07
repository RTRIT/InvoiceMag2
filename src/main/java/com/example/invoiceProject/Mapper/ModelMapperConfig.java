package com.example.invoiceProject.Mapper;

import com.example.invoiceProject.DTO.response.UserResponse;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        //Custom converter
//        Converter<Department, String> departmentToStringConverter = ctx ->
//                ctx.getSource() == null ? null :  ctx.getSource().getNameDepartment();
//
//        modelMapper.addMappings(new PropertyMap<User, UserResponse>() {
//            @Override
//            protected void configure() {
//                using(departmentToStringConverter).map(source.getDepartment()).setDepartment(null);
//            }
//        });
        return modelMapper;
    }
}