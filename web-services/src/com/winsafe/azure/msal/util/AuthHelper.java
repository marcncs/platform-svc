// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.winsafe.azure.msal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.aad.msal4j.*;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.StringUtil;

import static com.winsafe.azure.msal.util.SessionManagementHelper.FAILED_TO_VALIDATE_MESSAGE;

/**
 * Helpers for acquiring authorization codes and tokens from AAD
 */
public class AuthHelper {

    public static final String PRINCIPAL_SESSION_NAME = "principal";
    public static final String TOKEN_CACHE_SESSION_ATTRIBUTE = "token_cache";

    private String clientId = "";
    private String clientSecret = "";
    private String authority = "";
    private String redirectUriSignIn = "";
    private String redirectUriGraph = "";


    public AuthHelper() {
    	try {
    		clientId = SysConfig.getSysConfig().getProperty("aad.clientId");
    		clientSecret = SysConfig.getSysConfig().getProperty("aad.secretKey");
    		authority = SysConfig.getSysConfig().getProperty("aad.authority");
    		redirectUriSignIn = SysConfig.getSysConfig().getProperty("aad.redirectUriSignin");
    		redirectUriGraph = SysConfig.getSysConfig().getProperty("aad.redirectUriGraph");
		} catch (Exception e) {
			WfLogger.error("", e);
		}
	}

	public void init() {
        /*clientId = "ac845c84-9270-4d31-8d41-44eaeb1dc09f";
        authority = "https://login.microsoftonline.com/fcb2b37b-5da0-466b-9b83-0014b67a7c78/";
        clientSecret = "4y=UNYHd8Av8K[oBQ1hRATlWwAAU:c]5";
        redirectUriSignIn = "https://henkelrfid-qa.winsafe.cn/msal4jsample/secure/aad";
        redirectUriGraph = "https://henkelrfid-qa.winsafe.cn/msal4jsample/graph/me";*/
    }

    public void processAuthenticationCodeRedirect(HttpServletRequest httpRequest, String currentUri, String fullUrl)
            throws Exception {

    	WfLogger.error("set parameters...");
        Map<String, List<String>> params = new HashMap<>();
        for (String key : httpRequest.getParameterMap().keySet()) {
            params.put(key, Collections.singletonList(httpRequest.getParameterMap().get(key)[0]));
        }
        // validate that state in response equals to state in request
        //StateData stateData = SessionManagementHelper.validateState(httpRequest.getSession(), params.get(SessionManagementHelper.STATE).get(0));
        WfLogger.error("try to get accessToken...");
        AuthenticationResponse authResponse = AuthenticationResponseParser.parse(new URI(fullUrl), params);
        WfLogger.error("authResponse:"+authResponse);
        WfLogger.error("authResponse.getRedirectionURI():"+authResponse.getRedirectionURI());
        if (AuthHelper.isAuthenticationSuccessful(authResponse)) {
            AuthenticationSuccessResponse oidcResponse = (AuthenticationSuccessResponse) authResponse;
            WfLogger.error("validate that OIDC Auth Response matches Code Flow (contains only requested artifacts):");
            // validate that OIDC Auth Response matches Code Flow (contains only requested artifacts)
            validateAuthRespMatchesAuthCodeFlow(oidcResponse);

            IAuthenticationResult result = getAuthResultByAuthCode(
                    httpRequest,
                    oidcResponse.getAuthorizationCode(),
                    currentUri);

            // validate nonce to prevent reply attacks (code maybe substituted to one with broader access)
            //validateNonce(stateData, getNonceClaimValueFromIdToken(result.idToken()));
            SessionManagementHelper.setSessionPrincipal(httpRequest, result);
        } else {
            AuthenticationErrorResponse oidcResponse = (AuthenticationErrorResponse) authResponse;
            WfLogger.error("oidcResponse.getErrorObject().getCode():"+oidcResponse.getErrorObject().getCode());
            WfLogger.error("oidcResponse.getErrorObject().getDescription():"+oidcResponse.getErrorObject().getDescription());
            throw new Exception(String.format("Request for auth code failed: %s - %s",
                    oidcResponse.getErrorObject().getCode(),
                    oidcResponse.getErrorObject().getDescription()));
        }
    }

