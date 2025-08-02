package istad.co.mobilebankingapi.controller;

import istad.co.mobilebankingapi.dto.media.MediaResponse;
import istad.co.mobilebankingapi.service.MediaService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/medias")
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("/single-upload")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaResponse uploadSingle(@RequestPart MultipartFile file) {
        return mediaService.uploadSingle(file);
    }

    @PostMapping("/multiple-upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files);
    }

    // In spring
    // produces = Accept
    // consumes = Content-Type
    @GetMapping(path = "{mediaName}/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<?> downloadMediaByName(@PathVariable String mediaName) {

        Resource resource = mediaService.downloadMediaByName(mediaName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + mediaName);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    @DeleteMapping("{mediaName}")
    public MediaResponse deleteMediaByName(@PathVariable String mediaName) {
        return mediaService.deleteMediaByName(mediaName);
    }
}
