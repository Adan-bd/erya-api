package spider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());

        //不加可能会出现异常
        //Could not extract response: no suitable HttpMessageConverter found for response type [class ]

//        MediaType[] mediaTypes = new MediaType[]{
//                MediaType.APPLICATION_JSON,
//                MediaType.APPLICATION_OCTET_STREAM,
//                MediaType.TEXT_HTML,
//                MediaType.TEXT_PLAIN,
//                MediaType.TEXT_XML,
//                MediaType.APPLICATION_STREAM_JSON,
//                MediaType.APPLICATION_ATOM_XML,
//                MediaType.APPLICATION_FORM_URLENCODED,
//                MediaType.APPLICATION_JSON_UTF8,
//                MediaType.APPLICATION_PDF,
//        };
        MediaType[] mediaTypes = new MediaType[]{
                MediaType.ALL
        };

        converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));
        restTemplate.getMessageConverters().add(converter);

        restTemplate.setRequestFactory(getUnsafeClientHttpRequestFactory());
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory getUnsafeClientHttpRequestFactory() {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
            sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection,
                                             @NotNull String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(
                            sslContext.getSocketFactory());
                }
            }
        };
    }

}
