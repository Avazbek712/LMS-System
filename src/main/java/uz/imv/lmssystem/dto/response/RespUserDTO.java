package uz.imv.lmssystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response data transfer object for employee")
public class RespUserDTO {

    @Schema(description = "Full name of the employee", example = "John Doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String fullName;

    @Schema(description = "Username of the employee", example = "johnDoe")
    private String username;

    @Schema(description = "Phone number of employee", example = "+998901234567")
    private String phoneNumber;

    @Schema(description = "Role name of the employee", example = "ADMIN")
    private String roleName;

    @Schema(description = "Indicates if the employee is deleted", example = "true")
    private Boolean deleted;


}
