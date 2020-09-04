package io.matterlabs.zkscrypto.lib;

import io.matterlabs.zkscrypto.lib.entity.ZksPackedPublicKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPrivateKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPubkeyHash;
import io.matterlabs.zkscrypto.lib.entity.ZksSignature;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

/**
 * Access to the ZksCrypto native library
 *
 * This class provides methods for interaction the ZksCrypto native library.
 *
 */
public final class ZksCrypto {

    public static final Integer PACKED_SIGNATURE_LEN = 64;
    public static final Integer PRIVATE_KEY_LEN = 32;
    public static final Integer PUBKEY_HASH_LEN = 20;
    public static final Integer PUBKEY_KEY_LEN = 32;

    private static final String LIBRARY_NAME;

    private ZksCryptoNative crypto;
    private Runtime runtime;

    private ZksCrypto() {}

    static {
        try {
            Class.forName("jnr.ffi.Platform");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JNR-FFI is not available on the classpath, see https://github.com/jnr/jnr-ffi");
        }
        switch (Platform.getNativePlatform().getOS()) {
            case WINDOWS:
                LIBRARY_NAME = "libzks_crypto";
                break;
            default:
                LIBRARY_NAME = "zks_crypto";
                break;
        }
    }

    /**
     * Load and initiate ZksCrypto native library
     *
     * @return ZksCrypto library instance
     */
    public static ZksCrypto load() {
        ZksCrypto crypto = new ZksCrypto();
        crypto.crypto = LibraryLoader
                .create(ZksCryptoNative.class)
                .search("/usr/local/lib")
                .search("/opt/local/lib")
                .search("/usr/lib")
                .search("/lib")
                .search("../zks-crypto-c/target/release")
                .load(LIBRARY_NAME);
        crypto.runtime = Runtime.getRuntime(crypto.crypto);
        crypto.crypto.zks_crypto_init();
        return crypto;
    }

    /**
     * Generate private key from seed
     *
     * @param seed for generation private key (length must be greater than 32 inclusive)
     * @return instance of private key container
     */
    public ZksPrivateKey generatePrivateKey(byte[] seed) {
        ZksPrivateKey privateKey = new ZksPrivateKey(this.runtime);
        int resultCode = this.crypto.zks_crypto_private_key_from_seed(seed, seed.length, Struct.getMemory(privateKey));

        switch (resultCode) {
            case 0: return privateKey;
            case 1: throw new IllegalStateException("Given seed is too short, length must be greater than 32");
            default: throw new UnsupportedOperationException();
        }
    }

    /**
     * Generate public key from private key
     *
     * @param privateKey Instance of private key
     * @return  instance of public key container
     */
    public ZksPackedPublicKey getPublicKey(final ZksPrivateKey privateKey) {
        ZksPackedPublicKey publicKey = new ZksPackedPublicKey(this.runtime);
        int resultCode = this.crypto.zks_crypto_private_key_to_public_key(Struct.getMemory(privateKey), Struct.getMemory(publicKey));

        if (resultCode == 0) {
            return publicKey;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Generate hash from public key
     *
     * @param publicKey Instance of public key
     * @return  instance of public key hash container
     */
    public ZksPubkeyHash getPublicKeyHash(final ZksPackedPublicKey publicKey) {
        ZksPubkeyHash pubkeyHash = new ZksPubkeyHash(this.runtime);
        int resultCode = this.crypto.zks_crypto_public_key_to_pubkey_hash(Struct.getMemory(publicKey), Struct.getMemory(pubkeyHash));

        if (resultCode == 0) {
            return pubkeyHash;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Sign message with musig Schnorr signature scheme
     *
     * @param privateKey Instance of private key
     * @param message message for signing
     * @return instance of signature container
     */
    public ZksSignature signMessage(final ZksPrivateKey privateKey, byte[] message) {
        ZksSignature signature = new ZksSignature(this.runtime);
        int resultCode = this.crypto.zks_crypto_sign_musig(Struct.getMemory(privateKey), message, message.length, Struct.getMemory(signature));

        switch (resultCode) {
            case 0: return signature;
            case 1: throw new IllegalStateException("Musig message is too long");
            default: throw new UnsupportedOperationException();
        }
    }

}
