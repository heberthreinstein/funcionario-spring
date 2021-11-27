package reinstein.heberth.funcionario.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final StudentRepository studentRepository;

    @Autowired
    public FuncionarioService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Funcionario> getAllFuncionarios() {
        return studentRepository.findAll();
    }

    public void addFuncionario(Funcionario funcionario) {
        studentRepository.save(funcionario);
    }
}
