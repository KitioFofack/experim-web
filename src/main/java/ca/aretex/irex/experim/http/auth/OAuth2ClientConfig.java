package ca.aretex.irex.experim.http.auth;

import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.Collections;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
public class OAuth2ClientConfig {
    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;
    private final List<String> scopes;

    public OAuth2ClientConfig(Config config){
        this(
                config.getString("client.id"),
                config.getString("client.secret"),
                config.getString("server.token.url"),
                config.getStringList("server.scopes")
        );
    }

    public OAuth2RestTemplate getOAuth2RestTemplate(OAuth2ProtectedResourceDetails details){
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
        AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Collections.singletonList(new ClientCredentialsAccessTokenProvider()));
        oAuth2RestTemplate.setAccessTokenProvider(accessTokenProvider);
        return oAuth2RestTemplate;
    }

    public OAuth2RestTemplate getOAuth2RestTemplate(){
        return getOAuth2RestTemplate(getResourcesDetails());
    }

    public OAuth2ProtectedResourceDetails getResourcesDetails(){
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(tokenUrl);
        details.setScope(scopes);
        return details;
    }



}
