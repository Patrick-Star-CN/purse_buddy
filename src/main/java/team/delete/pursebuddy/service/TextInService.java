package team.delete.pursebuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.constant.TextInApi;
import team.delete.pursebuddy.dto.ImageItemDto;
import team.delete.pursebuddy.dto.TrainTicketDto;
import team.delete.pursebuddy.util.TextInFetch;


/**
 * @author Patrick_Star
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
public class TextInService {
    final ExpensesRecordService expensesRecordService;

    public int insertByTrainTicket(int userId, byte[] img) throws JsonProcessingException {
        Object result = TextInFetch.post(TextInApi.TRAIN_TICKET, img);
        ObjectMapper objectMapper = new ObjectMapper();
        TrainTicketDto trainTicketDto = objectMapper.convertValue(result, TrainTicketDto.class);
        double value = 0.0;
        String departureStation = null;
        String arrivalStation = null;
        String dateRaw = null;
        for (ImageItemDto item : trainTicketDto.getItemList()) {
            if ("price".equals(item.getKey())) {
                value = Double.parseDouble(item.getValue());
            } else if ("departure_date".equals(item.getKey())) {
                dateRaw = item.getValue().substring(0, 10);
            } else if ("arrival_station".equals(item.getKey())) {
                arrivalStation = item.getValue();
            } else if ("departure_station".equals(item.getKey())) {
                departureStation = item.getValue();
            }
        }
        String remark = departureStation + " 到 " + arrivalStation + " 的火车票";
        return expensesRecordService.insert(userId, value, true, "traffic", remark, dateRaw);
    }
}
