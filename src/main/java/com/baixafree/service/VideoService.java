package com.baixafree.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoService.class);

    private final Path videosDir;
    private final String ytDlpPath;

    public VideoService(@Value("${video.folder}") String videoFolder,
                        @Value("${yt-dlp-path}") String ytDlpPath) {
        this.ytDlpPath = ytDlpPath;
        this.videosDir = Paths.get(videoFolder);

        try {
            if (!Files.exists(videosDir)) {
                Files.createDirectories(videosDir);
                logger.info("Diretório de vídeos criado em: {}", videosDir.toAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Erro ao criar diretório de vídeos", e);
            throw new RuntimeException("Erro ao criar diretório de vídeos", e);
        }
    }

    /**
     * Baixa o vídeo da URL informada, salvando com nome UUID.mp4
     * Retorna o nome do arquivo salvo (UUID.mp4)
     */
    public String baixarVideo(String url) {
        // Gera UUID para nome do arquivo
        String uuidFilename = java.util.UUID.randomUUID().toString() + ".mp4";
        Path videoPath = videosDir.resolve(uuidFilename);

        ProcessBuilder pb = new ProcessBuilder(
                ytDlpPath,
                "-f", "best",
                "-o", videoPath.toString(),
                url
        );
        pb.redirectErrorStream(true);

        try {
            logger.info("Executando yt-dlp: {}", String.join(" ", pb.command()));
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info("[yt-dlp] {}", line);
                }
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                logger.error("yt-dlp retornou erro código {}", exitCode);
                return null;
            }

            if (!Files.exists(videoPath) || Files.size(videoPath) == 0) {
                logger.error("Arquivo de vídeo não encontrado ou vazio após download: {}", videoPath);
                return null;
            }

            logger.info("Vídeo baixado com sucesso: {}", videoPath.toAbsolutePath());
            return uuidFilename;

        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao baixar vídeo", e);
            return null;
        }
    }
}
