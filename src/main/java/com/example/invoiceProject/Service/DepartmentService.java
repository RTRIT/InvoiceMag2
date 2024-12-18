package com.example.invoiceProject.Service;

import com.example.invoiceProject.DTO.requests.DepartmentRequest;
import com.example.invoiceProject.DTO.response.DepartmentResponse;
import com.example.invoiceProject.Exception.AppException;
import com.example.invoiceProject.Exception.ErrorCode;
import com.example.invoiceProject.Model.Department;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.DepartmentRepository;
import com.example.invoiceProject.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;

    public DepartmentResponse addDepart (DepartmentRequest request){
        //Check if name exist
        if(departmentRepository.existsBynameDepartment(request.getNameDepartment())){
            throw new AppException(ErrorCode.DEPARTMENT_NAME_IS_EXISTED);
        }
        //Check if mail is exist
        if(findByEmail(request.getEmail()) != null){
            throw new AppException(ErrorCode.DEPARTMENT_MAIL_IS_EXISTED);
        }
        Department department = mapper.map(request, Department.class);

        departmentRepository.save(department);

        return mapper.map(department, DepartmentResponse.class);
    }

    public List<DepartmentResponse> getList(){
        return departmentRepository.findAll().stream()
                .map(department -> mapper.map(department, DepartmentResponse.class))
                .collect(Collectors.toList());
    }

    public Department findByEmail(String departmentMail){
        return departmentRepository.findByEmail(departmentMail);
    }

//    public void

    public void deleteDepartment(String departmentName){

        List<User> userList = userRepository.findAll();

        Department department = departmentRepository.findByName(departmentName);
        if(department==null){
            throw new AppException(ErrorCode.DEPARTMENT_IS_NOT_EXISTED);
        }
        System.out.println("The department need to delete!"+ department);

        userList.forEach(user -> {
            //condition to remove user that does not have department
            if(user.getDepartment()!=null &&
                    user.getDepartment().getNameDepartment().equals(departmentName)){
                user.setDepartment(null);
            }
        });

        userRepository.saveAll(userList);
        System.out.println("Delete all user that have the department");


        departmentRepository.delete(department);
        System.out.println("Delete department successfully");
    }

}
