package api.dto;

import java.util.List;

public class Entity {
    private Integer id;
    private String title;
    private boolean verified;
    private Addition addition;
    private List<Integer> important_numbers;

    public Entity() {}

    public Entity(Integer id, String title, boolean verified, Addition addition, List<Integer> important_numbers) {
        this.id = id;
        this.title = title;
        this.verified = verified;
        this.addition = addition;
        this.important_numbers = important_numbers;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public Addition getAddition() { return addition; }
    public void setAddition(Addition addition) { this.addition = addition; }

    public List<Integer> getImportant_numbers() { return important_numbers; }
    public void setImportant_numbers(List<Integer> important_numbers) { this.important_numbers = important_numbers; }
}
