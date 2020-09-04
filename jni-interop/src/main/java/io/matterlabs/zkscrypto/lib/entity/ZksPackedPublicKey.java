package io.matterlabs.zkscrypto.lib.entity;

import io.matterlabs.zkscrypto.lib.ZksCrypto;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;

public final class ZksPackedPublicKey extends Struct {
    protected final Unsigned8[] data = super.array(new Unsigned8[ZksCrypto.PUBKEY_KEY_LEN]);

    public ZksPackedPublicKey(Runtime runtime) {
        super(runtime);
    }

    public byte[] getData() {
        return ArrayUtils.toPrimitive(Arrays.stream(this.data).map(Struct.Unsigned8::byteValue).toArray(Byte[]::new));
    }
}
