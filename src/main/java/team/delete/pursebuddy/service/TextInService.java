package team.delete.pursebuddy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.constant.TextInApi;
import team.delete.pursebuddy.dto.*;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.LedgerPermissionMapper;
import team.delete.pursebuddy.util.IntegerUtil;
import team.delete.pursebuddy.util.TextInFetch;

import java.util.Base64;
import java.util.Calendar;

/**
 * @author Patrick_Star
 * @version 1.2
 */
@Service
@RequiredArgsConstructor
public class TextInService {
    final ExpensesRecordService expensesRecordService;
    final LedgerPermissionMapper ledgerPermissionMapper;

    public ExpensesRecordDto insertByTrainTicket(byte[] img) {
        Object result = TextInFetch.post(TextInApi.TRAIN_TICKET, processImage(img));
        ObjectMapper objectMapper = new ObjectMapper();
        TrainTicketDto trainTicketDto = objectMapper.convertValue(result, TrainTicketDto.class);
        String value = null;
        String departureStation = null;
        String arrivalStation = null;
        String dateRaw = null;
        for (TicketItemDto item : trainTicketDto.getItemList()) {
            if ("price".equals(item.getKey())) {
                value = item.getValue();
            } else if ("departure_date".equals(item.getKey())) {
                dateRaw = item.getValue().substring(0, 10);
            } else if ("arrival_station".equals(item.getKey())) {
                arrivalStation = item.getValue();
            } else if ("departure_station".equals(item.getKey())) {
                departureStation = item.getValue();
            }
        }
        String remark = departureStation + " 到 " + arrivalStation + " 的火车票";
        return ExpensesRecordDto.builder()
                .type(true)
                .value(value)
                .kind("traffic")
                .remark(remark)
                .date(dateRaw).build();
    }

    public ExpensesRecordDto insertByVoice(String sentence) {
        String dateRaw = null;
        Calendar calendar = Calendar.getInstance();
        if (sentence.contains("年") && sentence.contains("月") && sentence.contains("日")) {
            dateRaw = sentence.split("日", 2)[0];
            dateRaw = dateRaw.replaceFirst("年", "-");
            dateRaw = dateRaw.replaceFirst("月", "-");
        } else if (sentence.contains("月") && sentence.contains("日")) {
            dateRaw = sentence.split("日", 2)[0];
            dateRaw = dateRaw.replaceFirst("月", "-");
        } else if (sentence.contains("今天")) {
            sentence = sentence.substring(2);
            dateRaw = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DATE);
        } else if (sentence.contains("昨天")) {
            sentence = sentence.substring(2);
            calendar.add(Calendar.DATE, -1);
            dateRaw = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +calendar.get(Calendar.DATE);
        }
        String remark;
        String value;
        String[] s1;
        boolean type;
        if (sentence.contains("花了")) {
            s1 = sentence.split("花了", 2);
            type = true;
        } else if (sentence.contains("赚了")) {
            s1 = sentence.split("赚了", 2);
            type = false;
        } else {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        remark = s1[0];
        value = s1[1].substring(0, s1[1].length() - 1);
        return ExpensesRecordDto.builder()
                .date(dateRaw)
                .remark(remark)
                .value(value)
                .type(type).build();
    }

    public ExpensesRecordDto insertByCommonImg(byte []img) {
        Object result = TextInFetch.post(TextInApi.COMMON_RECOGNIZE, processImage(img));
        ObjectMapper objectMapper = new ObjectMapper();
        ImageDto imageDto = objectMapper.convertValue(result, ImageDto.class);
        String preText;
        int valueFlag = 0;
        int subValueFlag = 0;
        int timeFlag = 0;
        String value = null;
        String subValue = null;
        String remark = imageDto.getLines().get(0).getText();
        String dateRaw = null;
        for (ImageItemDto item : imageDto.getLines()) {
            preText = item.getText();
            if (valueFlag == 0 && (item.getText().contains("应收") || item.getText().contains("应付"))) {
                String[] split = item.getText().split("：");
                if (split.length != 1) {
                    value = split[split.length - 1];
                    valueFlag = 2;
                } else if (IntegerUtil.isNumber(preText)) {
                    value = preText;
                    valueFlag = 2;
                } else {
                    valueFlag = 1;
                }
            } else if (valueFlag == 1) {
                value = item.getText();
                valueFlag = 2;
            }
            if (subValueFlag == 0 && item.getText().contains("合计")) {
                String[] split = item.getText().split("：");
                if (split.length != 1) {
                    subValue = split[split.length - 1];
                    subValueFlag = 2;
                } else if (IntegerUtil.isNumber(preText)) {
                    subValue = preText;
                    subValueFlag = 2;
                } else {
                    subValueFlag = 1;
                }
            } else if (subValueFlag == 1) {
                subValue = item.getText();
                subValueFlag = 2;
            }
            if (timeFlag == 0 && item.getText().contains("时间")) {
                String[] split = item.getText().split("：");
                if (split.length != 1) {
                    dateRaw = split[1].split(" ")[0];
                    timeFlag = 2;
                } else {
                    timeFlag = 1;
                }
            } else if (timeFlag == 1) {
                dateRaw = item.getText().split(" ")[0];
                timeFlag = 2;
            }
        }
        return ExpensesRecordDto.builder()
                .type(true)
                .value(value != null ? value : subValue)
                .kind("")
                .remark(remark)
                .date(dateRaw).build();
    }

    private byte[] processImage(byte []img) {
        ObjectMapper objectMapper = new ObjectMapper();
        Base64.Decoder decoder = Base64.getDecoder();
        Object result = TextInFetch.post(TextInApi.CROP_ENHANCE, img);
        CropEnhanceDto cropEnhanceDto = objectMapper.convertValue(result, CropEnhanceDto.class);
        result = TextInFetch.post(TextInApi.DEMOIRE, decoder.decode(cropEnhanceDto.getImageList().get(0).getImage()));
        ProcessImageDto processImageDto = objectMapper.convertValue(result, ProcessImageDto.class);
        return decoder.decode(processImageDto.getImage());
    }
}
