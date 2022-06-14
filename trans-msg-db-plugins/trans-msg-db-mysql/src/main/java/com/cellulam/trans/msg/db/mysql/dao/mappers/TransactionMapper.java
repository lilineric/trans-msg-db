package com.cellulam.trans.msg.db.mysql.dao.mappers;

import com.trans.db.facade.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionMapper {

    int insert(@Param("producer") String producer,
               @Param("transType") String transType,
               @Param("transId") String transId,
               @Param("transMessage") String transMessage);

    List<Transaction> fetchTransByStatus(String status);

    List<Transaction> fetchTransByStatusTtl(@Param("tryTtl") long tryTtl,
                                            @Param("status") String status);

    int updateStatus(@Param("transId") String transId,
                     @Param("status") String status,
                     @Param("conditionStatus") String conditionStatus);

    Transaction getTrans(String transId);

    void insertHistory(Transaction transaction);

    void delete(String transId);

    int tryExecute(@Param("transId") String transId,
            @Param("status") String status,
            @Param("conditionStatus") String conditionStatus
    );

    int updateBranchTransStatus(@Param("transId") String transId,
                                @Param("status") String status);
}
