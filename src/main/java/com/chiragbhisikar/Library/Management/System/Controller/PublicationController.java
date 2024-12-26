package com.chiragbhisikar.Library.Management.System.Controller;

import com.chiragbhisikar.Library.Management.System.DTO.PublicationDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Publication;
import com.chiragbhisikar.Library.Management.System.Request.Publication.UpdatePublicationRequest;
import com.chiragbhisikar.Library.Management.System.Response.ApiResponse;
import com.chiragbhisikar.Library.Management.System.Service.Publication.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/publications")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllPublication() {
        try {
            List<Publication> publications = publicationService.getAllPublications();
            List<PublicationDto> publicationDtos = publicationService.convertPublicationToDto(publications);

            return ResponseEntity.ok(new ApiResponse("Success", publicationDtos));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<ApiResponse> getPublicationById(@PathVariable Long publicationId) {
        try {
            Publication publication = publicationService.getPublicationById(publicationId);
            PublicationDto publicationDto = publicationService.convertPublicationToDto(publication);

            return ResponseEntity.ok(new ApiResponse("Success", publicationDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse> getPublicationByName(@RequestParam(name = "name") String name) {
        try {
            Publication publication = publicationService.getPublicationByName(name);
            PublicationDto publicationDto = publicationService.convertPublicationToDto(publication);

            return ResponseEntity.ok(new ApiResponse("Success", publicationDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-publication")
    public ResponseEntity<ApiResponse> addPublication(@RequestBody Publication publication) {
        try {
            Publication newPublication = publicationService.addPublication(publication);
            PublicationDto publicationDto = publicationService.convertPublicationToDto(newPublication);

            return ResponseEntity.ok(new ApiResponse("Publication Added Successfully !", publicationDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-publication/{id}")
    public ResponseEntity<ApiResponse> updatePublication(@RequestBody UpdatePublicationRequest publication, @PathVariable Long id) {
        try {
            Publication newPublication = publicationService.updatePublication(publication, id);
            PublicationDto publicationDto = publicationService.convertPublicationToDto(newPublication);

            return ResponseEntity.ok(new ApiResponse("Publication Updated Successfully !", publicationDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-publication/{id}")
    public ResponseEntity<ApiResponse> deletePublication(@PathVariable Long id) {
        try {
            publicationService.deletePublication(id);
            return ResponseEntity.ok(new ApiResponse("Publication Deleted Successfully !", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
