package com.chiragbhisikar.Library.Management.System.Service.Publication;

import com.chiragbhisikar.Library.Management.System.DTO.PublicationDto;
import com.chiragbhisikar.Library.Management.System.Model.Publication;
import com.chiragbhisikar.Library.Management.System.Request.Publication.UpdatePublicationRequest;

import java.util.List;

public interface iPublicationService {
    List<Publication> getAllPublications();

    Publication getPublicationById(Long id);

    Publication getPublicationByName(String name);

    Publication addPublication(Publication publication);

    Publication updatePublication(UpdatePublicationRequest request, Long id);

    void deletePublication(Long id);

    PublicationDto convertPublicationToDto(Publication publication);

    List<PublicationDto> convertPublicationToDto(List<Publication> publications);
}
