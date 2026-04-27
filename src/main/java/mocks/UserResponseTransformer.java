package mocks;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

import static utils.Helpers.generateData;

public class UserResponseTransformer extends ResponseDefinitionTransformer {
    @Override
    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition,
                                        FileSource files, Parameters parameters) {
        String page = request.queryParameter("page").isPresent() ? request.queryParameter("page").firstValue() : "1";
        String size = request.queryParameter("size").isPresent() ? request.queryParameter("size").firstValue() : "20";
        String age = request.queryParameter("age").isPresent() ? request.queryParameter("age").firstValue() : "";
        String gender = request.queryParameter("gender").isPresent() ? request.queryParameter("gender").firstValue() : "all";
        String sort = request.queryParameter("sort").isPresent() ? request.queryParameter("sort").firstValue() : "asc";
        String sortBy = request.queryParameter("sortby").isPresent() ? request.queryParameter("sortby").firstValue() : "id";

        return ResponseDefinitionBuilder
                .like(responseDefinition)
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(generateData(page, size, age, gender, sort, sortBy))
                .build();
    }

    @Override
    public String getName() {
        return "userResponseTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}