package io.matterlabs.zkscrypto.lib.entity;

import io.matterlabs.zkscrypto.lib.ZksCryptoStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class ZksPrivateKey extends ZksCryptoStruct {

    public static final Integer PRIVATE_KEY_LEN = 32;

    public ZksPrivateKey(Runtime runtime) {
        super(runtime, PRIVATE_KEY_LEN);
    }

    public enum ResultCode {
        SUCCESS,
        SEED_TOO_SHORT;

        public static ResultCode fromCode(int code) {
            switch (code) {
                case 0: return SUCCESS;
                case 1: return SEED_TOO_SHORT;
                default: throw new IllegalArgumentException();
            }
        }
    }
}
