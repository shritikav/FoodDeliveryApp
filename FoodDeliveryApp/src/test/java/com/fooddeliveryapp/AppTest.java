package com.fooddeliveryapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppTest {

    // ✅ Test if Spring context loads successfully
    @Test
    void contextLoads() {
        // If Spring Boot application starts without issues, this test passes
    }

    // ✅ Example unit test (replace with real logic later)
    @Test
    void simpleMathTest() {
        int sum = 2 + 3;
        assertThat(sum).isEqualTo(5);
    }
}
