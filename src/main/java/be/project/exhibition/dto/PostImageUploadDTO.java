package be.project.exhibition.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostImageUploadDTO {

    private List<MultipartFile> imageFiles;
}
