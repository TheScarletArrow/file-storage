package ru.scarlet.filestorage.exception;

public class NoDownloadsLeftException extends RuntimeException {
    public NoDownloadsLeftException() {
    }

    public NoDownloadsLeftException(String message) {
        super(message);
    }

    public NoDownloadsLeftException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDownloadsLeftException(Throwable cause) {
        super(cause);
    }

    public NoDownloadsLeftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
