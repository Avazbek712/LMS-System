package uz.imv.lmssystem.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "DTO for filtering employees",
        title = "Employee Filter DTO")
public class UserFilterDTO {
    @Schema(description = "Full name of the employee", example = "John Doe")
    private String fullName;

    @Schema(description = "Username of the employee", example = "johnDoe")
    private String username;

    @Schema(description = "Phone number of employee", example = "+998901234567")
    private String phoneNumber;

    @Schema(description = "Indicates if the employee is deleted", example = "true")
    private Boolean deleted;

    @Schema(description = "Role of the employee", example = "ADMIN")
    private String role;
}
