package api.dto;

public class Addition {

    private String additional_info;

    private Integer additional_number;

    public Addition(String additional_info, Integer additional_number) {
        this.additional_info = additional_info;
        this.additional_number = additional_number;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public Integer getAdditional_number() {
        return additional_number;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public void setAdditional_number(Integer additional_number) {
        this.additional_number = additional_number;
    }
}
