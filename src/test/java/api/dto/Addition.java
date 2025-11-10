package api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Addition {

    private String additional_info;

    private Integer additional_number;
}
