package Exception;


//Validação Exception: Classe personalizada criada para indicar quando 
public class ValidacaoException extends Exception {
    public ValidacaoException(String message) {
        super(message);
    }
}