package com.uploadfile.learning.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {
    
    private static String UPLOADED_PATH = "/home/naufal/uploaded-files/";
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/status")
    public String uploadStatus(){
        return "status";
    }
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Select a file to upload");
            return "redirect:status";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_PATH + file.getOriginalFilename());
            Files.write(path, bytes, StandardOpenOption.WRITE);

            redirectAttributes.addFlashAttribute("message", "you successed upload a file");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return "redirect:status";
    }
}
