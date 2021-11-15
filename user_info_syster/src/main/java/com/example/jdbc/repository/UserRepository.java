package com.example.jdbc.repository;



import com.example.jdbc.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Repository注解：标注这是一个持久化操作对象
 */
@Repository
public class UserRepository {
    //注入JdbcTemplate模板对象
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有数据
     * @return 包含User对象的List集合
     */
    public List<Map<String, Object>> findAll(){
        //定义SQL语句
        String sql = "select * from use_info";
        //声明结果集的映射rowMapper,将结果集的数据映射成User对象数据
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.queryForList(sql);
    }


    /**
     * 删除
     * @param id:用户id
     */
    public void delete(final Integer id){
        //定义SQL语句
        String sql = "delete from use_info where id=?";
        //执行
        jdbcTemplate.update(sql, new Object[]{id});
    }

    /**
     *
     * @param user
     * @return
     */

    public Integer insertGetKey(User user) {
        //声明插入的SQL语句
        try {
            String sql = "insert into use_info (userName, sex, idNumber,phone,birth,address) values (?,?,?,?,?,?)";
            Object args[] = new Object[]{user.getUserName(), user.getSex(), user.getIdNumber(), user.getPhone(), user.getBirth(), user.getAddress()};
            return jdbcTemplate.update(sql, args);
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 修改
     * @param user
     */
    public void upDate(User user){
        //定义SQL语句
        String sql = "update use_info set userName=?, sex=?,idNumber=?,phone=?,birth=?,address=? where id=?";
        Object args[] = new Object[]{user.getUserName(),user.getSex(),user.getIdNumber(),user.getPhone(),user.getBirth(),user.getAddress(),user.getId()};
        //执行
        jdbcTemplate.update(sql, args);
    }

    /**判断字符串是否全部为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param keys
     * @return
     */

    public List<User> findByKeys(String keys) {
        String sql = null;
        if(isNumeric(keys)){
            sql = "select * from use_info where id ="+keys+" or birth="+keys+" or userName like '%"+keys+"%'";
        }else{
            sql = "select * from use_info where userName like '%"+keys+"%' or sex ='"+keys+"'";
        }
//        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        //执行查询方法
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql,rowMapper);
    }


    public List<User> findByPage(Integer pagesize,Integer pageindex){
        String sql = null;
        if(pageindex==1){
            sql = "select * from use_info limit "+pagesize+"";
        }else {
            Integer start = pagesize*(pageindex-1);
            Integer end = pagesize*pageindex-start;
            sql = "select * from use_info limit "+start+","+end+"";
        }
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql,rowMapper);
    }
}
