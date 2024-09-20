package com.jamesstorr.jokes_service.infrastructure.config;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import net.thucydides.core.steps.TestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("cucumber-glue")
public class TestExecutionContextHolder {

    private RequestSpecification requestSpec;

    private Response response;

    public void setRequestSpec(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
