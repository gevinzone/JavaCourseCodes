package io.kimmking.rpcfx.server;

public class RpcfxException extends Exception{
    static final long serialVersionUID = 7818375828146090156L;

    public RpcfxException() {
        super();
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcfxException(Throwable cause) {
        super(cause);
    }
}
