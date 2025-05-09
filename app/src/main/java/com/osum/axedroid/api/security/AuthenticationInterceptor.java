package com.osum.axedroid.api.security;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * A request interceptor that injects the API Key Header into requests, and signs messages, whenever required.
 */
public class AuthenticationInterceptor implements Interceptor {

    /**
     * HTTP Header to be used for API-KEY authentication.
     */
    public static final String API_KEY_HEADER = "X-MBX-APIKEY";

    /**
     * Decorator to indicate that an endpoint requires an API key.
     */
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY = "APIKEY";
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY_HEADER = ENDPOINT_SECURITY_TYPE_APIKEY + ": #";

    /**
     * Decorator to indicate that an endpoint requires a signature.
     */
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED = "SIGNED";
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = ENDPOINT_SECURITY_TYPE_SIGNED + ": #";

    public static final String ENDPOINT_SECURITY_TYPE_BEARER = "Authorization: Bearer";
    public static final String ENDPOINT_SECURITY_TYPE_BEARER_HEADER = ENDPOINT_SECURITY_TYPE_BEARER + ": #";

    /**
     * Default receiving window.
     */
    public static final long DEFAULT_RECEIVING_WINDOW = 60_000L;

    /**
     * Default margin receiving window.
     */
    public static final long DEFAULT_MARGIN_RECEIVING_WINDOW = 5_000L;

    private final String apiKey;

    private final String secret;

    public AuthenticationInterceptor(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder newRequestBuilder = original.newBuilder();

        boolean isApiKeyRequired = original.header(ENDPOINT_SECURITY_TYPE_APIKEY) != null;
        boolean isSignatureRequired = original.header(ENDPOINT_SECURITY_TYPE_SIGNED) != null;
        boolean isBearerRequired = original.header("Authorization") != null;
        newRequestBuilder.removeHeader(ENDPOINT_SECURITY_TYPE_APIKEY)
                .removeHeader(ENDPOINT_SECURITY_TYPE_SIGNED)
                .removeHeader("Authorization");

        if(isBearerRequired)
            newRequestBuilder.addHeader("Authorization", "Bearer " + apiKey);
        // Endpoint requires sending a valid API-KEY
        if (isApiKeyRequired || isSignatureRequired) {
            newRequestBuilder.addHeader(API_KEY_HEADER, apiKey);
        }

        // Endpoint requires signing the payload
        if (isSignatureRequired) {
            String payload = original.url().query();
            if (!TextUtils.isEmpty(payload)) {
                String signature = HmacSHA256Signer.sign(payload, secret);
                HttpUrl signedUrl = original.url().newBuilder().addQueryParameter("signature", signature).build();
                newRequestBuilder.url(signedUrl);
            }
        }

        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }

    /**
     * Extracts the request body into a String.
     *
     * @return request body as a string
     */
    @SuppressWarnings("unused")
    private static String bodyToString(RequestBody request) {
        try (final Buffer buffer = new Buffer()) {
            final RequestBody copy = request;
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthenticationInterceptor that = (AuthenticationInterceptor) o;
        return Objects.equals(apiKey, that.apiKey) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, secret);
    }
}