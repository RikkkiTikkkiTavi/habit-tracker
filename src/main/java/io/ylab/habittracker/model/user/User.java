package io.ylab.habittracker.model.user;

public class User {
    private Long id;
    private String name;
    private String email;
    private Status status;
    private String password;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = Status.AVAILABLE;

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

