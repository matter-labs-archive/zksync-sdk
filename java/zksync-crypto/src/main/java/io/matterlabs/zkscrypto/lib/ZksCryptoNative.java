package io.matterlabs.zkscrypto.lib;

import jnr.ffi.Pointer;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.types.ssize_t;

public interface ZksCryptoNative {

    void zks_crypto_init();

    int zks_crypto_private_key_from_seed(
            @In byte[] seed,
            @In @ssize_t long seed_len,
            @Out Pointer private_key
    );

    int zks_crypto_private_key_to_public_key(
            @In Pointer private_key,
            @Out Pointer public_key
    );

    int zks_crypto_public_key_to_pubkey_hash(
            @In Pointer public_key,
            @Out Pointer pubkey_hash
    );

    int zks_crypto_sign_musig(
            @In Pointer private_key,
            @In byte[] seed,
            @In @ssize_t long seed_len,
            @Out Pointer signature
    );
}
