package team.delete.pursebuddy.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
public class ImageItemDto {
    private String key;
    private String value;
    private String description;
    private ArrayList<Integer> position;
}
