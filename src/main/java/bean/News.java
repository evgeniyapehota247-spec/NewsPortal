package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    private Integer id;
    private String title;
    private String brief;
    private String content_path;
    private LocalDateTime publish_date;
    private Integer news_status_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer author_id;
    private String status_name;

}