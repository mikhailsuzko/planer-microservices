package com.sma.micro.planner.plannerutils.rest;

import com.sma.micro.planner.plannerentity.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserRestBuilder {
    private static final String BASE_URL = "http://localhost:8765/planner-users/user/";


    public boolean userExist(Long id) {
        var restTemplate = new RestTemplate();
        var request = new HttpEntity<>(id);
        try {
            var response = restTemplate.exchange(BASE_URL + "id", HttpMethod.POST, request, User.class);
            if (response.getStatusCode() == HttpStatus.OK) { // если статус был 200
                return true;
            }
        } catch (Exception e) {
            log.error("An error has occurred: {}", e.getMessage());
        }
        return false;
    }
}
