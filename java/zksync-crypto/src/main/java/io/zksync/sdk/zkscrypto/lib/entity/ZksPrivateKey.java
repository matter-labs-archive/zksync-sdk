package io.zksync.sdk.zkscrypto.lib.entity;

import com.sun.jna.Structure;
import io.zksync.sdk.zkscrypto.lib.ZksCryptoStruct;

public class ZksPrivateKey extends ZksCryptoStruct {

    public static final Integer PRIVATE_KEY_LEN = 32;

    public static class ByReference extends ZksPrivateKey implements Structure.ByReference { }

    private ZksPrivateKey() {
        super(new byte[PRIVATE_KEY_LEN]);
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
