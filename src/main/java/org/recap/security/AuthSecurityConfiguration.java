package org.recap.security;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.recap.PropertyKeyConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sheiks on 30/01/17.
 */
@RequestMapping(value = "/userSamlAuth")
@Api(value = "userSamlAuth")
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class AuthSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${" + PropertyKeyConstants.CAS_DEFAULT_URL_PREFIX + "}")
    private String casUrlPrefix;

    @Value("${" + PropertyKeyConstants.CAS_DEFAULT_SERVICE_LOGOUT + "}")
    private String casServiceLogout;

    @Value("${" + PropertyKeyConstants.SCSB_APP_SERVICE_LOGOUT + "}")
    private String appServiceLogout;

    @Value("${" + PropertyKeyConstants.SCSB_UI_URL + "}")
    private String scsbUiUrl;

    @Override
    @PostMapping(value = "/samlAuthService")
    @ApiOperation(value = "samlAuthService", notes = "Used to Authenticate User", consumes = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Session created successfully")})
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.saml2Login(saml2 -> {
                    try {
                        saml2
                                .relyingPartyRegistrationRepository(relyingPartyRegistrations());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrations() {
        RelyingPartyRegistration registration = RelyingPartyRegistrations
                .fromMetadataLocation("/data/sso/idp-metadata.xml")
                .registrationId("recap")
                .build();
        return new InMemoryRelyingPartyRegistrationRepository(registration);

    }
}

