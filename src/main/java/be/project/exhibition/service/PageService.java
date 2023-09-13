package be.project.exhibition.service;

import org.springframework.data.domain.Pageable;

public class PageService {

    public static Long getPageNum(Pageable pageable) {
        return 1L;
    }
}
