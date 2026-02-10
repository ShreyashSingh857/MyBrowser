package CustomExceptions;
public class ContentEncodingHeaderFoundException extends RuntimeException{
    public ContentEncodingHeaderFoundException(String message){
        super(message);
    }
}