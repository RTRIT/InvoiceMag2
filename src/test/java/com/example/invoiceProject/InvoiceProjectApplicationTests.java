package com.example.invoiceProject;

import com.example.invoiceProject.Model.Privilege;
import com.example.invoiceProject.Model.User;
import com.example.invoiceProject.Repository.PrivilegeRepository;
import com.example.invoiceProject.Repository.RoleRepository;
import com.example.invoiceProject.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InvoiceProjectApplicationTests {

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {

		//create privileges
		Privilege privilege1 = new Privilege();
		privilege1.setPrivilegeName("Create");
		privilege1.setPrivilegeDesc("Create Privilege");

		Privilege privilege2 = new Privilege();
		privilege2.setPrivilegeName("Delete");
		privilege2.setPrivilegeDesc("Delete Privilege");

		//privilegeRepository.addPrivilege(privilege1.getPrivilegeName(), privilege1.getPrivilegeDesc());
		//privilegeRepository.addPrivilege(privilege2.getPrivilegeName(), privilege2.getPrivilegeDesc());

		//roleRepository.addNewRole("ADMIN");
		//roleRepository.addNewRole("USER");
		//roleRepository.addNewRole("GUESS");

		//roleRepository.addPrivilegeToRole(roleRepository.getIdByRoleName("ADMIN"), privilegeRepository.getIdByName("Create"));
		//roleRepository.addPrivilegeToRole(roleRepository.getIdByRoleName("ADMIN"), privilegeRepository.getIdByName("Delete"));

		User user1 = new User();

		userRepository.register("email1@gmail.com", "123456789",roleRepository.getIdByRoleName("ADMIN"));

	}

}
