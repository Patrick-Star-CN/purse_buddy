package team.delete.pursebuddy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrainTicketDto {
    private String type;
    private String typeDescription;
    private int imageAngle;
    private int rotatedImageWidth;
    private int rotatedImageHeight;
    private String kind;
    private String kindDescription;
    private ArrayList<ImageItemDto> itemList;
}
