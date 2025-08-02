package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.domain.Media;
import istad.co.mobilebankingapi.dto.media.MediaResponse;
import istad.co.mobilebankingapi.repository.MediaRepository;
import istad.co.mobilebankingapi.service.MediaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file) {

        // New name
        String name = UUID.randomUUID().toString();

        // Find last index of (.)
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");

        // Extract extension
        String extension = file.getOriginalFilename().substring(lastIndex + 1);

        // Create path object
        Path path = Paths.get(serverPath + String.format("%s.%s", name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Media upload failed");
        }

        Media media = new Media();
        media.setName(name+ "."+ extension);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .size(file.getSize())
                .mimeType(file.getContentType())
                .uri(String.format("%s%s.%s", baseUri, name, extension))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {

        List<MediaResponse> mediaResponses = new ArrayList<>();

        // Use loop to upload each file
        files.forEach(file -> {
            MediaResponse mediaResponse = this.uploadSingle(file);
            mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public Resource downloadMediaByName(String mediaName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + mediaName);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media has been not found");
        }
    }

    @Override
    public MediaResponse deleteMediaByName(String mediaName) {

        Path path = Paths.get(serverPath + mediaName);

        try {
            if (Files.deleteIfExists(path)) {
                return MediaResponse.builder()
                        .name(mediaName)
                        .mimeType("")
                        .uri(String.format("%s%s", baseUri, mediaName))
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media has been not found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
