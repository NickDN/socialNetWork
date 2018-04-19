package ru.myproject.dyakins.account;

import ru.myproject.dyakins.phone.Phone;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Account {
    private Integer id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private List<Phone> phones;
    private String homeAddress;
    private String workAddress;
    private String email;
    private String icq;
    private String skype;
    private String additionalInfo;
    private String password;

    private Account(AccountBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
        this.middleName = builder.middleName;
        this.gender = builder.gender;
        this.dateOfBirth = builder.dateOfBirth;
        this.phones = builder.phones;
        this.homeAddress = builder.homeAddress;
        this.workAddress = builder.workAddress;
        this.email = builder.email;
        this.icq = builder.icq;
        this.skype = builder.skype;
        this.additionalInfo = builder.additionalInfo;
        this.password = builder.password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNew() {
        return this.getId() == null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(secondName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", phones=").append(phones);
        sb.append(", homeAddress='").append(homeAddress).append('\'');
        sb.append(", workAddress='").append(workAddress).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", icq=").append(icq);
        sb.append(", skype='").append(skype).append('\'');
        sb.append(", additionalInfo='").append(additionalInfo).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(getEmail(), account.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    public static class AccountBuilder {
        private Integer id;
        private String firstName;
        private String secondName;
        private String middleName;
        private Gender gender;
        private LocalDate dateOfBirth;
        private List<Phone> phones;
        private String homeAddress;
        private String workAddress;
        private String email;
        private String icq;
        private String skype;
        private String additionalInfo;
        private String password;

        public AccountBuilder(String firstName, String secondName, LocalDate dateOfBirth, String email, String password) {
            this.firstName = firstName;
            this.secondName = secondName;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.password = password;
        }

        public AccountBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public AccountBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public AccountBuilder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public AccountBuilder setPhones(List<Phone> phones) {
            this.phones = phones;
            return this;
        }

        public AccountBuilder setHomeAddress(String homeAddress) {
            this.homeAddress = homeAddress;
            return this;
        }

        public AccountBuilder setWorkAddress(String workAddress) {
            this.workAddress = workAddress;
            return this;
        }

        public AccountBuilder setIcq(String icq) {
            this.icq = icq;
            return this;
        }

        public AccountBuilder setSkype(String skype) {
            this.skype = skype;
            return this;
        }

        public AccountBuilder setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public AccountBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}