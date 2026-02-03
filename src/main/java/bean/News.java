package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    private int id;
    private String title;
    private String brief;
    private String content_path; // или contentPath в зависимости от того, что хранится
    private LocalDateTime publish_date;
    private int news_status_id; // 1 - черновик, 2 - опубликовано и т.д.
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int author_id;

    // Добавьте это поле для отображения названия статуса
    private String status_name;

}