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
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdsRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:alpine"));
    @Autowired
    private AdsRepository out;

    @Autowired
    private TestEntityManager entityManager;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username=", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password=", postgreSQLContainer::getPassword);
    }

    @Test
    public void findByUserId() {
        User userA = User.builder()
                .email("AAA@A.A")
                .build();
        User userB = User.builder()
                .email("BBB@B.B")
                .build();
        entityManager.persistAndFlush(userA);
        entityManager.persistAndFlush(userB);
        Ads adsA = Ads.builder()
                .description("Aaa")
                .price(10)
                .title("Bbb")
                .user(userA)
                .build();
        Ads adsB = Ads.builder()
                .description("Aan")
                .price(100)
                .title("Bbn")
                .user(userA)
                .build();
        Ads adsC = Ads.builder()
                .description("CCC")
                .price(555)
                .title("DDD")
                .user(userB)
                .build();
        entityManager.persist(adsA);
        entityManager.persist(adsB);
        entityManager.persist(adsC);

        assertThat(out.findByUserId(userA.getId()))
                .hasSize(2)
                .contains(adsA, adsB);
    }

}