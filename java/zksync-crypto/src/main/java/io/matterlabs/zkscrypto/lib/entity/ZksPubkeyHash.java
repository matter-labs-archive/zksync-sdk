package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class ZksPubkeyHash extends Struct {

    public static final Integer PUBKEY_HASH_LEN = 20;

    protected final Unsigned8[] data = super.array(new Unsigned8[PUBKEY_HASH_LEN]);

    public ZksPubkeyHash(Runtime runtime) {
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
