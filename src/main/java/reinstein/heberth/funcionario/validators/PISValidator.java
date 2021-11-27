package reinstein.heberth.funcionario.validators;

import java.util.function.Predicate;

public class PISValidator implements Predicate<String> {
    @Override
    public boolean test(String pis) {
        return validatePIS(pis);
    }

    public static boolean validatePIS (String plPIS) {

        int liTamanho = 0;
        StringBuffer lsAux = null;
        StringBuffer lsMultiplicador = new StringBuffer("3298765432");
        int liTotalizador = 0;
        int liResto = 0;
        int liMultiplicando = 0;
        int liMultiplicador = 0;
        boolean lbRetorno = true;
        int liDigito = 99;

        lsAux = new StringBuffer().append(plPIS);
        liTamanho = lsAux.length();

        if (liTamanho != 11) {
            lbRetorno = false;
        }

        if (lbRetorno) {
            for (int i=0; i<10; i++) {

                liMultiplicando = Integer.parseInt(lsAux.substring(i, i+1));
                liMultiplicador = Integer.parseInt(lsMultiplicador.substring(i, i+1));
                liTotalizador += liMultiplicando * liMultiplicador;
            }

            liResto = 11 - liTotalizador % 11;
            liResto = liResto == 10 || liResto == 11 ? 0 : liResto;

            liDigito = Integer.parseInt("" + lsAux.charAt(10));
            lbRetorno = liResto == liDigito;
        }

        return lbRetorno;
    }
}
