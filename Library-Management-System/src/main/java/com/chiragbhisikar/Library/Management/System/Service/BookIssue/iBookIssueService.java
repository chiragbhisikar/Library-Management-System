package com.chiragbhisikar.Library.Management.System.Service.BookIssue;

import com.chiragbhisikar.Library.Management.System.Model.BookIssue;
import com.chiragbhisikar.Library.Management.System.Request.BookIssue.BookIssueRequest;
import com.chiragbhisikar.Library.Management.System.Response.ReturnBookResponse;

import java.util.List;

public interface iBookIssueService {
    List<BookIssue> getAllBookIssues();

    BookIssue getBookIssueById(Long id);

    List<BookIssue> getBookIssueByUser(Long userId);

    BookIssue issueBook(BookIssueRequest bookIssueRequest);

    ReturnBookResponse returnBook(String copyBarcode);

//    BookIssue updateBookIssue(BookIssue deposit, Long id);
}
