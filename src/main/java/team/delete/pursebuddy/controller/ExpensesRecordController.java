package team.delete.pursebuddy.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.dto.ExpensesRecordDto;
import team.delete.pursebuddy.service.ExpensesRecordService;
import team.delete.pursebuddy.service.TextInService;

import java.io.IOException;
import java.util.Map;

/**
 * @author Patrick_Star
 * @version 1.2
 */
@Validated
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpensesRecordController {
    final ExpensesRecordService expensesRecordService;
    final TextInService textInService;

    /**
     * 添加新的消费记录
     *
     * @param expensesRecordDto 新建消费记录有关数据
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping
    public Object insert(@RequestBody ExpensesRecordDto expensesRecordDto) {
        return AjaxResult.SUCCESS(expensesRecordService.insert(StpUtil.getLoginIdAsInt(),
                expensesRecordDto.getLedgerId(),
                expensesRecordDto.getValue(),
                expensesRecordDto.getType(),
                expensesRecordDto.getKind(),
                expensesRecordDto.getRemark(),
                expensesRecordDto.getDate()));
    }

    /**
     * 删除指定消费记录
     *
     * @param id 消费记录 id
     * @return json数据，包含状态码和状态信息
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        expensesRecordService.delete(id, StpUtil.getLoginIdAsInt());
        return AjaxResult.SUCCESS();
    }

    /**
     * 修改指定消费记录
     *
     * @param expensesRecordDto 新建消费记录有关数据
     * @return json数据，包含状态码和状态信息
     */
    @PutMapping
    public Object update(@RequestBody ExpensesRecordDto expensesRecordDto) {
        expensesRecordService.update(expensesRecordDto.getId(),
                StpUtil.getLoginIdAsInt(),
                Double.parseDouble(expensesRecordDto.getValue()),
                expensesRecordDto.getType(),
                expensesRecordDto.getKind(),
                expensesRecordDto.getDate());
        return AjaxResult.SUCCESS();
    }

    /**
     * 获取分页后符合条件的消费记录
     *
     * @param date     查询的具体日期
     * @param month    查询的月份
     * @param year     查询的年份
     * @param type     查询的收支类型
     * @param pageNum  查询的页数
     * @param pageSize 查询的页大小
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping
    public Object fetch(@RequestParam(value = "date", required = false) String date,
                        @RequestParam(value = "month", required = false) String month,
                        @RequestParam(value = "year", required = false) String year,
                        @RequestParam(value = "type", required = false) Boolean type,
                        @RequestParam(value = "page_num") Integer pageNum,
                        @RequestParam(value = "page_size") Integer pageSize,
                        @RequestParam(value = "ledger_id") Integer ledgerId) {
        return AjaxResult.SUCCESS(expensesRecordService.getPageable(StpUtil.getLoginIdAsInt(),
                ledgerId, year, month, date, type, pageNum, pageSize));
    }

    /**
     * 通过识别火车票添加新的消费记录
     *
     * @param photo 图片文件
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping("/train_ticket")
    public Object insertByTicket(@RequestParam("photo") MultipartFile photo) throws IOException {
        return AjaxResult.SUCCESS(textInService.insertByTrainTicket(photo.getBytes()));
    }

    /**
     * 通过识别火车票添加新的消费记录
     *
     * @param map 请求体，包括声音转换来的文字
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping("/voice")
    public Object insertByVoice(@RequestBody Map<String, String> map) {
        String sentence = map.get("sentence");
        return AjaxResult.SUCCESS(textInService.insertByVoice(sentence));
    }

    /**
     * 通过识别外卖小票添加新的消费记录
     *
     * @param photo 图片文件
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping("/photo")
    public Object insertByPhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
        return AjaxResult.SUCCESS(textInService.insertByCommonImg(photo.getBytes()));
    }
}
