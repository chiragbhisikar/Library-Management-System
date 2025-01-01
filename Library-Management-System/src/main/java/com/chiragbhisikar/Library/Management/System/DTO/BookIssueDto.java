package com.chiragbhisikar.Library.Management.System.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class BookIssueDto {
    private Long id;
    private Date issueDate;
    private Date dueDate;
    private UserDto user;
    private CopyDto copy;
    private boolean is_deleted;
}
