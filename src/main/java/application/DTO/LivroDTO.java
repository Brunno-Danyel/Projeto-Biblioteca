package application.DTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Long id;

    @NotEmpty
    private String titulo;

    @NotEmpty
    private String autor;

    @NotEmpty
    private String isbn;

}
