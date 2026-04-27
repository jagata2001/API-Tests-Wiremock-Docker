package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import models.Users;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static data.Data.usersData;

public class Helpers {
    public static String generateData(String page, String size, String age, String gender, String sort, String sortBy) {
        page = page.isEmpty() ? "1" : page;
        size = size.isEmpty() ? "20" : size;
        gender = gender.isEmpty() ? "all" : gender;
        sort = sort.isEmpty() ? "asc" : sort;
        sortBy = sortBy.isEmpty() ? "id" : sortBy;

        List<User> data = usersData.getData().stream().toList();
        data = universalSort(data, sortBy, sort);
        if (!gender.equals("all")) {
            String finalGender = gender;
            data = data.stream()
                    .filter((u) -> u.getGender().equalsIgnoreCase(finalGender))
                    .toList();
        }

        if (!age.isEmpty()) {
            int finalAage = Integer.parseInt(age);
            data = data.stream()
                    .filter((u) -> u.getAge() == finalAage)
                    .toList();
        }

        try {
            return new ObjectMapper().writeValueAsString(pagination(data, Integer.parseInt(page), Integer.parseInt(size)));
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public static Users loadData(String fileName) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new RuntimeException("Can't find file: %s".formatted(fileName));
            }
            return new ObjectMapper().readValue(is, Users.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<User> universalSort(List<User> data, String field, String isAsc) {
        Comparator<User> comparator = switch (field.toLowerCase()) {
            case "id" -> Comparator.comparingInt(User::getId);
            case "name" -> Comparator.comparing(User::getName);
            case "age" -> Comparator.comparingInt(User::getAge);
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };

        if (isAsc.equals("desc")) {
            comparator = comparator.reversed();
        }
        return data.stream().sorted(comparator).toList();
    }

    public static Users pagination(List<User> data, int page, int size) {
        page--;
        int totalElements = data.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        return Users.builder()
                .page(++page)
                .size(size)
                .total(totalElements)
                .data(startIndex > totalElements ? Collections.emptyList() : data.subList(startIndex, endIndex))
                .build();
    }
}
