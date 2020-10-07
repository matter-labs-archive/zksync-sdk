package io.zksync.sdk.zkscrypto.lib.entity;

import com.sun.jna.Structure;
import io.zksync.sdk.zkscrypto.lib.ZksCryptoStruct;

public class ZksPubkeyHash extends ZksCryptoStruct {

    public static final Integer PUBKEY_HASH_LEN = 20;

    public static class ByReference extends ZksPubkeyHash implements Structure.ByReference { }

    private ZksPubkeyHash() {
        super(new byte[PUBKEY_HASH_LEN]);
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
