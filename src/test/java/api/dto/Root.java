package api.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Root{
    public ArrayList<Entity> entities;
    public int perPage;
    public int page;
}