package excusasHspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoExcusaInvalidoException extends RuntimeException {
    public TipoExcusaInvalidoException(String tipo) {
        super("Tipo de excusa inválido: " + tipo);
    }
}
