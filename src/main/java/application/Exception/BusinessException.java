package application.Exception;

import application.entities.Livro;

public class BusinessException extends RuntimeException {

    public BusinessException(String s) {
        super(s);
    }
}
