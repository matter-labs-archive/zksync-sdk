package io.matterlabs.zkscrypto.lib;

import io.matterlabs.zkscrypto.lib.entity.ZksPackedPublicKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPrivateKey;
import io.matterlabs.zkscrypto.lib.entity.ZksPubkeyHash;
import io.matterlabs.zkscrypto.lib.entity.ZksSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ZksCryptoTest {

    private static final byte[] SEED = Arrays.copyOf(new byte[0], 32);
    private static final byte[] MSG = "hello".getBytes(StandardCharsets.UTF_8);

    private ZksCrypto cryptoLib;

    @BeforeEach
    void init() {
        this.cryptoLib = ZksCrypto.load();
    }

    @Test
    void generatePrivateKey() {
        ZksPrivateKey privateKey = cryptoLib.generatePrivateKey(SEED);

        assertArrayEquals(privateKey.getData(), new byte[] {1, 31, 91, -103, 8, 76, 92, 46, 45, 94, 99, 72, -114, 15, 113, 104, -43, -103, -91, -64, 31, -23, -2, -60, -55, -106, 5, 116, 61, -91, -24, 92});
    }

    @Test
    void getPublicKey() {
        ZksPrivateKey privateKey = cryptoLib.generatePrivateKey(SEED);
        ZksPackedPublicKey publicKey = cryptoLib.getPublicKey(privateKey);

        assertArrayEquals(publicKey.getData(), new byte[] {23, -100, 58, 89, 20, 125, 48, 49, 108, -120, 102, 40, -123, 35, 72, -55, -76, 42, 24, -72, 33, 8, 74, -55, -17, 121, -67, 115, -23, -71, 78, -115});
    }

    @Test
    void getPublicKeyHash() {
        ZksPrivateKey privateKey = cryptoLib.generatePrivateKey(SEED);
        ZksPackedPublicKey publicKey = cryptoLib.getPublicKey(privateKey);
        ZksPubkeyHash pubkeyHash = cryptoLib.getPublicKeyHash(publicKey);

        assertArrayEquals(pubkeyHash.getData(), new byte[] {-57, 113, 39, 22, -71, -17, 107, -46, 23, 83, -60, -23, 29, -20, -61, 81, -79, 17, -64, 109});
    }

    @Test
    void signMessage() {
        ZksPrivateKey privateKey = cryptoLib.generatePrivateKey(SEED);
        ZksSignature signature = cryptoLib.signMessage(privateKey, MSG);

        assertArrayEquals(signature.getData(), new byte[] {66, 111, 115, 126, -54, 53, 46, -4, 88, -107, 33, 63, -100, -36, -54, -112, -94, 98, 68, -8, 76, -62, -107, -64, 31, 0, 20, 92, 6, -56, 13, 37, 62, 28, -71, -3, 66, -73, 96, -128, -60, -45, 32, 85, -74, -119, -22, 62, 1, -27, 111, -104, -128, -29, -111, 47, -101, 27, -103, -63, -28, 91, 80, 4});

    }
}