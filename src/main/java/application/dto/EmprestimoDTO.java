package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoDTO {

    private Long id;

    private String email;

    private String isbn;

    private String cliente;

    private LivroDTO livroDTO;
}
