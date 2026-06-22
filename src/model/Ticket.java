package model;

import java.sql.Date;

public class Ticket {

    private int ticketId;
    private String title;
    private String category;
    private String priority;
    private String status;
    private Date createdDate;
    private Date resolvedDate;
    private String assignedTo;

    // Empty constructor
    public Ticket() {
    }

    // Parameterized constructor
    public Ticket(String title, String category, String priority,
                  String status, Date createdDate, Date resolvedDate, String assignedTo) {
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.createdDate = createdDate;
        this.resolvedDate = resolvedDate;
        this.assignedTo = assignedTo;
    }

    // Getters
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public Date getCreatedDate() { return createdDate; }
    public Date getResolvedDate() { return resolvedDate; }
    public String getAssignedTo() { return assignedTo; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public void setResolvedDate(Date resolvedDate) { this.resolvedDate = resolvedDate; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}
