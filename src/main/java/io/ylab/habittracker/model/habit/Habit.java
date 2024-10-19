package io.ylab.habittracker.model.habit;

import java.time.LocalDate;

public class Habit {
    private Long userId;
    private Long id;
    private String name;
    private String description;
    private Frequency frequency;
    private LocalDate createTime;

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
