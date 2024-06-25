package org.spring.codingStory.approval.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/apvFile")
public class ApprovalFileController {

    @PostMapping("/fileDown")
    public ModelAndView modelAndView() {
        ModelAndView mv = new ModelAndView();
        File path = new File("C:/codingStoryDown/");
        String[] fileList = path.list();

        mv.addObject("fileList", fileList);
        mv.setViewName("apv/fileDown");

        return mv;
    }

    @GetMapping("/fileDown/{file}")
    public void fileDownLoad(@PathVariable String file, HttpServletResponse response) throws IOException {
        File f = new File("C:/codingStoryDown/", file);
        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int) f.length());
        response.setHeader("Content-disposition", "attachment;filename=\"" + file + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();

    }

}
