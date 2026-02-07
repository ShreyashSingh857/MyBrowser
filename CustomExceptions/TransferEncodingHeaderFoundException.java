package CustomExceptions;
public class TransferEncodingHeaderFoundException extends RuntimeException{

    public TransferEncodingHeaderFoundException(String message) {
        super(message);
    }
    
}