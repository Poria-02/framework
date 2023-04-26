package cn.common.core.exception;

/**
 * 内部认证异常
 *
 * @author ruoyi
 */
public class InnerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InnerException(String message) {
        super(message);
    }
}
