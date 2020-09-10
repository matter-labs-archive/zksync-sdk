package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class ZksSignature extends Struct {

    public static final Integer PACKED_SIGNATURE_LEN = 64;

    protected final Unsigned8[] data = super.array(new Unsigned8[PACKED_SIGNATURE_LEN]);

    public ZksSignature(Runtime runtime) {
        super(runtime);
    }

    public byte[] getData() {
        byte[] dataRaw = new byte[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            dataRaw[i] = data[i].byteValue();
        }
        return dataRaw;
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
