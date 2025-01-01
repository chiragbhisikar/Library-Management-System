package com.chiragbhisikar.Library.Management.System.Controller;


import com.chiragbhisikar.Library.Management.System.DTO.BookIssueDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.BookIssue;
import com.chiragbhisikar.Library.Management.System.Request.BookIssue.BookIssueRequest;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Response.ReturnBookResponse;
import com.chiragbhisikar.Library.Management.System.Service.BookIssue.BookIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/book-issue")
public class BookIssueController {

    private final BookIssueService bookIssueService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllBookIssue() {
        try {
            System.out.println("Get All Book Issue");
            List<BookIssue> bookIssues = bookIssueService.getAllBookIssues();
            List<BookIssueDto> bookIssueDto = bookIssueService.convertBookIssueToDto(bookIssues);

            return ResponseEntity.ok(new ApiResponse("Success", bookIssueDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/{bookIssueId}")
    public ResponseEntity<ApiResponse> getBookIssueById(@PathVariable Long bookIssueId) {
        try {
            BookIssue bookIssue = bookIssueService.getBookIssueById(bookIssueId);
            BookIssueDto bookIssueDto = bookIssueService.convertBookIssueToDto(bookIssue);

            return ResponseEntity.ok(new ApiResponse("Success", bookIssueDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/byUser")
    public ResponseEntity<ApiResponse> getBookIssueByUser(@RequestParam(name = "user") Long userId) {
        try {
            List<BookIssue> bookIssue = bookIssueService.getBookIssueByUser(userId);
            List<BookIssueDto> bookIssueDto = bookIssueService.convertBookIssueToDto(bookIssue);

            return ResponseEntity.ok(new ApiResponse("Success", bookIssueDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/issue-book")
    public ResponseEntity<ApiResponse> issueBook(@RequestBody BookIssueRequest bookIssueRequest) {
        try {
            System.out.println(bookIssueRequest);
            BookIssue issuedBook = bookIssueService.issueBook(bookIssueRequest);

            if (issuedBook != null) {
                BookIssueDto bookIssueDto = bookIssueService.convertBookIssueToDto(issuedBook);

                return ResponseEntity.ok(new ApiResponse("BookIssue Issued Successfully !", bookIssueDto));
            } else {
                return ResponseEntity.ok(new ApiResponse("Book Not Issued Because Book Doesn't Returned By Previous User !", null));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/return-book/{copyBarcode}")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable String copyBarcode) {
        try {
            ReturnBookResponse returnBook = bookIssueService.returnBook(copyBarcode);

            if (returnBook != null) {
                return ResponseEntity.ok(new ApiResponse("Book Returned Successfully !", returnBook));
            } else {
                return ResponseEntity.ok(new ApiResponse("Book Not Returned Because Book Doesn't Issued !", null));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /*
    @PutMapping("/update-publication/{id}")
    public ResponseEntity<ApiResponse> updateBookIssue(@RequestBody UpdateBookIssueRequest publication, @PathVariable Long id) {
        try {
            BookIssue newBookIssue = bookIssueService.updateBookIssue(publication, id);
            BookIssueDto publicationDto = bookIssueService.convertBookIssueToDto(newBookIssue);

            return ResponseEntity.ok(new ApiResponse("BookIssue Updated Successfully !", publicationDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-publication/{id}")
    public ResponseEntity<ApiResponse> deleteBookIssue(@PathVariable Long id) {
        try {
            bookIssueService.deleteBookIssue(id);
            return ResponseEntity.ok(new ApiResponse("BookIssue Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
 */
}
