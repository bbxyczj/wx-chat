package com.talent.wxChat.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 11:35
 */
@Data
@Builder
public class InputStatusMessage implements Serializable {

    private static final long serialVersionUID = 4421150048778523195L;
    private String touser;

    private String command;
}
