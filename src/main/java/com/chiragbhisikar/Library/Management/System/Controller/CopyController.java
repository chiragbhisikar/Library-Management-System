package com.chiragbhisikar.Library.Management.System.Controller;


import com.chiragbhisikar.Library.Management.System.DTO.CopyDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Copy;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Barcode.BarcodeService;
import com.chiragbhisikar.Library.Management.System.Service.Copy.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/copies")
public class CopyController {
    private final CopyService copyService;
    private final BarcodeService barcodeService;


    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCopy() {
        try {
            List<Copy> copies = copyService.getAllCopy();
            List<CopyDto> copyDto = copyService.convertCopiesToDto(copies);

            return ResponseEntity.ok(new ApiResponse("Success", copyDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{copyId}")
    public ResponseEntity<ApiResponse> getCopyById(@PathVariable Long copyId) {
        try {
            Copy copy = copyService.getCopyById(copyId);
            CopyDto copyDto = copyService.convertCopyToDto(copy);

            return ResponseEntity.ok(new ApiResponse("Success", copyDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byBarcode")
    public ResponseEntity<ApiResponse> getCopyByBookBarcode(@RequestParam(name = "barcode") String bookBarcodeNumber) {
        try {
            System.out.printf(bookBarcodeNumber);
            Copy copy = copyService.getCopyByBarcode(bookBarcodeNumber);
            CopyDto copyDto = copyService.convertCopyToDto(copy);

            return ResponseEntity.ok(new ApiResponse("Success", copyDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/add-copy")
    public ResponseEntity<ApiResponse> addCopy(@RequestBody Copy copy, @RequestParam(name = "numberOfCopies", required = false, defaultValue = "1") int numberOfCopies) {
        try {
            if (numberOfCopies == 1) {
                String barcodeUrl = copyService.addCopy(copy);
                return ResponseEntity.ok(new ApiResponse("Copy Added Successfully !", Map.of("url", barcodeUrl)));
            } else {
                List<Map<String, String>> barcodeUrls = copyService.addCopies(copy, numberOfCopies);
                return ResponseEntity.ok(new ApiResponse("Copy Added Successfully !", barcodeUrls));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), "Book Not Found !"));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), "Copy Already Exists !"));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/barcode")
    public ResponseEntity<ApiResponse> getBarcodeImage(@RequestParam(name = "barcode") String barcodeNumber) {
        try {
            String barcodeUrl = barcodeService.generateBarcodeUrl(barcodeNumber);

            return ResponseEntity.ok().body(new ApiResponse("Barcode Gotten Successfully", Map.of("url", barcodeUrl)));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

//    @PostMapping("/add-category")
//    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
//        try {
//            Category newCategory = categoryService.addCategory(category);
//            CategoryDto categoryDto = categoryService.convertCategoryToDto(newCategory);
//
//            return ResponseEntity.ok(new ApiResponse("Category Added Successfully !", categoryDto));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
//        }
//    }
//
//    @PutMapping("/update-category/{id}")
//    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
//        try {
//            Category newCategory = categoryService.updateCategory(category, id);
//            CategoryDto categoryDto = categoryService.convertCategoryToDto(newCategory);
//
//            return ResponseEntity.ok(new ApiResponse("Category Updated Successfully !", categoryDto));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
//        }
//    }
//
//    @DeleteMapping("/delete-category/{id}")
//    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
//        try {
//            categoryService.deleteCategory(id);
//            return ResponseEntity.ok(new ApiResponse("Category Deleted Successfully !", null));
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
//        }
//    }
}
