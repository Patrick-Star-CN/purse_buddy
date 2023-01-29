package team.delete.pursebuddy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.delete.pursebuddy.entity.ExpensesRecord;

import java.util.List;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Mapper
public interface ExpensesRecordMapper extends BaseMapper<ExpensesRecord> {
    /**
     * 分页查询消费记录
     */
    @Select("<script>"
            +"select * from expenses_record where user_id = #{userId} "
            +"<if test='startTime!=null' and startTime.trim() neq ''\">\n"
            +"and date = #{date}"
            +"</if>"
            +"<if test='type != null'>"
            +"and type = #{type}"
            +"</if>"
            +" order by comp_year DESC limit #{offset}, #{pageSize}"
            +"</script>")
    List<ExpensesRecord> getCompetitionPageable(@Param("date") String date, @Param("type") Boolean type, @Param("userId") Integer userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

}