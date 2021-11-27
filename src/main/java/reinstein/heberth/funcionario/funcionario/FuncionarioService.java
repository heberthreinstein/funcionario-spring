package reinstein.heberth.funcionario.funcionario;

import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reinstein.heberth.funcionario.exceptions.EmailTakenException;
import reinstein.heberth.funcionario.exceptions.InvalidEmailException;
import reinstein.heberth.funcionario.exceptions.InvalidPISException;
import reinstein.heberth.funcionario.validators.PISValidator;

import java.util.List;

@Service
public class FuncionarioService {

    private PISValidator pisValidator;
    private EmailValidator emailValidator;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.emailValidator = EmailValidator.getInstance();
        this.pisValidator = new PISValidator();
    }

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public void addFuncionario(Funcionario funcionario) throws EmailTakenException, InvalidEmailException, InvalidPISException {
        //TODO: Aplicar um design pattern aqui por causa dos if repetido;
        boolean isEmailValid = EmailValidator.getInstance().isValid(funcionario.getEmail());
        if (!isEmailValid){
            throw new InvalidEmailException();
        }
        // Remove a mascara do PIS caso tenha
        funcionario.setPis(funcionario.getPis().replaceAll("[^0-9]+", ""));
        boolean isPisValid = pisValidator.test(funcionario.getPis());
        if (!isPisValid){
            throw new InvalidPISException();
        }

        try {
            funcionarioRepository.save(funcionario);
        } catch (ConstraintViolationException e) {
            throw new EmailTakenException();
        }
    }
}
