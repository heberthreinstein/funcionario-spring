package reinstein.heberth.funcionario.exceptions;

public class InvalidPISException extends Throwable {
    public InvalidPISException() {
        super("PIS inválido");
    }
}
