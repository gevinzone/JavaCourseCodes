package com.gevinzone.foreignexchange.mapper;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountTransferDetailMapper {
    @Insert("INSERT INTO account_transfer_detail (from_id, to_id, amount_us, amount_cny, create_time, update_time, `status`) \n" +
            "VALUES (#{fromId}, #{toId}, #{amountUs}, #{amountCny}, #{createTime}, #{updateTime}, #{status});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccountTransferDetail detail);

    @Update("UPDATE account_transfer_detail set status = #{status} \n" +
            "WHERE id = #{id} AND status = 0")
    int updateAccountTransferDetailStatus(AccountTransferDetail detail);
}
