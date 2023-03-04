package team.delete.pursebuddy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.constant.TextInApi;
import team.delete.pursebuddy.dto.TextInResponse;
import team.delete.pursebuddy.util.TextInFetch;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TextInService {
    final ExpensesRecordService expensesRecordService;

    public void insertByTrainTicket(int userId, byte[] img) {
        ResponseEntity<TextInResponse> resp = TextInFetch.post(TextInApi.TRAIN_TICKET, img);
        System.out.println(resp.getBody().getMessage());
        System.out.println(resp.getBody().getResult());
    }
}
