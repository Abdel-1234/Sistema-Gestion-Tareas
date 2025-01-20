/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionatustareas.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "This model is used to return errors in RFC 7807 which created a generalized error-handling schema composed by five parts")
@NoArgsConstructor
@Data
public class StandarizedApiExceptionResponse {

    @Schema(description = "The unique uri identifier that categorizes the error", name = "type",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized")
    private String type;

    @Schema(description = "A brief, human-readable message about the error", name = "title",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have autorization")
    private String title;

    @Schema(description = "The unique error code", name = "code",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "192")
    private String code;

    @Schema(description = "A human-readable explanation of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have the propertly persmissions to acces the resource")
    private String detail;

    @Schema(description = "A URI that identifies the specific occurrence of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized/01")
    private String instance;

    public StandarizedApiExceptionResponse(String type, String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
        this.type = type;
    }
}
