//
//  ZksCryptoSecretKey.swift
//  ZKSyncSDK-UI
//
//  Made with ❤️ by Matter Labs on 10/23/20
//

import Foundation

public class ZKSecretKey {
    private var content: Data
    
    init(_ content: Data) {
        self.content = content
    }
    
    convenience init(_ content: [UInt8]) {
        self.init(Data(content))
    }
    
    public func data() -> Data {
        return content
    }
    
    public func base64String() -> String {
        return content.base64EncodedString()
    }
    
    public func withUnsafeBytes<ResultType>(_ body: (UnsafeRawBufferPointer) throws -> ResultType) rethrows -> ResultType {
        return try! content.withUnsafeBytes(body)
    }
}

public class ZKPackedPublicKey: ZKSecretKey {
    static let keyLength = 32
}

public class ZKPrivateKey: ZKSecretKey {
    static let keyLength = 32
}

public class ZKPublicHash: ZKSecretKey {
    static let keyLength = 20
}

public class ZKSignature: ZKSecretKey {
    static let keyLength = 64
}


public enum ZKSyncSDKError: Error {
    case musigTooLongError
    case seedTooShortError
    case unsupportedOperation
}

public enum ZKSyncSDKResult<T> {
    case success(_ result: T)
    case error(_ error: Error)
}
