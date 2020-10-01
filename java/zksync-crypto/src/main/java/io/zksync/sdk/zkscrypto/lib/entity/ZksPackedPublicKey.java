package io.zksync.sdk.zkscrypto.lib.entity;

import com.sun.jna.Structure;
import io.zksync.sdk.zkscrypto.lib.ZksCryptoStruct;

public class ZksPackedPublicKey extends ZksCryptoStruct {

    public static final Integer PUBLIC_KEY_LEN = 32;

    public static class ByReference extends ZksPackedPublicKey implements Structure.ByReference { }

    private ZksPackedPublicKey() {
        super(new byte[PUBLIC_KEY_LEN]);
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
