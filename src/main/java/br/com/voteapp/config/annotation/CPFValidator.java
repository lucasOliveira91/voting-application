package br.com.voteapp.config.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public void initialize(ValidCPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) {
            return false;
        }
        cpf = cpf.trim().replace(".", "").replace("-", "");
        if (cpf.length() != 11 || isInvalidCPFSequence(cpf)) {
            return false;
        }
        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }

        return validateCPF(cpf);
    }

    private boolean isInvalidCPFSequence(String cpf) {
        return cpf.chars().allMatch(c -> c == cpf.charAt(0));
    }

    private boolean validateCPF(String cpf) {
        char dig10, dig11;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 10;
            for (i = 0; i < 9; i++) {
                num = (cpf.charAt(i) - '0');
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + '0');

            sm = 0;
            weight = 11;
            for (i = 0; i < 10; i++) {
                num = (cpf.charAt(i) - '0');
                sm = sm + (num * weight);
                weight = weight - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + '0');

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (InputMismatchException error) {
            return false;
        }
    }
}
