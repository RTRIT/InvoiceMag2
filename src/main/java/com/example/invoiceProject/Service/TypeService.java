package com.example.invoiceProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.invoiceProject.Model.Type;
import com.example.invoiceProject.Repository.TypeRepository;
import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public Type getTypeById(Long type_id){
        return typeRepository.getTypeById(type_id);
    }

    public List<Type> getAllTypes(){
        return typeRepository.getAllTypes();
    }

    public void createType(Type type){
        typeRepository.createType(type.getName(), type.getDescription());
    }

    public void updateType(Type type, Long type_id){
        typeRepository.updateType(type_id, type.getName(), type.getDescription());
    }

    public void deleteType(Long type_id){
        typeRepository.deleteType(type_id);
    }
}
