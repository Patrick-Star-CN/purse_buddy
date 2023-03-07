package team.delete.pursebuddy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.dto.LedgerDto;
import team.delete.pursebuddy.entity.Ledger;
import team.delete.pursebuddy.entity.LedgerPermission;
import team.delete.pursebuddy.entity.User;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.LedgerMapper;
import team.delete.pursebuddy.mapper.LedgerPermissionMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackForClassName = "RuntimeException")
@CacheConfig(cacheNames = "ExpireOneMin")
public class LedgerService {
    final LedgerMapper ledgerMapper;
    final LedgerPermissionMapper ledgerPermissionMapper;

    /**
     * 创建用户的默认记账本
     */
    public void createDefault(User user) {
        Ledger ledger = Ledger.builder()
                .name(user.getUsername() + "的记账本")
                .isPublic(false)
                .ownerId(user.getUserId()).build();
        ledgerMapper.insert(ledger);
        ledgerPermissionMapper.insert(LedgerPermission.builder()
                .ledgerId(ledger.getId())
                .userId(user.getUserId()).build());
    }

    /**
     * 创建记账本
     */
    public int create(int userId, String name, boolean isPublic, String password) {
        if (isPublic && password == null) {
            throw new AppException(ErrorCode.LEDGER_PUBLIC_WITHOUT_PASSWORD_ERROR);
        }
        Ledger ledger = Ledger.builder()
                .name(name)
                .password(password)
                .isPublic(isPublic)
                .ownerId(userId).build();
        ledgerMapper.insert(ledger);
        ledgerPermissionMapper.insert(LedgerPermission.builder()
                .ledgerId(ledger.getId())
                .userId(userId).build());
        return ledger.getId();
    }

    /**
     * 查询用户参与的所有账本
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_'+#userId")
    public List<LedgerDto> query(int userId) {
        List<LedgerPermission> ledgerPermissions = ledgerPermissionMapper.selectList(new QueryWrapper<LedgerPermission>()
                .eq("user_id", userId));
        List<LedgerDto> ledgers = new ArrayList<>();
        for (LedgerPermission ledgerPermission : ledgerPermissions) {
            Ledger ledger = ledgerMapper.selectById(ledgerPermission.getLedgerId());
            ledgers.add(LedgerDto.builder()
                    .id(ledger.getId())
                    .name(ledger.getName()).build());
        }
        return ledgers;
    }

    /**
     * 修改用户拥有的账本的资料
     */
    public void update(int userId, int ledgerId, String name, boolean isPublic, String password) {
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("owner_id", userId)
                .eq("id", ledgerId));
        if (ledger == null) {
            throw new AppException(ErrorCode.LEDGER_PERMISSION_ERROR);
        }
        if (isPublic && password == null) {
            throw new AppException(ErrorCode.LEDGER_PUBLIC_WITHOUT_PASSWORD_ERROR);
        }
        ledger.setName(name);
        ledger.setPassword(password);
        ledger.setIsPublic(isPublic);
        ledgerMapper.updateById(ledger);
    }

    /**
     * 删除用户拥有的账本
     */
    public void delete(int userId, int ledgerId) {
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("owner_id", userId)
                .eq("id", ledgerId));
        if (ledger == null) {
            throw new AppException(ErrorCode.LEDGER_PERMISSION_ERROR);
        }
        ledgerMapper.deleteById(ledgerId);
    }

    /**
     * 加入公开的账本
     */
    public void join(int userId, int ledgerId, String password) {
        LedgerPermission ledgerPermission = ledgerPermissionMapper.selectOne(new QueryWrapper<LedgerPermission>()
                .eq("user_id", userId)
                .eq("ledger_id", ledgerId));
        if (ledgerPermission != null) {
            throw new AppException(ErrorCode.LEDGER_HAS_JOINED_ERROR);
        }
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("id", ledgerId));
        if (ledger == null || !ledger.getIsPublic()) {
            throw new AppException(ErrorCode.LEDGER_NOT_PUBLIC_OR_NOT_EXISTS_ERROR);
        } else if (!ledger.getPassword().equals(password)) {
            throw new AppException(ErrorCode.LEDGER_PASSWORD_ERROR);
        }
        ledgerPermissionMapper.insert(LedgerPermission.builder()
                .ledgerId(ledgerId)
                .userId(userId).build());
    }

    /**
     * 通过名称搜索公开的账本
     */
    @Cacheable(value = "ExpireOneMin", key = "'search_ledger_'+#ledgerName")
    public List<LedgerDto> search(String ledgerName) {
        List<Ledger> ledgers = ledgerMapper.selectList(new QueryWrapper<Ledger>()
                .eq("is_public", true)
                .like("name", ledgerName));
        List<LedgerDto> ledgerList = new ArrayList<>();
        for (Ledger ledger : ledgers) {
            ledgerList.add(LedgerDto.builder()
                    .id(ledger.getId())
                    .name(ledger.getName()).build());
        }
        return ledgerList;
    }
}
