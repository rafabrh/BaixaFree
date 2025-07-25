# BaixaFree

**BaixaFree** é um serviço backend em Java com Spring Boot para download automático de vídeos a partir de URLs públicas, utilizando a ferramenta \yt-dlp\. O projeto foca em simplicidade, robustez e escalabilidade, oferecendo uma API REST para baixar vídeos e servir arquivos para o usuário.

---

### Principais funcionalidades

- API REST para receber URLs e baixar vídeos no formato MP4.
- Download confiável via integração com \yt-dlp\ (sucessor do youtube-dl).
- Armazenamento organizado dos vídeos no servidor, com controle de arquivos por UUID.
- Endpoint para download dos vídeos baixados.
- Código limpo, modular e aderente a boas práticas de engenharia de software.
- Logging detalhado para rastreamento dos processos de download.
- Configuração via \pplication.yml\ para fácil ajuste do diretório de vídeos e caminho do executável \yt-dlp\.

---

### Por que BaixaFree?

- **Solução backend eficiente** para integração em sistemas que precisam capturar e distribuir vídeos.
- Facilita a automação de downloads para diversos tipos de aplicações (ex: portais de notícias, apps de mídia, automações internas).
- Código aberto para expansão, customização e integração.
- Ferramenta útil para desenvolvedores que querem entender integração com processos externos e gerenciamento de arquivos em Java.

---

### Tecnologias usadas

- Java 17+
- Spring Boot 3
- SLF4J / Logback para logging
- \yt-dlp\ para download de vídeo
- API REST para comunicação
- Maven para build e dependências

---

### Como usar

1. Configurar o caminho do \yt-dlp\ no \pplication.yml\.
2. Rodar a aplicação Spring Boot localmente ou no servidor.
3. Enviar POST para \/api/baixar\ com JSON \{ \
url\: \URL_DO_VIDEO\ }\.
4. Receber confirmação de sucesso.
5. Baixar o vídeo pelo endpoint \/api/video/{filename}\.

---

### Próximos passos (roadmap)

- Interface web simples para upload e download direto pelo navegador.
- Suporte a múltiplos formatos e qualidades de vídeo.
- Sistema de autenticação e controle de acesso.
- Monitoramento e métricas dos downloads.
- Suporte a filas para processar múltiplos downloads simultâneos.

---

### Contribuição

Contribuições são bem-vindas! Fork, melhorias, issues e pull requests são incentivados para tornar o BaixaFree cada vez mais robusto e versátil.

---

### Licença

MIT License — uso livre e aberto para projetos pessoais e comerciais.
