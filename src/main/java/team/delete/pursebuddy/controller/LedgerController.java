package team.delete.pursebuddy.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.delete.pursebuddy.dto.AjaxResult;
import team.delete.pursebuddy.dto.LedgerDto;
import team.delete.pursebuddy.service.LedgerService;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Validated
@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {
    final LedgerService ledgerService;

    /**
     * 新建账本
     *
     * @param ledgerDto 新建账本有关数据
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping
    public Object insert(@RequestBody LedgerDto ledgerDto) {
        return AjaxResult.SUCCESS(ledgerService.create(StpUtil.getLoginIdAsInt(),
                ledgerDto.getName(),
                ledgerDto.getIsPublic()));
    }

    /**
     * 获取当前用户参与的账本数据
     *
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping
    public Object query() {
        return AjaxResult.SUCCESS(ledgerService.query(StpUtil.getLoginIdAsInt()));
    }

    /**
     * 修改账本数据
     *
     * @return json数据，包含状态码和状态信息
     */
    @PutMapping
    public Object update(@RequestBody LedgerDto ledgerDto) {
        ledgerService.update(StpUtil.getLoginIdAsInt(),
                ledgerDto.getId(),
                ledgerDto.getName(),
                ledgerDto.getIsPublic());
        return AjaxResult.SUCCESS();
    }

    /**
     * 删除账本数据
     *
     * @param id 需要被删除的账本 id
     * @return json数据，包含状态码和状态信息
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        ledgerService.delete(StpUtil.getLoginIdAsInt(), id);
        return AjaxResult.SUCCESS();
    }

    /**
     * 通过名称查询账本信息
     *
     * @param ledgerId 查询的关键词
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/query")
    public Object search(@RequestParam(value = "ledger_id") Integer ledgerId) {
        return AjaxResult.SUCCESS(ledgerService.query(StpUtil.getLoginIdAsInt(), ledgerId));
    }

    /**
     * 加入账本
     *
     * @param password 对应账本的密钥
     * @return json数据，包含状态码和状态信息
     */
    @PostMapping("/join")
    public Object join(@RequestParam String password) {
        ledgerService.join(StpUtil.getLoginIdAsInt(), password);
        return AjaxResult.SUCCESS();
    }
}
