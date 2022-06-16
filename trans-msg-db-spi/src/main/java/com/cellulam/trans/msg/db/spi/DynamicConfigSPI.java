package com.cellulam.trans.msg.db.spi;

import com.cellulam.trans.msg.db.spi.anotation.SingletonSPI;
import com.cellulam.trans.msg.db.spi.contract.TypeSPI;
import com.trans.db.facade.ConsumerRegister;

import java.util.List;

/**
 * dynamic config SPI
 *
 * @author eric.li
 * @date 2022-06-12 22:24
 */
@SingletonSPI
public interface DynamicConfigSPI extends TypeSPI {
    /**
     * get the consumers by producer and transType
     *
     * @param transType
     * @param producer
     * @return Returns a specific type of message that has been registered
     */
    List<String> getConsumers(String transType, String producer);


    /**
     * get trans types by producer
     * @param producer
     * @return
     */
    List<String> getTransTypes(String producer);

    /**
     * get the register info by consumer
     * @param consumer
     * @return
     */
    List<ConsumerRegister> getRegistersByConsumer(String consumer);
}
