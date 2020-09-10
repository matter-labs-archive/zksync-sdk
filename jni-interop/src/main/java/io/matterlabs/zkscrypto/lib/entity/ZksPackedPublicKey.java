package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public final class ZksPackedPublicKey extends Struct {

    public static final Integer PUBLIC_KEY_LEN = 32;

    protected final Unsigned8[] data = super.array(new Unsigned8[PUBLIC_KEY_LEN]);

    public ZksPackedPublicKey(Runtime runtime) {
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
