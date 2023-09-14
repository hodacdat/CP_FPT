package com.example.demo.DTO;

public class CountResult {
    private double online;
    private double completed;
    private double cancel;

    public CountResult(double online, double completed, double cancel) {
        this.online = online;
        this.completed = completed;
        this.cancel = cancel;
    }

    // Các phương thức getter cho từng giá trị nếu cần thiết
    public double getOnline() {
        return online;
    }

    public double getCompleted() {
        return completed;
    }

    public double getCancel() {
        return cancel;
    }
    public void setCancel(double cancel) {
		this.cancel = cancel;
	}
    public void setCompleted(double completed) {
		this.completed = completed;
	}
    public void setOnline(double online) {
		this.online = online;
	}
}