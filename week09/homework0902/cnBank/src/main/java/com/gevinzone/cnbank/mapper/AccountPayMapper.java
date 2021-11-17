package com.gevinzone.cnbank.mapper;

import com.gevinzone.cnbank.bo.PayInfo;
import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountPayMapper {
    @Insert("INSERT INTO account_change_log (business_id, user_id, amount, create_time, update_time, `status`)\n" +
            "VALUES (#{businessId}, #{userId}, #{amount}, #{createTime}, #{updateTime}, #{status})")
    int insertAccountChangeLog(AccountChangeLog log);

    @Update("UPDATE account SET balance=balance-#{amount}, frozen=frozen+#{amount}, update_time=#{updateTime}\n" +
            "WHERE id = #{userId};")
    int decreaseAccount(PayInfo info);

    @Update("UPDATE account SET frozen = frozen - #{amount}, update_time = #{updateTime}\n" +
            "WHERE id = #{userId}")
    int updateAccountFrozen(PayInfo info);

    @Update("UPDATE account_change_log SET `status` = 1, update_time = #{updateTime}\n" +
            "WHERE business_id = #{businessId} and `status` = 0;")
    int updateAccountChangeLogForCompletion(PayInfo info);

    @Update("UPDATE account_change_log SET `status` = 2, update_time = #{updateTime}\n" +
            "WHERE business_id = #{businessId} and status = 0")
    int updateAccountChangeLogForCancel(PayInfo info);

    @Update("UPDATE account SET balance = balance + #{amount}, frozen = frozen - #{amount}, update_time = #{updateTime}\n" +
            "WHERE id = #{userId}")
    int rollbackAccountBalance(PayInfo info);
}
