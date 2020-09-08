package io.matterlabs.zkscrypto.lib.entity;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public final class ZksSignature extends Struct {

    public static final Integer PACKED_SIGNATURE_LEN = 64;

    protected final Unsigned8[] data = super.array(new Unsigned8[PACKED_SIGNATURE_LEN]);

    public ZksSignature(Runtime runtime) {
        super(runtime);
    }

    public byte[] getData() {
        return ArrayUtils.toPrimitive(Arrays.stream(this.data).map(Struct.Unsigned8::byteValue).toArray(Byte[]::new));
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
