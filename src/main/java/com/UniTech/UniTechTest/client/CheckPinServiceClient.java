package com.UniTech.UniTechTest.client;

import com.UniTech.UniTechTest.dto.CheckPinResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class CheckPinServiceClient {
    @Autowired
    private  RestTemplate template;

    @Value("${server.baseUrl}")
    private String path;

    public CheckPinResponse checkPin(String pin) {
        return template.getForObject(path+"/mock/checkPin/" + pin, CheckPinResponse.class);
    }
}
