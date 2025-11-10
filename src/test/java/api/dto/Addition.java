package api.dto;

public class Addition {
    private String additional_info;
    private int additional_number;

    public Addition() {}

    public Addition(String additional_info, int additional_number) {
        this.additional_info = additional_info;
        this.additional_number = additional_number;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public int getAdditional_number() {
        return additional_number;
    }

    public void setAdditional_number(int additional_number) {
        this.additional_number = additional_number;
    }
}
