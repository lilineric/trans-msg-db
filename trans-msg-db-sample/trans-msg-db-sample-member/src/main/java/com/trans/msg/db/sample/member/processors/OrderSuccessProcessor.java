package com.trans.msg.db.sample.member.processors;

import com.cellulam.trans.db.sample.common.dto.OrderDto;
import com.cellulam.trans.db.sample.common.utils.ThreadUtils;
import com.trans.db.facade.AbstractTransMessageProcessor;
import com.trans.db.facade.enums.TransProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * @author eric.li
 * @date 2022-06-16 15:25
 */
@Slf4j
@Component
public class OrderSuccessProcessor extends AbstractTransMessageProcessor<OrderDto> {
    @Override
    public String getProducer() {
        return "order";
    }

    @Override
    public String getTransType() {
        return "success";
    }

    @Override
    public TransProcessResult process(OrderDto body) {
        ThreadUtils.sleep(RandomUtils.nextLong(10, 1000));
        log.info("process success: " + body);
        return RandomUtils.nextInt() % 2 == 0
                ? TransProcessResult.SUCCESS
                : TransProcessResult.FAILED;
    }
}
