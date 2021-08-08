package ${package}.need;

public class ApplicationRuntimeException extends RuntimeException{
    private String message;
    private Error error;

    public ApplicationRuntimeException(String message, Error error){
        super(message);
        this.message = message;
        this.error = error;
    }

    public Error getError(){
        return this.error;
    }
}
