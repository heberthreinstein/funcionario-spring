package reinstein.heberth.funcionario.exceptions;

public class FuncionarioNotFoundExpetion extends Exception {
    public FuncionarioNotFoundExpetion(Long id) {
        super("Funcionario id = " + id + " não encontrado");
    }
}
