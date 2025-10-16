package com.serratec.serratec_music.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResposta {

    private Integer status;
    private String mensagem;
    private LocalDateTime dataHora;
    private List<String> erros;

    public ErroResposta(Integer status, String mensagem, LocalDateTime dataHora, List<String> erros) {
        this.status = status;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
        this.erros = erros;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<String> getErros() {
        return erros;
    }
}
