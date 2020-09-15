package io.matterlabs.zkscrypto.lib;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public abstract class ZksCryptoStruct extends Struct {

	protected final Unsigned8[] data;
	protected byte[] cachedData;

	protected ZksCryptoStruct(Runtime runtime, int dataLength) {
		super(runtime);
		data = super.array(new Unsigned8[dataLength]);
	}

	protected void flushCachedValues() {
		this.cachedData = null;
	}

	public byte[] getData() {
		while (cachedData == null) {
			synchronized (this) {
				if (cachedData == null) {
					cachedData = new byte[this.data.length];
					for (int i = 0; i < this.data.length; i++) {
						cachedData[i] = data[i].byteValue();
					}
				}
			}
		}
		return cachedData;
	}

}
