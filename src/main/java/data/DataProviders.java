package data;

import models.ErrorResponse;
import models.User;
import models.Users;
import org.testng.annotations.DataProvider;

import java.util.Comparator;
import java.util.Map;

import static data.Data.usersData;
import static data.Messages.*;
import static utils.Helpers.pagination;

public class DataProviders {
    @DataProvider(name = "filterProviderPositive")
    public Object[][] filterProviderPositive() {
        return new Object[][]{
                {
                        "filterByAge",
                        Map.of("page", 1, "size", 200, "age", 60),
                        200,
                        Users.builder().total(5).page(1).size(200).data(
                                usersData.getData().stream()
                                        .filter((u) -> u.getAge() == 60)
                                        .toList()
                        ).build(),
                        5
                },
                {
                        "filterByGender",
                        Map.of("page", 1, "size", 200, "gender", "female"),
                        200,
                        Users.builder().total(57).page(1).size(200).data(
                                usersData.getData().stream()
                                        .filter((u) -> u.getGender().equalsIgnoreCase("female"))
                                        .toList()
                        ).build(),
                        57
                },
                {
                        "filterByAgeAndGender",
                        Map.of("page", 1, "size", 200, "gender", "female", "age", 60),
                        200,
                        Users.builder().total(1).page(1).size(200).data(
                                usersData.getData().stream()
                                        .filter((u) -> (
                                                u.getGender().equalsIgnoreCase("female")
                                                        && u.getAge() == 60
                                        ))
                                        .toList()

                        ).build(),
                        1
                }
        };
    }

    @DataProvider(name = "sortProviderPositive")
    public Object[][] sortProviderPositive() {
        return new Object[][]{
                {
                        "sortASC", //default sort by value is: id
                        Map.of("page", 1, "size", 200, "sort", "asc"),
                        200,
                        Users.builder().total(200).page(1).size(200).data(
                                usersData.getData().stream()
                                        .sorted(Comparator.comparingInt(User::getId))
                                        .toList()
                        ).build()
                },
                {
                        "sortDESC", //default sort by value is: id
                        Map.of("page", 1, "size", 200, "sort", "desc"),
                        200,
                        Users.builder().total(200).page(1).size(200).data(
                                usersData.getData().stream()
                                        .sorted(Comparator.comparingInt(User::getId).reversed())
                                        .toList()
                        ).build()
                },
                {
                        "sortByAgeDESC", //change default sort value
                        Map.of("page", 1, "size", 200, "sort", "desc", "sortby", "age"),
                        200,
                        Users.builder().total(200).page(1).size(200).data(
                                usersData.getData().stream()
                                        .sorted(Comparator.comparingInt(User::getAge).reversed())
                                        .toList()
                        ).build()
                }
        };
    }

    @DataProvider(name = "paginationProviderPositive")
    public Object[][] paginationProviderPositive() {
        return new Object[][]{
                {
                        "pagination",
                        Map.of("page", 2, "size", 10),
                        200,
                        pagination(usersData.getData(), 2, 10)
                },
                {
                        "paginationWithFilterByGender",
                        Map.of("page", 4, "size", 10, "gender", "male"),
                        200,
                        Users.builder().total(143).page(4).size(10).data(
                                pagination(
                                        usersData.getData().stream()
                                                .filter((u) -> u.getGender().equalsIgnoreCase("male"))
                                                .toList(), 4, 10
                                ).getData()
                        ).build()
                }
        };
    }

    @DataProvider(name = "invalidParameterProviderNegative")
    public Object[][] invalidParameterProviderNegative() {
        return new Object[][]{
                {
                        "invalidAge",
                        Map.of("age", "invalid parameter"),
                        400,
                        ErrorResponse.builder().error("invalid_parameter").message(PARAMETER_MUST_BE_POSITIVE).build()
                },
                {
                        "invalidGender",
                        Map.of("gender", "unknown"),
                        422,
                        ErrorResponse.builder().error("invalid_parameter").message(GENDER_ERROR_MESSAGE).build()
                },
                {
                        "invalidSort",
                        Map.of("sort", "invalid"),
                        400,
                        ErrorResponse.builder().error("invalid_parameter").message(SORT_ERROR_MESSAGE).build()
                },
                {
                        "invalidSortBy",
                        Map.of("sortby", "invalid"),
                        400,
                        ErrorResponse.builder().error("invalid_parameter").message(SORT_BY_ERROR_MESSAGE).build()
                },
                {
                        "invalidPaginationPage",
                        Map.of("page", "0"),
                        400,
                        ErrorResponse.builder().error("invalid_parameter").message(PARAMETER_MUST_BE_POSITIVE).build()
                },
                {
                        "invalidPaginationSize",
                        Map.of("size", "-1"),
                        400,
                        ErrorResponse.builder().error("invalid_parameter").message(PARAMETER_MUST_BE_POSITIVE).build()
                }
        };
    }
}
