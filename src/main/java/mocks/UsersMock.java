package mocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.http.MultiValue;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import models.ErrorResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static data.Messages.*;

public class UsersMock {
    public static void setupUsersFilterableStub() throws JsonProcessingException {
        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .andMatching(request -> (
                                !request.queryParameter("page").isPresent()
                                        && !request.queryParameter("size").isPresent()
                                        && !request.queryParameter("age").isPresent()
                                        && !request.queryParameter("gender").isPresent()
                                        && !request.queryParameter("sort").isPresent()
                                        && !request.queryParameter("sortby").isPresent()
                        )
                                ? MatchResult.exactMatch()
                                : MatchResult.noMatch()
                )
                .atPriority(1)
                .willReturn(aResponse()
                        .withStatus(500)));

        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("page", or(matching("[1-9][0-9]*$|$"), absent()))
                .withQueryParam("size", or(matching("[1-9][0-9]*$|$"), absent()))
                .withQueryParam("age", or(matching("[1-9][0-9]*$|$"), absent()))
                .withQueryParam("gender", or(matching("(female|male)|$"), absent()))
                .withQueryParam("sort", or(matching("(asc|desc)|$"), absent()))
                .withQueryParam("sortby", or(matching("(name|age|id)|$"), absent()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withTransformers("userResponseTransformer")));

        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .andMatching(request ->
                        isInvalid(request.queryParameter("page")) ||
                                isInvalid(request.queryParameter("size")) ||
                                isInvalid(request.queryParameter("age"))
                                ? MatchResult.exactMatch()
                                : MatchResult.noMatch()
                )
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsString(new ErrorResponse("invalid_parameter", PARAMETER_MUST_BE_POSITIVE)))));

        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("gender", matching("^(?!(male|female)$|$).*$"))
                .willReturn(aResponse()
                        .withStatus(422)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsString(new ErrorResponse("invalid_parameter", GENDER_ERROR_MESSAGE)))));

        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("sort", matching("^(?!(asc|desc)$|$).*$"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsString(new ErrorResponse("invalid_parameter", SORT_ERROR_MESSAGE)))));

        MockServerManager.getInstance().stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("sortby", matching("^(?!(name|age|id)$|$).*$"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsString(new ErrorResponse("invalid_parameter", SORT_BY_ERROR_MESSAGE)))));

    }

    private static boolean isInvalid(MultiValue parameter) {
        if (!parameter.isPresent()) return false;
        String value = parameter.firstValue();
        return !value.matches("^[1-9][0-9]*$|$");
    }
}
