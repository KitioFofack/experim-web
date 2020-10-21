package ca.aretex.irex.experim.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class RestClient<T, R> implements Function<T, Optional<R>> {
    protected final transient ObjectMapper objectMapper = new ObjectMapper();
    protected final String serverBaseUrl;
    protected final RestTemplate restTemplate;
    protected final Class<R> responseClassTag;

    private String objectToJson(T requestObject) throws JsonProcessingException {
        return objectMapper.writeValueAsString(requestObject);
    }

    private HttpEntity<String> jsonToEntity(String requestJson){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<String>(requestJson, httpHeaders);
    }

    private Optional<R> getResponse(RestTemplate restTemplate, T requestObject){
        try {
            String requestJson = objectToJson(requestObject);
            log.info("sending requestJson={}", requestJson);
            R response = restTemplate.postForObject(serverBaseUrl, jsonToEntity(requestJson), responseClassTag);
            log.info("receiving response={}", response);
            return response == null ? Optional.empty() : Optional.of(response);
        } catch (Exception exception){
            log.error("could not get response from requestObject={} due to", requestObject, exception);
        }
        return Optional.empty();
    }


    @Override
    public Optional<R> apply(T t) {
        return getResponse(restTemplate, t);
    }
}
