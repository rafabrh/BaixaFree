package com.baixafree.controller;

import com.baixafree.service.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DownloaderController {

    private final VideoService videoService;

    @Value("${video.folder}")
    private String videoFolder;

    public DownloaderController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/baixar")
    public ResponseEntity<?> baixar(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        if (url == null || url.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "URL não fornecida."));
        }

        try {
            String filename = videoService.baixarVideo(url);
            if (filename == null) {
                return ResponseEntity.status(500).body(Map.of("error", "Falha ao baixar vídeo."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Falha ao baixar vídeo: " + e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("message", "Vídeo baixado com sucesso"));
    }

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> serveVideo(@PathVariable String filename) {
        File file = new File(videoFolder, filename);

        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }
}
