package reinstein.heberth.funcionario.funcionario;

import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reinstein.heberth.funcionario.exceptions.EmailTakenException;
import reinstein.heberth.funcionario.exceptions.FuncionarioNotFoundExpetion;
import reinstein.heberth.funcionario.exceptions.InvalidEmailException;
import reinstein.heberth.funcionario.exceptions.InvalidPISException;
import reinstein.heberth.funcionario.validators.PISValidator;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private PISValidator pisValidator;

    @Mock
    EmailValidator emailValidator;

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
    void canAddFuncionario() throws EmailTakenException, InvalidEmailException, InvalidPISException {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "80238693030");

        //when
        underTest.addFuncionario(funcionario);

        //then
        ArgumentCaptor<Funcionario> argumentCaptor = ArgumentCaptor.forClass(Funcionario.class);
        verify(funcionarioRepository).save(argumentCaptor.capture());
        Funcionario capFuncionario = argumentCaptor.getValue();
        assertThat(capFuncionario).isEqualTo(funcionario);
    }

    @Test
    void canAddFuncionarioPISWithDots() throws EmailTakenException, InvalidEmailException, InvalidPISException {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "802.38693.03-0");

        //when
        underTest.addFuncionario(funcionario);

        //then
        ArgumentCaptor<Funcionario> argumentCaptor = ArgumentCaptor.forClass(Funcionario.class);
        verify(funcionarioRepository).save(argumentCaptor.capture());
        Funcionario capFuncionario = argumentCaptor.getValue();
        assertThat(capFuncionario).isEqualTo(funcionario);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "64240065073");

        given(funcionarioRepository.save(funcionario)).willThrow(ConstraintViolationException.class);

        //when
        //then
        assertThatThrownBy(() -> underTest.addFuncionario(funcionario)).isInstanceOf(EmailTakenException.class);
    }

    @Test
    void willThrowWhenEmailIsNotValid() {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@",
                "64240065073");

        given(emailValidator.isValid(funcionario.getEmail())).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.addFuncionario(funcionario)).isInstanceOf(InvalidEmailException.class);
        verify(funcionarioRepository, never()).save(funcionario);
    }

    @Test
    void willThrowWhenPISIsNotValid() {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "00000000");

         given(pisValidator.test(funcionario.getPis())).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> underTest.addFuncionario(funcionario)).isInstanceOf(InvalidPISException.class);
        verify(funcionarioRepository, never()).save(funcionario);

    }

    @Test
    void canDeleteFuncionario() throws FuncionarioNotFoundExpetion {
        //given
        long id = 1L;

        //when
        underTest.delete(id);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(funcionarioRepository).deleteById(argumentCaptor.capture());
        Long capId = argumentCaptor.getValue();
        assertThat(capId).isEqualTo(id);
    }

    @Test
    void willThrowWhenFuncionarioNotFoundWhileDeleting() throws FuncionarioNotFoundExpetion {
        //given
        long id = 1L;
        given(funcionarioRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.delete(id)).isInstanceOf(FuncionarioNotFoundExpetion.class);
    }

    @Test
    void canUpdateFuncionario() throws FuncionarioNotFoundExpetion, EmailTakenException, InvalidEmailException {
        //given
        long id = 1L;
        Funcionario funcionario = new Funcionario(
                "TestUpdatedNome",
                "TestUpdatedSobrenome",
                "testeUpdated@email.com",
                "64240065073");

        given(funcionarioRepository.findById(id)).willReturn(Optional.of(new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "00000000")));


        //when
        underTest.update(id,funcionario);

        //then
    }

}