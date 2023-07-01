package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {
    /**
     * 通知标签状态
     */
    enum TagType {
        DANGER("danger"), UNPUBLISHED("primary");

        private String value;
        TagType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
    private Integer id;
    private String title;
    private String content;
    @TableField(exist = false)
    private User author;
    private String tag;
    private String tagType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String status;
}

