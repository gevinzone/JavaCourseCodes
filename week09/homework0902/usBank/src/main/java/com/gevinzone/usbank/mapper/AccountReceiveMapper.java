package com.gevinzone.usbank.mapper;

import com.gevinzone.remittancecontract.entity.AccountChangeLog;
import com.gevinzone.usbank.bo.ReceiveInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountReceiveMapper {
    @Insert("INSERT INTO account_change_log (business_id, user_id, amount, create_time, update_time, `status`)\n" +
            "VALUES (#{businessId}, #{userId}, #{amount}, #{createTime}, #{updateTime}, #{status})")
    int insertAccountReceiveLog(AccountChangeLog log);

    @Insert("UPDATE account SET frozen = frozen + #{amount}, update_time = #{updateTime}\n" +
            "WHERE id = #{userId}")
    int insertAccountFrozenAmount(ReceiveInfo info);

    @Update("UPDATE account_change_log SET `status` = 1, update_time = #{updateTime}\n" +
            "WHERE business_id = #{businessId} and status = 0")
    int updateAccountChangeLogForCompletion(ReceiveInfo info);

    @Update("UPDATE account SET frozen = frozen - #{amount}, balance = balance + #{amount}, update_time = #{updateTime}\n" +
            "WHERE id = #{userId}")
    int updateAccountBalanceForCompletion(ReceiveInfo info);

    @Update("UPDATE account_change_log SET `status` = 2, update_time = #{updateTime}\n" +
            "WHERE business_id = #{businessId} and `status` = 0")
    int updateAccountChangeLogForCancel(ReceiveInfo info);

    @Update("UPDATE account SET frozen = frozen - #{amount}, update_time = NOW()\n" +
            "WHERE id = #{userId}")
    int releaseAccountFrozenForCancel(ReceiveInfo info);
}
