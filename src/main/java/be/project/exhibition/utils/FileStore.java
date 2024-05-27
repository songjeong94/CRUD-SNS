package be.project.exhibition.utils;

import be.project.exhibition.entity.PostEntity;
import be.project.exhibition.entity.PostImage;
import be.project.exhibition.entity.UserEntity;
import be.project.exhibition.entity.UserImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.postImagePath}")
    private String postImageDir;

    @Value("${file.userImagePath}")
    private String userImageDir;

    public String getPostImagePath(String filename) {
        return postImageDir + filename;
    }
    public String getUserImagePath(String filename) {
        return userImageDir + filename;
    }


    public List<PostImage> postStoreFiles(List<MultipartFile> multipartFiles, PostEntity post) throws IOException {
        List<PostImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(postStoreFile(multipartFile, post));
            }
        }
        return storeFileResult;
    }

    public PostImage postStoreFile(MultipartFile multipartFile, PostEntity post) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getPostImagePath(storeFileName)));
        return new PostImage().builder()
                .uploadFilename(originalFilename)
                .storeFilename(storeFileName)
                .post(post)
                .build();
    }

    public UserImage userStoreFile(MultipartFile multipartFile, UserEntity user) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getUserImagePath(storeFileName)));
        return new UserImage().builder()
                .attachImage(originalFilename)
                .storedImage(storeFileName)
                .user(user)
                .build();
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
