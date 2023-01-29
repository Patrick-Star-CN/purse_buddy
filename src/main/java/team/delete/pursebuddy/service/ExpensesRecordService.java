package team.delete.pursebuddy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.entity.ExpensesRecord;
import team.delete.pursebuddy.exception.AppException;
import team.delete.pursebuddy.mapper.ExpensesRecordMapper;
import team.delete.pursebuddy.util.TimeUtil;

import java.text.ParseException;
import java.util.*;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class ExpensesRecordService {
    final ExpensesRecordMapper expensesRecordMapper;

    /**
     * 新增消费记录
     */
    public void insert(int userId, int value, boolean type, String kind, String dateRaw) {
        Date date;
        try {
            date = TimeUtil.tranStringToDate(dateRaw);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        ExpensesRecord expensesRecord = ExpensesRecord.builder().userId(userId)
                .kind(kind)
                .value(value)
                .date(date)
                .type(type).build();
        expensesRecordMapper.insert(expensesRecord);
    }

    /**
     * 判断该用户是否有管理该消费记录的权限
     */
    @Cacheable(value = "ExpireOneMin", key = "'not_exist_'+#id")
    public boolean exist(int id, int userId) {
        Long count = expensesRecordMapper.selectCount(new QueryWrapper<ExpensesRecord>().eq("id", id).eq("user_id", userId));
        return count != 0;
    }

    /**
     * 删除消费记录
     */
    public void delete(int id, int userId) {
        if (!exist(id, userId)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        expensesRecordMapper.delete(new QueryWrapper<ExpensesRecord>().eq("id", id));
    }

    /**
     * 修改消费记录
     */
    public void update(Integer id, int userId, int value, boolean type, String kind, String dateRaw) {
        if (id == null || !exist(id, userId)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        Date date;
        try {
            date = dateRaw != null ? TimeUtil.tranStringToDate(dateRaw) : null;
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        UpdateWrapper<ExpensesRecord> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id)
                .set("value", value)
                .set("type", type)
                .set(kind != null, "kind", kind)
                .set(date != null, "date", date);
        expensesRecordMapper.update(null, wrapper);
    }

    /**
     * 获取分页后符合条件的消费记录
     */
    public Map<String, Object> getPageable(int userId, String year, String month, String date, Boolean type, Integer offset, Integer pageSize) {
        Date startTime = null;
        Date endTime = null;
        try {
            if (date != null) {
                startTime = TimeUtil.tranStringToDate(date);
            } else if (month != null) {
                startTime = TimeUtil.tranStringToDate(month + "-01");
                endTime = TimeUtil.tranStringToDate(month + "-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.MONTH,1);
                endTime = endCalendar.getTime();
            } else if (year != null) {
                startTime = TimeUtil.tranStringToDate(year + "-01-01");
                endTime = TimeUtil.tranStringToDate(year + "-01-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.YEAR,1);
                endTime = endCalendar.getTime();
            }
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        IPage<ExpensesRecord> page = new Page<>(offset, pageSize);
        QueryWrapper<ExpensesRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.ge(startTime != null, "date", startTime);
        queryWrapper.lt(endTime != null, "date", endTime);
        expensesRecordMapper.selectPage(page, queryWrapper);
        Map<String, Object> res = new HashMap<>(4);
        res.put("pageNumber", offset);
        res.put("pageSize", pageSize);
        res.put("total", page.getTotal());
        res.put("result", page.getRecords());
        return res;
    }
}
