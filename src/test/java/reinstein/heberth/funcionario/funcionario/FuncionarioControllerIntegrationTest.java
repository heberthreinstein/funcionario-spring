package reinstein.heberth.funcionario.funcionario;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @MockBean private FuncionarioService funcionarioService;
    @MockBean private FuncionarioRepository funcionarioRepository;

    @Test
    void canAddFuncionario() throws Exception {
        //given
        Funcionario funcionario = new Funcionario(
                "TestNome",
                "TestSobrenome",
                "teste@email.com",
                "64240065073");

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/funcionario")
                .content(asJsonString(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @Disabled
    void getAllFuncionarios() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}