package ru.strorin.businesscardapp;

class NewsLoader {
    private static final NewsLoader newsInstance = new NewsLoader();

    static NewsLoader getInstance() {
        return newsInstance;
    }

    private NewsLoader() {
    }
}
