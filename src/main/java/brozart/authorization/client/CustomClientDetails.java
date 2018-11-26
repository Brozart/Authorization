package brozart.authorization.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class CustomClientDetails implements ClientDetails {

    private final Client client;
    private final Set<String> scope;

    /**
     * Creates a new {@link CustomClientDetails}
     *
     * @param client the client
     */
    CustomClientDetails(final Client client) {
        this.client = client;
        final String scope = client.getScope();
        if (scope != null) {
            final String[] split = scope.trim().split("\\s+");
            this.scope = new LinkedHashSet<>(Arrays.asList(split));
        } else {
            this.scope = Collections.emptySet();
        }
    }


    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return Collections.singleton("resource_id");
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new LinkedHashSet<>(Arrays.asList("client_credentials", "password"));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.emptySet();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 3600;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(final String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }
}
