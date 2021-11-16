package com.gevinzone.foreignexchange.mapper;

import com.gevinzone.remittancecontract.entity.AccountTransferDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountTransferDetailMapper {
    @Insert("INSERT INTO account_transfer_detail (from_id, to_id, amount_us, amount_cny, create_time, update_time, `status`) \n" +
            "VALUES (#{fromId}, #{toId}, #{amountUs}, #{amountCny}, #{createTime}, #{updateTime}, #{status});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AccountTransferDetail detail);
}
