package com.cellulam.trans.msg.db.core.repository;

import com.cellulam.trans.msg.db.core.context.TransContext;
import com.cellulam.trans.msg.db.core.factories.RepositoryFactory;
import com.cellulam.trans.msg.db.core.factories.SerializeFactory;
import com.cellulam.trans.msg.db.core.spi.RepositorySPI;
import com.cellulam.trans.msg.db.core.spi.SerializeSPI;

import java.io.Serializable;

/**
 * @author eric.li
 * @date 2022-06-12 10:56
 */
public class TransRepository {
    private TransRepository() {
    }

    private RepositorySPI repositorySPI = RepositoryFactory.getInstance(TransContext.getConfiguration().getRepositoryType());
    private SerializeSPI serializeSPI = SerializeFactory.getInstance(TransContext.getConfiguration().getSerializeType());

    public final static TransRepository instance = new TransRepository();

    public <T extends Serializable> String insertTransMessage(String producer, T body) {
        return repositorySPI.insertTransMessage(producer, serializeSPI.serialize(body));
    }
}
