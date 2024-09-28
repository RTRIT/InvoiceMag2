package com.example.invoiceProject.Repository;

import com.example.invoiceProject.Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        //create data for testing;
        User user = new User();

        userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void authenticate() {
    }

    @Test
    public void findByEmail() {

    }

    @Test
    public void updateUserById() {
    }

    @Test
    public void getUserByEmail() {
    }

    @Test
    public void existUser() {
    }
}