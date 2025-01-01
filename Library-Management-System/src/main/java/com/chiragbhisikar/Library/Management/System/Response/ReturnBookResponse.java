package com.chiragbhisikar.Library.Management.System.Response;

import com.chiragbhisikar.Library.Management.System.Model.BookIssue;
import com.chiragbhisikar.Library.Management.System.Model.Penalty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnBookResponse {
    private BookIssue bookIssue;
    private Penalty penalty;
}
