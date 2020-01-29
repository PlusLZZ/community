package lzz.blog.community.community.controller;

import lzz.blog.community.community.dto.FileDTO;
import lzz.blog.community.community.exception.CustomizeErrorCode;
import lzz.blog.community.community.exception.CustomizeException;
import lzz.blog.community.community.service.TencentCould;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private TencentCould fileService;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        String fileName = null;
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
            fileName = fileService.fileUpload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(fileName);
        return fileDTO;
    }

    @GetMapping("/fileTest")
    public void fileTest() {

    }

}
