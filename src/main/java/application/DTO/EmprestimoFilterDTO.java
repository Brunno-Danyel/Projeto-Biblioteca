package application.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class EmprestimoFilterDTO {

    private String isbn;
    private String customer;

}
