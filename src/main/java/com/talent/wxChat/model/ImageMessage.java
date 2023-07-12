package com.talent.wxChat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiefc
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ImageMessage extends BaseMessage {
    private Image Image;
}
