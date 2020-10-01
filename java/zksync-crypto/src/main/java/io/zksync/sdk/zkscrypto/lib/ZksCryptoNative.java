package io.zksync.sdk.zkscrypto.lib;

import com.sun.jna.Library;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPackedPublicKey;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPrivateKey;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPubkeyHash;
import io.zksync.sdk.zkscrypto.lib.entity.ZksSignature;

public interface ZksCryptoNative extends Library {

    void zks_crypto_init();

    int zks_crypto_private_key_from_seed(
            byte[] seed,
            long seed_len,
            ZksPrivateKey.ByReference private_key
    );

    int zks_crypto_private_key_to_public_key(
            ZksPrivateKey.ByReference private_key,
            ZksPackedPublicKey.ByReference public_key
    );

    int zks_crypto_public_key_to_pubkey_hash(
            ZksPackedPublicKey.ByReference public_key,
            ZksPubkeyHash.ByReference pubkey_hash
    );

    int zks_crypto_sign_musig(
            ZksPrivateKey.ByReference private_key,
            byte[] seed,
            long seed_len,
            ZksSignature.ByReference signature
    );
}
