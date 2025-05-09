package academy.devdojo.springBoot2.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimePostRequestBody {
    @NotBlank(message = "O nome não pode estar em branco")
    @Schema(description = "Nome do anime", example = "Demon Slayer", required = true)
    private String name;

}
