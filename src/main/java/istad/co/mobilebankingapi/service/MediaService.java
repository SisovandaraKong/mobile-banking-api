package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.dto.media.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse uploadSingle(MultipartFile file);

    List<MediaResponse> uploadMultiple(List<MultipartFile> files);

    Resource downloadMediaByName(String mediaName);

    MediaResponse deleteMediaByName(String mediaName);

//    MediaResponse loadMediaByName(String mediaName);
//
//    List<MediaResponse> loadAllMedia(String folderName);

}
