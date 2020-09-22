package io.matterlabs.zkscrypto.lib;

import io.matterlabs.zkscrypto.lib.entity.ZksPackedPublicKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPrivateKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPubkeyHash;
import io.matterlabs.zkscrypto.lib.entity.ZksSignature;
import io.matterlabs.zkscrypto.lib.exception.ZksMusigTooLongException;
import io.matterlabs.zkscrypto.lib.exception.ZksSeedTooShortException;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Access to the ZksCrypto native library
 *
 * This class provides methods for interaction the ZksCrypto native library.
 *
 */
public final class ZksCrypto {

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
        try {
            NativeLoader.loadLibrary("zks_crypto");
        } catch (SecurityException e) {
            throw new IllegalStateException("Cannot load native library", e);
        } catch (IOException ignored) {}
    }

    /**
     * Load and initialize ZksCrypto native library
     * @param paths Custom paths to search ZksCrypto library binary
     *
     * @return ZksCrypto library instance
     */
    public static ZksCrypto load(Path ...paths) {
        ZksCrypto crypto = new ZksCrypto();
        LibraryLoader<ZksCryptoNative> libraryLoader = LibraryLoader
                .create(ZksCryptoNative.class)
                .search("/usr/local/lib")
                .search("/opt/local/lib")
                .search("/usr/lib")
                .search("/lib")
                .search("../../zks-crypto/zks-crypto-c/target/release")
                .search("./")
                .searchDefault();
        Arrays.stream(paths)
                .map(Path::toAbsolutePath)
                .map(Path::toString)
                .forEach(libraryLoader::search);
        crypto.crypto = libraryLoader.load(LIBRARY_NAME);
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
    public ZksPrivateKey generatePrivateKey(byte[] seed) throws ZksSeedTooShortException {
        ZksPrivateKey privateKey = new ZksPrivateKey(this.runtime);
        int resultCode = this.crypto.zks_crypto_private_key_from_seed(seed, seed.length, Struct.getMemory(privateKey));

        switch (ZksPrivateKey.ResultCode.fromCode(resultCode)) {
            case SUCCESS: return privateKey;
            case SEED_TOO_SHORT: throw new ZksSeedTooShortException("Given seed is too short, length must be greater than 32");
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

        if (ZksPackedPublicKey.ResultCode.fromCode(resultCode) == ZksPackedPublicKey.ResultCode.SUCCESS) {
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

        if (ZksPubkeyHash.ResultCode.fromCode(resultCode) == ZksPubkeyHash.ResultCode.SUCCESS) {
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
    public ZksSignature signMessage(final ZksPrivateKey privateKey, byte[] message) throws ZksMusigTooLongException {
        ZksSignature signature = new ZksSignature(this.runtime);
        int resultCode = this.crypto.zks_crypto_sign_musig(Struct.getMemory(privateKey), message, message.length, Struct.getMemory(signature));

        switch (ZksSignature.ResultCode.fromCode(resultCode)) {
            case SUCCESS: return signature;
            case MUSIG_MESSAGE_TOO_LONG: throw new ZksMusigTooLongException("Musig message is too long");
            default: throw new UnsupportedOperationException();
        }
    }

}
