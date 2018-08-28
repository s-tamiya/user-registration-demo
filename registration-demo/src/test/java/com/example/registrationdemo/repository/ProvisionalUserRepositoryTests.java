package com.example.registrationdemo.repository;

import static org.assertj.core.api.Assertions.*;

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
    public void findByUserId() {
        ProvisionalUser expected = testEntityManager
                .persistFlushFind(
                        ProvisionalUser.of(Long.valueOf(3L), Long.valueOf(3L))
                        );
        System.out.println(expected);
        Optional<ProvisionalUser> user = provisionalUserRepository.findByUserId(expected.getUserId());
        ProvisionalUser actual = user.orElseThrow(RuntimeException::new);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }
}
