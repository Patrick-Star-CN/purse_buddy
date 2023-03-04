package team.delete.pursebuddy.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.dto.ExpensesRecordDto;
import team.delete.pursebuddy.service.ExpensesRecordService;
import team.delete.pursebuddy.service.TextInService;

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
    @PostMapping("/insert")
    public Object insert(@RequestBody ExpensesRecordDto expensesRecordDto) {
        expensesRecordService.insert(StpUtil.getLoginIdAsInt(),
                expensesRecordDto.getValue(),
                expensesRecordDto.getType(),
                expensesRecordDto.getKind(),
                expensesRecordDto.getDate());
        return AjaxResult.SUCCESS();
    }

    /**
     * 删除指定消费记录
     *
     * @param id 消费记录 id
     * @return json数据，包含状态码和状态信息
     */
    @DeleteMapping("/delete/{id}")
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
    @PostMapping("/update")
    public Object update(@RequestBody ExpensesRecordDto expensesRecordDto) {
        expensesRecordService.update(expensesRecordDto.getId(),
                StpUtil.getLoginIdAsInt(),
                expensesRecordDto.getValue(),
                expensesRecordDto.getType(),
                expensesRecordDto.getKind(),
                expensesRecordDto.getDate());
        return AjaxResult.SUCCESS();
    }

    /**
     * 获取分页后符合条件的消费记录
     *
     * @param date 查询的具体日期
     * @param month 查询的月份
     * @param year 查询的年份
     * @param type 查询的收支类型
     * @param pageNum 查询的页数
     * @param pageSize 查询的页大小
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/fetch")
    public Object fetch(
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "type", required = false) Boolean type,
            @RequestParam(value = "page_num") Integer pageNum,
            @RequestParam(value = "page_size") Integer pageSize) {
        return AjaxResult.SUCCESS(expensesRecordService.getPageable(StpUtil.getLoginIdAsInt(),
                year, month, date, type, pageNum, pageSize));
    }

    /**
     * 通过识别火车票添加新的消费记录
     *
     * @param map 传输图片数据的 body
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping("/train_ticket")
    public Object insertByPhoto(@RequestBody Map<String, Object> map) {
        textInService.insertByTrainTicket(StpUtil.getLoginIdAsInt(),
                ((String) map.get("photo")).getBytes());
        return AjaxResult.SUCCESS();
    }

}
