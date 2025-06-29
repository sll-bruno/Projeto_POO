package Exception;

public class LeituraDeArquivosException extends Exception {
    public LeituraDeArquivosException(String message) {
        super(message);
    }

    public LeituraDeArquivosException(String message, Throwable cause) {
        super(message, cause);
    }
}
