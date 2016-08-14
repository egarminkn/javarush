package ru.javarush.tasks.crud.model;

/**
 * Created by eGarmin
 */
public class Page {

    // Будем считать, что размер страницы константа
    private static final int PAGE_SIZE = 5;

    private int number;
    private long totalCount;

    /*
     * Конструкторы
     */
    public Page() {
        // Конструктор по умолчанию
    }
    public Page(int number, long totalCount) {
        this.number = number;
        this.totalCount = totalCount;
    }

    /*
     * Геттеры и сеттеры
     */

    public static int getPageSize() {
        return PAGE_SIZE;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

}
