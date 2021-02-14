package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.domain.Message;
import com.example.servingwebcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter_param,
                       Model model) {

        Iterable<Message> messages = messageRepo.findAll();

        if (filter_param != null && !filter_param.isEmpty())
            messages = messageRepo.findByTag(filter_param);
        else
            messages = messageRepo.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter_param);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal Customer customer,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {
        message.setCustomer(customer);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            /**
             * обработка загрузки файла из UI в локальную директорию
             * под файлом понимаем путь к файлу, под путем понимаем поле Message.file
             *  проверяем указано ли имя файла
             *  создаем объект файла
             *  проверяем наличие директории на диске если ее нет то создаем
             *  во избежании коллизий имен генерируем уникальное имя UUID.randomUUID().toString()
             *  прибавляем к уникальное к имени файла String resultFilename = uuidFile + "." + file.getOriginalFilename();
             *  загрузка файла на диск file.transferTo(new File(resultFilename)); - требует обработки эксепшена.
             *  сохранение имени файла в объекте Message message.setFilename(resultFilename);
             *  не забыть настройки в MVC config
             *      @Value("${upload.path}")
             *      private String uploadPath;
             *      @Override
             *      public void addResourceHandlers(ResourceHandlerRegistry registry) {
             */
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                message.setFilename(resultFilename);
                System.out.println(uploadPath + "/" + resultFilename);
            }
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("message", messages);
        return "main";
    }

}
