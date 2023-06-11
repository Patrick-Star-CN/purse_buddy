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
import team.delete.pursebuddy.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    final UserMapper userMapper;

    /**
     * 创建用户的默认记账本
     */
    public void createDefault(User user) {
        Ledger ledger = Ledger.builder()
                .name(user.getUsername() + "的记账本")
                .isPublic(false)
                .template("default")
                .ownerId(user.getUserId()).build();
        ledgerMapper.insert(ledger);
        ledgerPermissionMapper.insert(LedgerPermission.builder()
                .ledgerId(ledger.getId())
                .userId(user.getUserId()).build());
    }

    /**
     * 创建记账本
     */
    public int create(int userId, String name, boolean isPublic, String template) {
        Ledger ledger = Ledger.builder()
                .name(name)
                .isPublic(isPublic)
                .ownerId(userId)
                .template(template).build();
        String password = null;
        if (isPublic) {
            password = String.valueOf(UUID.randomUUID());
        }
        ledger.setPassword(password);
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
                    .name(ledger.getName())
                    .template(ledger.getTemplate())
                    .isPublic(ledger.getIsPublic())
                    .owner(userMapper.selectOne(new QueryWrapper<User>().eq("user_id", ledger.getOwnerId())).getUsername()).build());
        }
        return ledgers;
    }

    /**
     * 修改用户拥有的账本的资料
     */
    public void update(int userId, int ledgerId, String name, boolean isPublic) {
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("owner_id", userId)
                .eq("id", ledgerId));
        if (ledger == null) {
            throw new AppException(ErrorCode.LEDGER_PERMISSION_ERROR);
        }
        String password = null;
        if (isPublic) {
            password = String.valueOf(UUID.randomUUID());
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
    public void join(int userId, String password) {
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("password", password));
        if (ledger == null || !ledger.getIsPublic()) {
            throw new AppException(ErrorCode.LEDGER_NOT_PUBLIC_OR_NOT_EXISTS_ERROR);
        }
        LedgerPermission ledgerPermission = ledgerPermissionMapper.selectOne(new QueryWrapper<LedgerPermission>()
                .eq("user_id", userId)
                .eq("ledger_id", ledger.getId()));
        if (ledgerPermission != null) {
            throw new AppException(ErrorCode.LEDGER_HAS_JOINED_ERROR);
        }
        ledgerPermissionMapper.insert(LedgerPermission.builder()
                .ledgerId(ledger.getId())
                .userId(userId).build());
    }

    public String query(int userId, int ledgerId) {
        Ledger ledger = ledgerMapper.selectOne(new QueryWrapper<Ledger>()
                .eq("owner_id", userId)
                .eq("id", ledgerId));
        if (ledger == null) {
            throw new AppException(ErrorCode.LEDGER_PERMISSION_ERROR);
        }
        return ledger.getPassword();
    }
}
