package reinstein.heberth.funcionario.exceptions;

public class EmailTakenException extends Exception{

    public EmailTakenException(){
        super("Email ja utilizado!");
    }

}
