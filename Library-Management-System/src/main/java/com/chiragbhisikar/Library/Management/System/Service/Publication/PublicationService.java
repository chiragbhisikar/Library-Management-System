package com.chiragbhisikar.Library.Management.System.Service.Publication;

import com.chiragbhisikar.Library.Management.System.DTO.PublicationDto;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Publication;
import com.chiragbhisikar.Library.Management.System.Repository.PublicationRepository;
import com.chiragbhisikar.Library.Management.System.Request.Publication.UpdatePublicationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService implements iPublicationService {
    private final PublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Publication getPublicationById(Long id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publication not found!"));
    }

    @Override
    public Publication getPublicationByName(String name) {
        return publicationRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Publication not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Publication addPublication(Publication publication) {
        return Optional.of(publication).filter(c -> !publicationRepository.existsByName(c.getName()))
                .map(publicationRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(publication.getName() + " already exists"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Publication updatePublication(UpdatePublicationRequest request, Long id) {
        return Optional.ofNullable(getPublicationById(id)).map(oldPublication -> {
            request.getName().ifPresent(oldPublication::setName);
            request.getLocation().ifPresent(oldPublication::setLocation);

            return publicationRepository.save(oldPublication);
        }).orElseThrow(() -> new NotFoundException("Publication not found!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deletePublication(Long id) {
        publicationRepository.findById(id)
                .ifPresentOrElse(publicationRepository::delete, () -> {
                    throw new NotFoundException("Publication not found!");
                });
    }

    @Override
    public PublicationDto convertPublicationToDto(Publication publication) {
        return modelMapper.map(publication, PublicationDto.class);
    }

    @Override
    public List<PublicationDto> convertPublicationToDto(List<Publication> publications) {
        return publications.stream().map(this::convertPublicationToDto).collect(Collectors.toList());
    }
}
