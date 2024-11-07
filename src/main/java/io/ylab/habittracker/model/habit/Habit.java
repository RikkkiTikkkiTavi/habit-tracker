package io.ylab.habittracker.model.habit;

import java.time.LocalDate;

/**
 * Класс модель привычки
 *
 * @autor Константин Щеглов
 */
public class Habit {
    /**
     * Поле идентификатор создателя привычки
     */
    private Long userId;

    /**
     * Поле идентификатор привычки
     */
    private Long id;

    /**
     * Поле имя привычки
     */
    private String name;

    /**
     * Поле описание привычки
     */
    private String description;

    /**
     * Поле частота выполнения привычки
     */
    private Frequency frequency;

    /**
     * Поле дата создания привычки
     */
    private LocalDate createTime;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     */
    public Habit(String name, String description, Frequency frequency, Long userId) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createTime = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Habit:" + "id пользователя =" + userId +
                ", id привычки =" + id +
                ", название ='" + name + '\'' +
                ", описание ='" + description + '\'' +
                ", частотность =" + frequency +
                ", время создания =" + createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }
}
