package az.nijat_aliyev.zmm.exception;

public interface CommonException {

    ErrorCode getErrorCode();

    int getHttpStatusCode();

    String getMessageInEng();

    String getMessageInAz();
}
