package io.zksync.sdk.zkscrypto.lib;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public abstract class ZksCryptoStruct extends Structure {

    protected ZksCryptoStruct(byte[] data) {
        this.data = data;
    }

    public byte[] data;

    public byte[] getData() {
        return data;
    }

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("data");
    }

}
