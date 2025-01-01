package com.chiragbhisikar.Library.Management.System.Request.BookIssue;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class BookIssueRequest {
    @NotNull(message = "Issue Date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;
    private Long enrollmentId;
    private String copyBarcode;
}
