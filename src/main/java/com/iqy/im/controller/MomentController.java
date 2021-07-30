package com.iqy.im.controller;

import com.iqy.im.dto.MomentDTO;
import com.iqy.im.service.MomentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/moment")
@RestController
public class MomentController extends BaseController {

    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @PostMapping("")
    public void save(@RequestParam("medias") MultipartFile multipartFile,
                     @RequestBody MomentDTO momentDTO) {
        momentService.save(getUserId(), momentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        momentService.delete(getUserId(), id);
    }

    @PostMapping("/{id}/like")
    public void like(@PathVariable("id") String id) {
        momentService.like(getUserId(), id);
    }

    @PostMapping("/{id}/unlike")
    public void unlike(@PathVariable("id") String id) {
        momentService.unlike(getUserId(), id);
    }

    @PostMapping("/{id}/comment")
    public void comment(@PathVariable("id") String id, @RequestBody String content) {
        momentService.comment(getUserId(), id, content);
    }
}
