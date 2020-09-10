package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class ZksPackedPublicKey extends Struct {

    public static final Integer PUBLIC_KEY_LEN = 32;

    protected final Unsigned8[] data = super.array(new Unsigned8[PUBLIC_KEY_LEN]);

    public ZksPackedPublicKey(Runtime runtime) {
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
        SUCCESS;

        public static ResultCode fromCode(int code) {
            switch (code) {
                case 0: return SUCCESS;
                default: throw new IllegalArgumentException();
            }
        }
    }
}
