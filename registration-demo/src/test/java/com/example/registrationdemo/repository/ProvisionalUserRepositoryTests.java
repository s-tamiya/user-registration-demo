package com.example.registrationdemo.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.registrationdemo.entity.ProvisionalUser;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProvisionalUserRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProvisionalUserRepository provisionalUserRepository;

    @Test
    public void findByEmail() {
        ProvisionalUser expected = testEntityManager
                .persistFlushFind(
                        ProvisionalUser.of("test1@example", "pass", "test1 token", LocalDateTime.of(2018, 8, 1, 0, 0, 0))
                        );

        Optional<ProvisionalUser> user = provisionalUserRepository.findByEmail(expected.getEmail());
        ProvisionalUser actual = user.orElseThrow(RuntimeException::new);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}