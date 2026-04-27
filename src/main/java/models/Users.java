package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Comparator<Users> {
    private Integer page;
    private Integer size;
    private int total;
    private List<User> data;

    @Override
    public int compare(Users o1, Users o2) {
        if (o1.getData().size() != o2.getData().size()) {
            return Integer.compare(o1.getData().size(), o2.getData().size());
        }
        return o1.getData().equals(o2.getData()) ? 0 : 1;
    }
}
