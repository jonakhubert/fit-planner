package com.fitplanner.authentication.repository;

import com.fitplanner.authentication.MongoDBContainerConfig;
import com.fitplanner.authentication.model.token.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBContainerConfig.class)
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository underTest;

    @Test
    public void shouldReturnTokenWhenExistingTokenIsProvided() {
        // given
        String validToken = "john-token";
        Token token = new Token(
            validToken,
            "johnsmith@gmail.com"
        );
        underTest.save(token);

        // when
        Token result = underTest.findByToken(validToken).orElse(null);

        // then
        assertEquals(result, token);
    }

    @Test
    public void shouldReturnNullWhenNonExistingTokenIsProvided() {
        // given
        String invalidToken = "invalid-token";

        // when
        Token result = underTest.findByToken(invalidToken).orElse(null);

        // then
        assertNull(result);
    }

    @Test
    public void shouldReturnTokenWhenExistingUserEmailIsProvided() {
        // given
        String email = "emmabrown@gmail.com";
        Token token = new Token(
            "emma-token",
            email
        );
        underTest.save(token);

        // when
        Token result = underTest.findByUserEmail(email).orElse(null);

        // then
        assertEquals(result, token);
    }

    @Test
    public void shouldReturnNullWhenNonExistingUserEmailIsProvided() {
        // given
        String email = "invalid";

        // when
        Token result = underTest.findByUserEmail(email).orElse(null);

        // then
        assertNull(result);
    }
}