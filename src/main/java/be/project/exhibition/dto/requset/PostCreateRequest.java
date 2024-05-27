package be.project.exhibition.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostCreateRequest {
    String title;
    String body;
    List<MultipartFile> multipartFileList;
}