    public IAuthenticationResult getAuthResultBySilentFlow(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws Throwable {

        IAuthenticationResult result =  SessionManagementHelper.getAuthSessionObject(httpRequest);

        IConfidentialClientApplication app = createClientApplication();

        Object tokenCache = httpRequest.getSession().getAttribute("token_cache");
        if (tokenCache != null) {
            app.tokenCache().deserialize(tokenCache.toString());
        }

        SilentParameters parameters = SilentParameters.builder(
                Collections.singleton("User.Read"),
                result.account()).build();

        CompletableFuture<IAuthenticationResult> future = app.acquireTokenSilently(parameters);
        IAuthenticationResult updatedResult = future.get();

        //update session with latest token cache
        SessionManagementHelper.storeTokenCacheInSession(httpRequest, app.tokenCache().serialize());

        return updatedResult;
    }

    private void validateNonce(StateData stateData, String nonce) throws Exception {
        if (StringUtil.isEmpty(nonce) || !nonce.equals(stateData.getNonce())) {
            throw new Exception(FAILED_TO_VALIDATE_MESSAGE + "could not validate nonce");
        }
    }

    private String getNonceClaimValueFromIdToken(String idToken) throws ParseException {
        return (String) JWTParser.parse(idToken).getJWTClaimsSet().getClaim("nonce");
    }

    private void validateAuthRespMatchesAuthCodeFlow(AuthenticationSuccessResponse oidcResponse) throws Exception {
        WfLogger.error("oidcResponse.getIDToken():"+oidcResponse.getIDToken());
        WfLogger.error("oidcResponse.getAccessToken():"+oidcResponse.getAccessToken());
        WfLogger.error("oidcResponse.getAuthorizationCode():"+oidcResponse.getAuthorizationCode());
        if (oidcResponse.getIDToken() != null || oidcResponse.getAccessToken() != null ||
                oidcResponse.getAuthorizationCode() == null) {
            throw new Exception(FAILED_TO_VALIDATE_MESSAGE + "unexpected set of artifacts received");
        }
    }

    public void sendAuthRedirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String scope, String redirectURL)
            throws IOException {

        // state parameter to validate response from Authorization server and nonce parameter to validate idToken
        WfLogger.error("state parameter to validate response from Authorization server and nonce parameter to validate idToken...");
        String state = UUID.randomUUID().toString();
        String nonce = UUID.randomUUID().toString();
        SessionManagementHelper.storeStateAndNonceInSession(httpRequest.getSession(), state, nonce);

        httpResponse.setStatus(302);
        String authorizationCodeUrl = getAuthorizationCodeUrl(httpRequest.getParameter("claims"), scope, redirectURL, state, nonce);
        httpResponse.sendRedirect(authorizationCodeUrl);
    }

    public String getAuthorizationCodeUrl(String claims, String scope, String registeredRedirectURL, String state, String nonce)
            throws UnsupportedEncodingException {
        WfLogger.error("生成重定向的URL...");
        String urlEncodedScopes = scope == null ?
                URLEncoder.encode("openid offline_access profile", "UTF-8") :
                URLEncoder.encode("openid offline_access profile" + " " + scope, "UTF-8");


        String authorizationCodeUrl = authority + "oauth2/v2.0/authorize?" +
                "response_type=code&" +
                "response_mode=form_post&" +
                "redirect_uri=" +  URLEncoder.encode(registeredRedirectURL, "UTF-8") +
                "&client_id=" + clientId +
                "&scope=" + urlEncodedScopes +
                (StringUtil.isEmpty(claims) ? "" : "&claims=" + claims) +
                //"&prompt=select_account" +
                "&state=" + state
                + "&nonce=" + nonce;
        WfLogger.error("生成重定向的URL:"+authorizationCodeUrl);
        return authorizationCodeUrl;
    }

    private IAuthenticationResult getAuthResultByAuthCode(
            HttpServletRequest httpServletRequest,
            AuthorizationCode authorizationCode,
            String currentUri) throws Exception {
        WfLogger.error("authorizationCode..."+authorizationCode);
        WfLogger.error("currentUri..."+currentUri);
        IAuthenticationResult result;
        ConfidentialClientApplication app;
        try {
            app = createClientApplication();

            String authCode = authorizationCode.getValue();
            AuthorizationCodeParameters parameters = AuthorizationCodeParameters.builder(
                    authCode,
                    new URI(currentUri)).
                    build();

            Future<IAuthenticationResult> future = app.acquireToken(parameters);

            result = future.get();
            WfLogger.error("result.accessToken.."+result.accessToken());
            WfLogger.error("result.idToken.."+result.idToken());
            WfLogger.error("result.account.."+result.account());
            WfLogger.error("result.environment.."+result.environment());
            WfLogger.error("result.scopes.."+result.scopes());
            WfLogger.error("result.expiresOnDate.."+result.expiresOnDate());
            if(result.account() !=null) {
                WfLogger.error("result.account.homeAccountId."+result.account().homeAccountId());
                WfLogger.error("result.account.username."+result.account().username());
                WfLogger.error("result.account.environment."+result.account().environment());
            }
        } catch (ExecutionException e) {
            WfLogger.error("", e);
            throw e;
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }

        WfLogger.error("app.tokenCache().serialize().."+app.tokenCache().serialize());

        SessionManagementHelper.storeTokenCacheInSession(httpServletRequest, app.tokenCache().serialize());

        return result;
    }

    private ConfidentialClientApplication createClientApplication() throws MalformedURLException {
        return ConfidentialClientApplication.builder(clientId, ClientCredentialFactory.createFromSecret(clientSecret)).
                authority(authority).
                build();
    }

    private static boolean isAuthenticationSuccessful(AuthenticationResponse authResponse) {
        return authResponse instanceof AuthenticationSuccessResponse;
    }

    public String getRedirectUriSignIn() {
        return redirectUriSignIn;
    }

    public String getRedirectUriGraph() {
        return redirectUriGraph;
    }
}
