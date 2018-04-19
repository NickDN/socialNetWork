package ru.myproject.dyakins.phone;

import java.time.LocalDate;

public class Phone {
    private Integer id;
    private PhoneType type;
    //todo М. String
    private Long number;

    public Phone() {
    }

    public Phone(PhoneType type, Long number) {
        this(null, type, number);
    }

    public Phone(Integer id, PhoneType type, Long number) {
        this.id = id;
        this.type = type;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public boolean isNew() {
        return this.getId() == null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", number=").append(number);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }

        Phone phone = (Phone) o;

        if (!getId().equals(phone.getId())) {
            return false;
        }
        return getNumber().equals(phone.getNumber());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNumber().hashCode();
        return result;
    }
}