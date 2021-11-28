package reinstein.heberth.funcionario.exceptions;

public class InvalidEmailException extends Exception {

    public InvalidEmailException() {
        super("Email Inválido");
    }
}
