package hu.petrik.somogyibotond_javafxrestclientdolgozat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Worker {


    public enum posts {
        ProjectManager,
        TeamLeader,
        TeamMember,
        Worker
    }

    private int id;
    private String name;
    private int payment;
    private posts post;
    private LocalDate hireDate;


    public Worker(int id, String name, int payment, posts post, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.post = post;
        this.hireDate = hireDate;
    }

    public Worker(int id, String name, int payment, posts post, LocalDateTime hireDate) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.post = post;
        this.hireDate = hireDate.toLocalDate();
    }

    public Worker(String name, int payment, posts post, LocalDate hireDate) {
        this.name = name;
        this.payment = payment;
        this.post = post;
        this.hireDate = hireDate;
    }

    public Worker(String name, int payment, posts post, LocalDateTime hireDate) {
        this.name = name;
        this.payment = payment;
        this.post = post;
        this.hireDate = hireDate.toLocalDate();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPayment() {
        return payment;
    }

    public posts getPost() {
        return post;
    }

    public String getHireDateText() {
        return hireDate.toString();
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setPost(posts post) {
        this.post = post;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
