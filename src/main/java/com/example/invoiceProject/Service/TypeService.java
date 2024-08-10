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
        return typeRepository.findById(type_id).orElse(null);
    }

    public List<Type> getAllTypes(){
        return typeRepository.getAllTypes();
    }

    public void createType(Type type){
        typeRepository.createType(type.getName(), type.getDescription());
    }

    public void updateType(Type type, Long type_id){
        Type exiType = typeRepository.getTypeById(type_id);
        exiType.setName(type.getName());
        exiType.setDescription(type.getDescription());
        typeRepository.save(exiType);
    }

    public void deleteType(Long type_id){
        typeRepository.deleteType(type_id);
    }
}
