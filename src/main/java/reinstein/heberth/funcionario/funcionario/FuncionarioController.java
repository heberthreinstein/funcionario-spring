package reinstein.heberth.funcionario.funcionario;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reinstein.heberth.funcionario.exceptions.EmailTakenException;
import reinstein.heberth.funcionario.exceptions.InvalidEmailException;
import reinstein.heberth.funcionario.exceptions.InvalidPISException;

import java.util.List;

@RestController
@RequestMapping("api/v1/funcionario")
@AllArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> getAllFuncionarios(){
        return funcionarioService.getAllFuncionarios();
    }

    @PostMapping
    public void addFuncionario(@RequestBody Funcionario funcionario) throws EmailTakenException, InvalidEmailException, InvalidPISException {
        funcionarioService.addFuncionario(funcionario);
    }


}
