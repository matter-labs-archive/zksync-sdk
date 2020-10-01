package io.zksync.sdk.zkscrypto.lib;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPackedPublicKey;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPrivateKey;
import io.zksync.sdk.zkscrypto.lib.entity.ZksPubkeyHash;
import io.zksync.sdk.zkscrypto.lib.entity.ZksSignature;
import io.zksync.sdk.zkscrypto.lib.exception.ZksMusigTooLongException;
import io.zksync.sdk.zkscrypto.lib.exception.ZksSeedTooShortException;
import org.scijava.nativelib.BaseJniExtractor;
import org.scijava.nativelib.NativeLibraryUtil;
import org.scijava.nativelib.NativeLoader;

import java.io.File;
import java.io.IOException;

/**
 * Access to the ZksCrypto native library
 *
 * This class provides methods for interaction the ZksCrypto native library.
 *
 */
public final class ZksCrypto {

    private static final String LIBRARY_NAME = "zks_crypto";

    private ZksCryptoNative crypto;

    private ZksCrypto() {}

    static {
        try {
            NativeLoader.loadLibrary(LIBRARY_NAME);
            NativeLibrary.addSearchPath(LIBRARY_NAME, ((BaseJniExtractor) NativeLoader.getJniExtractor()).getJniDir().getAbsolutePath());
            NativeLibrary.addSearchPath(LIBRARY_NAME, ((BaseJniExtractor) NativeLoader.getJniExtractor()).getNativeDir().getAbsolutePath());
        } catch (SecurityException e) {
            throw new IllegalStateException("Cannot load native library", e);
        } catch (IOException ignored) {}
    }

    /**
     * Load and initialize ZksCrypto native library
     *
     * @return ZksCrypto library instance
     */
    public static ZksCrypto load() {
        ZksCrypto crypto = new ZksCrypto();
        crypto.crypto = Native.load(LIBRARY_NAME, ZksCryptoNative.class);
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
        ZksPrivateKey.ByReference privateKey = new ZksPrivateKey.ByReference();
        int resultCode = this.crypto.zks_crypto_private_key_from_seed(seed, seed.length, privateKey);
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
        ZksPackedPublicKey.ByReference publicKey = new ZksPackedPublicKey.ByReference();
        int resultCode = this.crypto.zks_crypto_private_key_to_public_key((ZksPrivateKey.ByReference) privateKey, publicKey);

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
        ZksPubkeyHash.ByReference pubkeyHash = new ZksPubkeyHash.ByReference();
        int resultCode = this.crypto.zks_crypto_public_key_to_pubkey_hash((ZksPackedPublicKey.ByReference) publicKey, pubkeyHash);

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
        ZksSignature.ByReference signature =  new ZksSignature.ByReference();
        int resultCode = this.crypto.zks_crypto_sign_musig((ZksPrivateKey.ByReference) privateKey, message, message.length, signature);

        switch (ZksSignature.ResultCode.fromCode(resultCode)) {
            case SUCCESS: return signature;
            case MUSIG_MESSAGE_TOO_LONG: throw new ZksMusigTooLongException("Musig message is too long");
            default: throw new UnsupportedOperationException();
        }
    }

    private static String getLibraryClasspathName() {
        return "/" + NativeLibraryUtil.getPlatformLibraryPath(NativeLibraryUtil.DEFAULT_SEARCH_PATH) + NativeLibraryUtil.getPlatformLibraryName("zks_crypto");
    }

}
