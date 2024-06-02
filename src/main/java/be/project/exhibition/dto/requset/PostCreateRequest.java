package be.project.exhibition.dto.requset;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @NotBlank(message = "내용을 입력해주세요")
    String body;
    List<MultipartFile> multipartFileList;
}
