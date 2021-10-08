package org.project.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OrderBy("createDate desc")
    private List<Advertisement> advertisements;

    public Category() {
    }

    public long getId() {
        return id;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Category setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name)
                && Objects.equals(description, category.description)
                && Objects.equals(createDate, category.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", createDate=").append(createDate);
        //sb.append(", advertisements=").append(advertisements);
        sb.append('}');
        return sb.toString();
    }
}
