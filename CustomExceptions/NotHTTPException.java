package CustomExceptions;
public class NotHTTPException extends RuntimeException{
    public NotHTTPException(String message){
        super(message);
    }
}