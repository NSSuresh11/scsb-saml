package org.recap.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.security.AuthSecurityConfiguration;
import org.recap.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dinakar on 23/12/20.
 */
@RequestMapping(value = "/userSamlAuth")
@Api(value = "userSamlAuth")
@Slf4j
@RestController
public class SamlController extends AbstractController {

    @Autowired
    private PropertyUtil propertyUtil;

    private HttpSecurity http;



    /**
     * Perform the SCSB authentication and authorization after user authenticated from partners IMS
     *
     * @param request the request
     * @return the view name
     */
    @PostMapping(value = "/samlAuthService")
    @ApiOperation(value = "samlAuthService", notes = "Used to Authenticate User", consumes = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Session created successfully")})
    public Map<String, Object> loginSaml(@RequestBody UsernamePasswordToken token, HttpServletRequest request, BindingResult error) {
       log.info("Inside loginSaml >>>>");
        Map<String, Object> authMap = new HashMap<>();
        AuthSecurityConfiguration authSecurityConfiguration = new AuthSecurityConfiguration();
        ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
            @Override
            public <O> O postProcess(O object) {
                return null;
            }
        } ;
        AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
        Map<Class<?>, Object> sharedObjects = new HashMap<>() ;
        try {
            authSecurityConfiguration.configure(new HttpSecurity(objectPostProcessor,
                    authenticationBuilder, sharedObjects));
        }
        catch (Exception e){
            log.info("Caught Exception >>>>>>> " + e);
            return authMap;
        }
        return authMap;
    }

    @GetMapping(value = "/login-saml")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        log.info("Calling login /login-scsb >>>>>> ");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        } catch (Exception exception) {
            log.error(ScsbCommonConstants.LOG_ERROR, exception);
            log.error("Exception occurred in authentication : {}" , exception.getLocalizedMessage());
            return ScsbConstants.REDIRECT_HOME;
        }
        return ScsbConstants.REDIRECT_SEARCH;
    }

    private boolean userHasRoles(Map<String, Object> resultMap) {
        return (Boolean) resultMap.get(ScsbConstants.SEARCH_PRIVILEGE);
    }
}
