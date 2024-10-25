package com.openwebinars.data.todoapp.rest.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class CheckListTask extends Task {


    @OneToMany(
            mappedBy = "task",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<CheckListItem> items = new ArrayList<>();

    // Helpers

    public void addItem(CheckListItem item) {
        this.items.add(item);
        item.setTask(this);
    }

    public void removeItem(CheckListItem item) {
        this.items.remove(item);
        //item.setTask(null); // No hace falta por tener orphanRemoval
    }

    public void removeItemById(Long id) {
        this.items.removeIf(item -> item.getId().equals(id));
    }






    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CheckListTask that = (CheckListTask) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

