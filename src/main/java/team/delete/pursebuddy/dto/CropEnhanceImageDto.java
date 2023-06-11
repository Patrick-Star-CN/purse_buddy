package team.delete.pursebuddy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CropEnhanceImageDto {
    private int croppedWidth;
    private int croppedHeight;
    private String image;
    private ArrayList<Integer> position;
    private int angle;
}
