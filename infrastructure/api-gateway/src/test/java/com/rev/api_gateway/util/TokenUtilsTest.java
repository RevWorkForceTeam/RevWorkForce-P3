package com.rev.api_gateway.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenUtilsTest {

    @Test
    void extractToken_ValidBearerHeader_ReturnsToken() {
        String header = "Bearer my-secret-token";
        String result = TokenUtils.extractToken(header);
        assertEquals("my-secret-token", result);
    }

    @Test
    void extractToken_NullHeader_ReturnsNull() {
        assertNull(TokenUtils.extractToken(null));
    }

    @Test
    void extractToken_InvalidHeader_ReturnsNull() {
        assertNull(TokenUtils.extractToken("Basic user:pass"));
    }
}
