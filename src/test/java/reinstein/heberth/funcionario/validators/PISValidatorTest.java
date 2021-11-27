package reinstein.heberth.funcionario.validators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PISValidatorTest {

    PISValidator pisValidator = new PISValidator();

    @Test
    void testValidPIS() {
        //given
        String pis = "43334212026";

        //when
        boolean result = pisValidator.test(pis);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void testNotValidPIS() {
        //given
        String pis = "1231231231231231231231231";

        //when
        boolean result = pisValidator.test(pis);

        //then
        assertThat(result).isFalse();
    }
}