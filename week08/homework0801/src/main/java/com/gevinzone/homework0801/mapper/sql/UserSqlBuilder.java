package com.gevinzone.homework0801.mapper.sql;

import com.gevinzone.homework0801.entity.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class UserSqlBuilder {
    public String insertUsers(List<User> users) {
        return new SQL() {{
            INSERT_INTO("`t_user`");
            INTO_COLUMNS("username", "`password`", "salt", "nickname", "id_number", "create_time", "update_time");
            int count = 0;
            for (User user : users) {
                if (count > 0) {
                    ADD_ROW();
                }
                INTO_VALUES(String.format("'%s'", user.getUsername()),
                        String.format("'%s'", user.getPassword()),
                        String.format("'%s'", user.getSalt()),
                        String.format("'%s'", user.getNickname()),
                        String.format("'%s'", user.getIdNumber()),
                        String.format("'%tF %tT'", user.getCreateTime(), user.getCreateTime()),
                        String.format("'%tF %tT'", user.getUpdateTime(), user.getUpdateTime())
                );
                count++;
            }
        }}.toString();
    }
}
