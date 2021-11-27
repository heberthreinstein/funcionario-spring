package reinstein.heberth.funcionario.funcionario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;
    private FuncionarioService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new FuncionarioService(funcionarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllFuncionarios() {
        //when
        underTest.getAllFuncionarios();
        //then
        verify(funcionarioRepository).findAll();
    }

    @Test
    void canAddFuncionario() {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                64240065073L);

        //when
        underTest.addFuncionario(funcionario);

        //then
        ArgumentCaptor<Funcionario> argumentCaptor = ArgumentCaptor.forClass(Funcionario.class);

        verify(funcionarioRepository).save(argumentCaptor.capture());

        Funcionario capFuncionario = argumentCaptor.getValue();

        assertThat(capFuncionario).isEqualTo(funcionario);

    }

}