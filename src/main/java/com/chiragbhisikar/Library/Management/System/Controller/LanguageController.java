package com.chiragbhisikar.Library.Management.System.Controller;


import com.chiragbhisikar.Library.Management.System.DTO.LanguageDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Language;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllLanguage() {
        try {
            List<Language> languages = languageService.getAllLanguages();
            List<LanguageDto> languageDtos = languageService.convertLanguagesToDto(languages);

            return ResponseEntity.ok(new ApiResponse("Success", languageDtos));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{languageId}")
    public ResponseEntity<ApiResponse> getLanguageById(@PathVariable Long languageId) {
        try {
            Language language = languageService.getLanguageById(languageId);
            LanguageDto languageDto = languageService.convertLanguageToDto(language);

            return ResponseEntity.ok(new ApiResponse("Success", languageDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse> getLanguageByName(@RequestParam(name = "name") String name) {
        try {
            Language category = languageService.getLanguageByName(name);
            LanguageDto categoryDto = languageService.convertLanguageToDto(category);

            return ResponseEntity.ok(new ApiResponse("Success", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-language")
    public ResponseEntity<ApiResponse> addLanguage(@RequestBody Language language) {
        try {
            Language newCategory = languageService.addLanguage(language);
            LanguageDto categoryDto = languageService.convertLanguageToDto(newCategory);

            return ResponseEntity.ok(new ApiResponse("Language Added Successfully !", categoryDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-language/{id}")
    public ResponseEntity<ApiResponse> updateLanguage(@RequestBody Language language, @PathVariable Long id) {
        try {
            Language newLanguage = languageService.updateLanguage(language, id);
            LanguageDto languageDto = languageService.convertLanguageToDto(newLanguage);

            return ResponseEntity.ok(new ApiResponse("language Updated Successfully !", languageDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-language/{id}")
    public ResponseEntity<ApiResponse> deleteLanguage(@PathVariable Long id) {
        try {
            languageService.deleteLanguage(id);
            return ResponseEntity.ok(new ApiResponse("Language Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
