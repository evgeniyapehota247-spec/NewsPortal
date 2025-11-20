package dao.impl;

import bean.News;
import dao.DaoException;
import dao.NewsDao;

import java.util.ArrayList;
import java.util.List;

public class DBNewsDao implements NewsDao {

    @Override
    public List<News> topNews(int count) throws DaoException {

        List<News> topNews = new ArrayList<News>();

        topNews.add(new News(
                1,
                "QQQРост промышленного производства в Беларуси",
                "AAAЗа последний квардекс наблюдается устойчивый рост промышленного производства на 5.3% по сравнению с аналогичным периодом прошлого года.",
                "content 1"));
        topNews.add(new News(
                2,
                "Открытие нового музея в Минске",
                "В столице открылся современный музей истории Беларуси с интерактивными экспонатами и цифровыми технологиями",
                "content 2"));
        topNews.add(new News(
                3,
                "Белорусские атлеты завоевали медали на международных соревнованиях",
                "На чемпионате Европы по легкой атлетике белорусские спортсмены показали выдающиеся результаты, завоевав 3 золотые медали.",
                "content 3"));

        return topNews;
    }
}
