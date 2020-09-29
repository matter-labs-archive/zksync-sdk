package io.zksync.sdk.zkscrypto.lib.entity;

import io.zksync.sdk.zkscrypto.lib.ZksCryptoStruct;
import jnr.ffi.Runtime;

public final class ZksPackedPublicKey extends ZksCryptoStruct {

    public static final Integer PUBLIC_KEY_LEN = 32;

    public ZksPackedPublicKey(Runtime runtime) {
        super(runtime, PUBLIC_KEY_LEN);
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
