package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public final class ZksPubkeyHash extends Struct {

    public static final Integer PUBKEY_HASH_LEN = 20;

    protected final Unsigned8[] data = super.array(new Unsigned8[PUBKEY_HASH_LEN]);

    public ZksPubkeyHash(Runtime runtime) {
        super(runtime);
    }

    public byte[] getData() {
        return ArrayUtils.toPrimitive(Arrays.stream(this.data).map(Struct.Unsigned8::byteValue).toArray(Byte[]::new));
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
