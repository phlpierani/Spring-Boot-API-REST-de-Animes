package academy.devdojo.springBoot2.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private long id;
    private String name;
}
