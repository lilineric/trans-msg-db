package com.cellulam.trans.db.sample.common.utils;

import java.util.UUID;

public abstract class UUIDUtils {
    public static final String randomUUID32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
