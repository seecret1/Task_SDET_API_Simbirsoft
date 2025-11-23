package api.dto;

import java.util.List;

public class Entity {

    private String title;

    private boolean verified;

    private Addition addition;

    private List<Integer> important_numbers;

    public Entity(String title, boolean verified, Addition addition, List<Integer> important_numbers) {
        this.title = title;
        this.verified = verified;
        this.addition = addition;
        this.important_numbers = important_numbers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVerified() {
        return verified;
    }

    public Addition getAddition() {
        return addition;
    }

    public List<Integer> getImportant_numbers() {
        return important_numbers;
    }

    public void setAddition(Addition addition) {
        this.addition = addition;
    }

    public void setImportant_numbers(List<Integer> important_numbers) {
        this.important_numbers = important_numbers;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
