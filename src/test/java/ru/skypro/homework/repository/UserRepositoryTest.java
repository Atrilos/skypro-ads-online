package ru.skypro.homework.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skypro.homework.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine"));
    @Autowired
    private UserRepository out;

    @Autowired
    private TestEntityManager entityManager;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username=", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password=", postgreSQLContainer::getPassword);
    }

    @Test
    public void findByEmail() {
        User userA = User.builder()
                .email("AAA@A.A")
                .build();
        User userB = User.builder()
                .email("bbb@b.b")
                .build();

        entityManager.persist(userA);
        entityManager.persist(userB);

        assertThat(out.findByEmail("aaa@a.a"))
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(userA));
        assertThat(out.findByEmail("bBB@B.b"))
                .usingRecursiveComparison()
                .isEqualTo(Optional.of(userB));
    }

    @Test
    public void existByEmail() {
        User userA = User.builder()
                .email("AAA@A.A")
                .build();
        User userB = User.builder()
                .email("bbb@b.b")
                .build();

        entityManager.persist(userA);
        entityManager.persist(userB);

        assertThat(out.existsByEmail("aaa@a.a")).isTrue();
        assertThat(out.existsByEmail("bBB@B.b")).isTrue();
    }

}