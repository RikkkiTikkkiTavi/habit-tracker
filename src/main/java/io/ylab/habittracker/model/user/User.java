package io.ylab.habittracker.model.user;

/**
 * Класс модель пользователя
 *
 * @autor Константин Щеглов
 */
public class User {
    /**
     * Поле идентификатор пользователя
     */
    private Long id;
    /**
     * Поле имя пользователя
     */
    private String name;
    /**
     * Поле адрес почты пользователя
     */
    private String email;
    /**
     * Поле статус доступа пользователя
     */
    private Status status;
    /**
     * Поле пароль пользователя
     */
    private String password;

    /**
     * Конструктор - создание нового объекта без параметров
     */
    public User() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = Status.AVAILABLE;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

