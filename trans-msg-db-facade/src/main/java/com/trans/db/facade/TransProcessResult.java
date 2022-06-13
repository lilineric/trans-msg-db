package com.trans.db.facade;

/**
 * trans message process result
 * it will retry when result is FAILED
 * @author eric.li
 * @date 2022-06-13 11:38
 */
public enum TransProcessResult {
    SUCCESS,
    FAILED
}
