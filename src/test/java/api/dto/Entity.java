package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Entity {

    private String title;

    private boolean verified;

    private Addition addition;

    private List<Integer> important_numbers;
}
