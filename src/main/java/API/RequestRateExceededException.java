package API;

public class RequestRateExceededException extends Exception{
    public RequestRateExceededException(){
        super("Request rate is too high.");
    }
}
