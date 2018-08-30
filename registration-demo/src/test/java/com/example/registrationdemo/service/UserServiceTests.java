package com.example.registrationdemo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.registrationdemo.entity.ProvisionalUser;
import com.example.registrationdemo.entity.User;
import com.example.registrationdemo.repository.ProvisionalUserRepository;
import com.example.registrationdemo.repository.UserRepository;
import com.example.registrationdemo.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTests {

    @InjectMocks
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProvisionalUserRepository provisionalUserRepository;


    @Before
    public void setup() {
        // これを忘れると@MockBeanアノテーションを付けたクラスがサービスに注入されない
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserByEmail() throws Exception {
        User expected = new User(1L, "testuser1", "pass", "test1@example.com");

        // Mockitoでモックを作成する
        when(userRepository.findByEmail(expected.getEmail()))
            .thenReturn(Optional.of(expected));

        User actual = userService.findUserByEmail(expected.getEmail()).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updateUser() {
        User expected = new User(1L, "testuser1", "pass", "test1@example.com");
        userService.updateUser(expected);
        // 戻り値 void のメソッドはverifyでメソッドが呼ばれたことを確認する
        verify(userRepository, times(1)).save(expected);
    }

    @Test
    public void deleteUser() {
        User expected = new User(1L, "testuser1", "pass", "test1@example.com");
        userService.deleteUser(expected.getUserId());
        verify(userRepository, times(1)).deleteById(expected.getUserId());
    }

    @Test
    public void findProvisionalUserByEmail() {
        ProvisionalUser expected = new ProvisionalUser(
                1L, "test1@example.com", "pass", "test_token", LocalDateTime.now().plusHours(24L), LocalDateTime.now()
                );

        // Mockitoでモックを作成する
        when(provisionalUserRepository.findByEmail(expected.getEmail()))
            .thenReturn(Optional.of(expected));

        ProvisionalUser actual = userService.
                findProvisionalUserByEmail(expected.getEmail()).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findProvisionalUserByToken() {
        ProvisionalUser expected = new ProvisionalUser(
                1L, "test1@example.com", "pass", "test_token", LocalDateTime.now().plusHours(24L), LocalDateTime.now()
                );

        // Mockitoでモックを作成する
        when(provisionalUserRepository.findByToken(expected.getToken()))
            .thenReturn(Optional.of(expected));

        ProvisionalUser actual = userService.
                findProvisionalUserByToken(expected.getToken()).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void provisionalRegister_OK() {
        User user = new User(1L, "testuser1", "pass", "test1@example.com");

        when(userRepository.findByEmail(user.getEmail()))
            .thenReturn(Optional.empty());
        when(provisionalUserRepository.findByEmail(user.getEmail()))
            .thenReturn(Optional.empty());

        userService.provisionalRegister(user);

        // パラメータの内容がロジックに左右されるときのテストをどうするか
        // 例えばLocalDateTime.now()とかsecureRandomを使用していとき
        verify(provisionalUserRepository, times(1)).save(any());
    }

    // 例外のテスト① アノテーションで例外を予測する
    @Test(expected = RuntimeException.class)
    public void provisionalRegister_AlreadyExists() {
        User user = new User(1L, "testuser1", "pass", "test1@example.com");

        // 本登録済みを想定
        when(userRepository.findByEmail(user.getEmail()))
            .thenReturn(Optional.of(user));

        userService.provisionalRegister(user);
    }

    // 例外のテスト② @RuleにExpectedException、@Testメソッド内で発生する例外を予測
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void provisionalRegister_AlreadyExists2() {
        User user = new User(1L, "testuser1", "pass", "test1@example.com");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("本登録済み");

        // 本登録済みを想定
        when(userRepository.findByEmail(user.getEmail()))
            .thenReturn(Optional.of(user));

        userService.provisionalRegister(user);
    }


    @Test
    public void register_OK() {
        String token = "test_token";
        ProvisionalUser prov = new ProvisionalUser(
                1L,
                "test1@example.com",
                "pass",
                "test_token",
                LocalDateTime.of(2018, 8, 31, 12, 00),
                LocalDateTime.of(2017, 8, 31, 12, 00)
                );
        User expected = new User(null, null, "pass", "test1@example.com");

        when(provisionalUserRepository.findByToken(token))
            .thenReturn(Optional.of(prov));

        userService.register(token);

        verify(userRepository, times(1)).save(expected);
    }

    @Test(expected = RuntimeException.class)
    public void register_ProvisionalEmpty() {
        String token = "test_token";

        when(provisionalUserRepository.findByToken(token))
            .thenReturn(Optional.empty());

        userService.register(token);
    }

    @Test(expected = RuntimeException.class)
    public void register_Expired() {
        String token = "test_token";
        ProvisionalUser prov = new ProvisionalUser(
                1L,
                "test1@example.com",
                "pass",
                "test_token",
                LocalDateTime.of(2017, 8, 30, 12, 00),
                LocalDateTime.of(2017, 8, 30, 12, 00)
                );

        when(provisionalUserRepository.findByToken(token))
        .thenReturn(Optional.of(prov));

        userService.register(token);

    }
}
