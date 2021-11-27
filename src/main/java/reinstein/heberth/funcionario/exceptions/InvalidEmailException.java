package reinstein.heberth.funcionario.exceptions;

public class InvalidEmailException extends Throwable {

    public InvalidEmailException() {
        super("Email Inválido");
    }
}
