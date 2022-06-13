package com.trans.db.facade.enums;

/**
 * trans message process result
 * it will retry when result is FAILED
 * @author eric.li
 * @date 2022-06-13 11:38
 */
public enum TransProcessResult {
    INIT,
    SUCCESS,
    FAILED
}
