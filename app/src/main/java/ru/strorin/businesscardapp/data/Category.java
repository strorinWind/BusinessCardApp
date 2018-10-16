package ru.strorin.businesscardapp.data;

import java.io.Serializable;

public class Category implements Serializable {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Category.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Category category = (Category) obj;
        if (id != category.getId()) return false;
        if (!name.equals(category.getName())) return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
