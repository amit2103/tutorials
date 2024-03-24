package com.baeldung.webclient.clientcredentials.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, new WebSessionServerOAuth2AuthorizedClientRepository());

        oauth.setDefaultClientRegistrationId("bael");
        return WebClient.builder()
            .filter(oauth)
            .build();
    }
    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations() {
        ClientRegistration registration =   ClientRegistration.withRegistrationId("bael").authorizationGrantType(
            AuthorizationGrantType.CLIENT_CREDENTIALS).clientId("bael").tokenUri("default").build();

        return new InMemoryReactiveClientRegistrationRepository(registration);

    }

}
