package io.matterlabs.zkscrypto.lib.entity;

import io.matterlabs.zkscrypto.lib.ZksCryptoStruct;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class ZksSignature extends ZksCryptoStruct {

    public static final Integer PACKED_SIGNATURE_LEN = 64;

      public ZksSignature(Runtime runtime) {
        super(runtime, PACKED_SIGNATURE_LEN);
    }

    public enum ResultCode {
        SUCCESS,
        MUSIG_MESSAGE_TOO_LONG;

        public static ResultCode fromCode(int code) {
            switch (code) {
                case 0: return SUCCESS;
                case 1: return MUSIG_MESSAGE_TOO_LONG;
                default: throw new IllegalArgumentException();
            }
        }
    }
}
