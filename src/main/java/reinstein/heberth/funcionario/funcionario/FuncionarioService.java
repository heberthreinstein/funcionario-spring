package reinstein.heberth.funcionario.funcionario;

import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reinstein.heberth.funcionario.exceptions.EmailTakenException;
import reinstein.heberth.funcionario.exceptions.FuncionarioNotFoundExpetion;
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

    public void addFuncionario(Funcionario funcionario)
            throws EmailTakenException, InvalidEmailException, InvalidPISException {
        //TODO: Aplicar um design pattern aqui por causa dos if repetido;
        //Email Validation
        boolean isEmailValid = EmailValidator.getInstance().isValid(funcionario.getEmail());
        if (!isEmailValid){
            throw new InvalidEmailException();
        }

        // Remove a mascara do PIS caso tenha
        funcionario.setPis(funcionario.getPis().replaceAll("[^0-9]+", ""));
        // PIS Validation
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

    public void delete(long id) throws FuncionarioNotFoundExpetion {
        funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundExpetion(id));
        funcionarioRepository.deleteById(id);
    }

    @Transactional(rollbackFor=Exception.class)
    public void update(Long id, Funcionario updatedFuncionario) throws FuncionarioNotFoundExpetion, EmailTakenException, InvalidEmailException {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNotFoundExpetion(id));

        //Nome
        if (updatedFuncionario.getNome() != null)
            if (!funcionario.getNome().equals(updatedFuncionario.getNome()))
                funcionario.setNome(updatedFuncionario.getNome());

        //Sobrenome
        if (updatedFuncionario.getSobrenome() != null)
            if (!funcionario.getSobrenome().equals(updatedFuncionario.getSobrenome()))
                funcionario.setSobrenome(updatedFuncionario.getSobrenome());

        //Email
        if (!funcionario.getEmail().equals(updatedFuncionario.getEmail())) {
            boolean isEmailValid = EmailValidator.getInstance().isValid(updatedFuncionario.getEmail());
            if (!isEmailValid){
                throw new InvalidEmailException();
            }

            try {
                funcionario.setEmail(updatedFuncionario.getEmail());
            } catch (ConstraintViolationException e) {
                throw new EmailTakenException();
            }
        }

        //PIS
        if (!funcionario.getPis().equals(updatedFuncionario.getPis())) {
            boolean isPisValid = pisValidator.test(updatedFuncionario.getPis());
            if (!isPisValid){
                throw new InvalidEmailException();
            }
                funcionario.setPis(updatedFuncionario.getEmail());
        }
    }
}
