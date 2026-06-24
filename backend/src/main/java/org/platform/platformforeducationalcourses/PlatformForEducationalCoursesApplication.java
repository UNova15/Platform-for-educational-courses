package org.platform.platformforeducationalcourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class PlatformForEducationalCoursesApplication {

    static void main(String[] args) {
        SpringApplication.run(PlatformForEducationalCoursesApplication.class, args);
    }
}

// Получение статистики по курсу??

// CourseCatalogController
// получение краткой инфы о всех доступных на платформе курсах

// TODO исправить мелкие косяки
// TODO проверка того, что пользователь - преподователь (в бд при создании курса)
// TODO добавить enum в бд или таблицу индефикаторов
// TODO добавить индексы в бд
