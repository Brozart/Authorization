package brozart.authorization.client;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class CustomClientDetailsTest {

    @Test
    public void testCreateClientDetails() {
        final Client client = new Client();
        client.setClientId("client_id");
        client.setClientSecret("client_secret");
        client.setScope("scope1 scope2");

        final CustomClientDetails customClientDetails = new CustomClientDetails(client);
        assertTrue(customClientDetails.isScoped());
        assertTrue(customClientDetails.getScope().stream()
                .allMatch(s -> s.equals("scope1") || s.equals("scope2")));
        assertEquals(client.getClientId(), customClientDetails.getClientId());
        assertEquals(client.getClientSecret(), customClientDetails.getClientSecret());
        assertTrue(customClientDetails.getResourceIds().contains("resource_id"));
        assertTrue(customClientDetails.isSecretRequired());
        assertTrue(customClientDetails.getAuthorizedGrantTypes().stream()
                .allMatch(g -> g.equals("client_credentials") || g.equals("password")));
        assertEquals(Collections.emptySet(), customClientDetails.getRegisteredRedirectUri());
        assertEquals(Collections.emptySet(), customClientDetails.getAuthorities());
        assertEquals(3600, customClientDetails.getAccessTokenValiditySeconds().intValue());
        assertNull(customClientDetails.getRefreshTokenValiditySeconds());
        assertFalse(customClientDetails.isAutoApprove(null));
        assertEquals(Collections.emptyMap(), customClientDetails.getAdditionalInformation());
    }

    @Test
    public void testCreateClientDetails_EmptyScope() {
        final Client client = new Client();
        client.setClientId("client_id");
        client.setClientSecret("client_secret");

        final CustomClientDetails customClientDetails = new CustomClientDetails(client);
        assertTrue(customClientDetails.isScoped());
        assertEquals(Collections.emptySet(), customClientDetails.getScope());
        assertEquals(client.getClientId(), customClientDetails.getClientId());
        assertEquals(client.getClientSecret(), customClientDetails.getClientSecret());
        assertTrue(customClientDetails.getResourceIds().contains("resource_id"));
        assertTrue(customClientDetails.isSecretRequired());
        assertTrue(customClientDetails.getAuthorizedGrantTypes().stream()
                .allMatch(g -> g.equals("client_credentials") || g.equals("password")));
        assertEquals(Collections.emptySet(), customClientDetails.getRegisteredRedirectUri());
        assertEquals(Collections.emptySet(), customClientDetails.getAuthorities());
        assertEquals(3600, customClientDetails.getAccessTokenValiditySeconds().intValue());
        assertNull(customClientDetails.getRefreshTokenValiditySeconds());
        assertFalse(customClientDetails.isAutoApprove(null));
        assertEquals(Collections.emptyMap(), customClientDetails.getAdditionalInformation());
    }
}
