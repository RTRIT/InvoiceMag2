//package com.example.invoiceProject.Repository;
//
//import com.example.invoiceProject.Model.Privilege;
//import com.example.invoiceProject.Model.Role;
//import com.example.invoiceProject.Model.User;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class UserRepositoryTest {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    PrivilegeRepository privilegeRepository;
//
//    @Before
//    public void setUp() throws Exception {
//
//        Privilege privilege1 = new Privilege();
//        privilege1.setPrivilegeName("Edit");
//        privilege1.setPrivilegeDesc("Edit User");
//        Privilege privilege2 = new Privilege();
//        privilege2.setPrivilegeName("Add");
//        privilege2.setPrivilegeDesc("Add User");
//        Privilege privilege3 = new Privilege();
//        privilege3.setPrivilegeName("Delete");
//        privilege3.setPrivilegeDesc("Delete User");
//        //Save privilege
//        privilegeRepository.save(privilege1);
//        privilegeRepository.save(privilege2);
//        privilegeRepository.save(privilege3);
//
//
//        List<Privilege> privilegeList = new ArrayList<>();
//        privilegeList.add(privilege1);
//        privilegeList.add(privilege2);
//        privilegeList.add(privilege3);
//
//
//        Role role = new Role();
//        role.setRoleName("USER");
//        role.setPrivileges(privilegeList);
//        roleRepository.save(role);
//
//        Role newRole = roleRepository.findByRoleName("USER");
////        assert(role)
//        System.out.println(newRole);
//
//        //create data for testing;
//        User user = new User();
//        user.setEmail("tringuyen240503@gmail.com");
//        user.setPassword("123456789");
//        user.setRole(newRole);
//        System.out.println(user);
//
//        userRepository.save(user);
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void authenticate() {
//    }
//
//    @Test
//    public void findByEmail() {
//
//    }
//
//    @Test
//    public void updateUserById() {
//    }
//
//    @Test
//    public void getUserByEmail() {
//    }
//
//    @Test
//    public void existUser() {
//    }
//}