package io.zksync.sdk.zkscrypto.lib.entity;

import io.zksync.sdk.zkscrypto.lib.ZksCryptoStruct;
import jnr.ffi.Runtime;

public final class ZksPubkeyHash extends ZksCryptoStruct {

    public static final Integer PUBKEY_HASH_LEN = 20;

    public ZksPubkeyHash(Runtime runtime) {
        super(runtime, PUBKEY_HASH_LEN);
    }
    
    public enum ResultCode {
        SUCCESS;

        public static ResultCode fromCode(int code) {
            switch (code) {
                case 0: return SUCCESS;
                default: throw new IllegalArgumentException();
            }
        }
    }
}
