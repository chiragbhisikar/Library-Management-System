package com.chiragbhisikar.Library.Management.System.Service.BookIssue;


import com.chiragbhisikar.Library.Management.System.DTO.BookIssueDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.*;
import com.chiragbhisikar.Library.Management.System.Repository.BookIssueRepository;
import com.chiragbhisikar.Library.Management.System.Repository.CopyRepository;
import com.chiragbhisikar.Library.Management.System.Repository.PenaltyRepository;
import com.chiragbhisikar.Library.Management.System.Repository.UsersRepository;
import com.chiragbhisikar.Library.Management.System.Request.BookIssue.BookIssueRequest;
import com.chiragbhisikar.Library.Management.System.Response.ReturnBookResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookIssueService implements iBookIssueService {
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;
    private final BookIssueRepository bookIssueRepository;
    private final PenaltyRepository penaltyRepository;
    private final CopyRepository copyRepository;

    public List<BookIssue> getAllBookIssues() {
        return bookIssueRepository.findAll();
    }


    public BookIssue getBookIssueById(Long id) {
        return bookIssueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BookIssue not found!"));
    }

    public List<BookIssue> getBookIssueByUser(@RequestParam("userId") Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));

        return bookIssueRepository.findBookIssuesByUser(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookIssue issueBook(BookIssueRequest bookIssueRequest) {
        Date dueDate = bookIssueRequest.getDueDate();
        Copy copy = copyRepository.findCopyByBookBarcodeNumber(bookIssueRequest.getCopyBarcode())
                .orElseThrow(() -> new NotFoundException("Copy not found!"));
        User user = usersRepository.findByEnrollmentNo(bookIssueRequest.getEnrollmentId())
                .orElseThrow(() -> new NotFoundException("User not found!"));

//        here checking is book not returned or not
        BookIssue bookIssue = bookIssueRepository.findUnreturnedBooksByCopyId(copy.getId()).orElse(null);

        if (bookIssue == null) {
            BookIssue issueBook = new BookIssue();
            issueBook.setUser(user);
            issueBook.setCopy(copy);
            issueBook.setDueDate(dueDate);
            bookIssueRepository.save(issueBook);

            return issueBook;
        } else {
            return null;
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ReturnBookResponse returnBook(String copyBarcode) {
        Copy copy = copyRepository.findCopyByBookBarcodeNumber(copyBarcode)
                .orElseThrow(() -> new NotFoundException("Copy not found!"));
        BookIssue returnBook = bookIssueRepository.findUnreturnedBooksByCopyId(copy.getId()).orElse(null);

        ReturnBookResponse returnBookResponse = new ReturnBookResponse();
        if (returnBook != null) {
            Book book = copy.getBook();

            // Convert Date to LocalDate
            Date dueDate = returnBook.getDueDate();
            LocalDate dueLocalDate = dueDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Convert Date to LocalDate
            Date returnDate = new Date();
            LocalDate returnLocalDate = returnDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            long daysBetween = ChronoUnit.DAYS.between(dueLocalDate, returnLocalDate);

            System.out.println(daysBetween + "\t" + dueLocalDate + "\t" + returnLocalDate + "\t" + "okk");

            if (daysBetween > 0) {
                double penaltyAmount = daysBetween * book.getPerDayPenalty();

                Penalty penalty = new Penalty();
                penalty.setBookIssue(returnBook);
                penalty.setUser(returnBook.getUser());
                penalty.setPenaltyAmount(penaltyAmount);
                penaltyRepository.save(penalty);

                returnBookResponse.setPenalty(penalty);
            }
            returnBook.set_returned(true);
            bookIssueRepository.save(returnBook);

            returnBookResponse.setBookIssue(returnBook);
            return returnBookResponse;
        }

        throw new NotFoundException("Copy Not Issued !");
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public BookIssue updateBookIssue(BookIssue deposit, Long id) {
//        return Optional.ofNullable(getBookIssueById(id)).map(oldBookIssue -> {

    /// /            oldBookIssue.setName(deposit.getName());
//            return depositRepository.save(oldBookIssue);
//        }).orElseThrow(() -> new NotFoundException("BookIssue not found!"));
//    }

    public BookIssueDto convertBookIssueToDto(BookIssue deposit) {
        return modelMapper.map(deposit, BookIssueDto.class);
    }

    public List<BookIssueDto> convertBookIssueToDto(List<BookIssue> deposits) {
        return deposits.stream().map(this::convertBookIssueToDto).collect(Collectors.toList());
    }
}
