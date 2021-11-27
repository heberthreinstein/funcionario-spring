package reinstein.heberth.funcionario.funcionario;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void addFuncionario(@RequestBody Funcionario funcionario){
        funcionarioService.addFuncionario(funcionario);
    }


}
