package common;

public class User {
    private int idUser;
    private String fullName;
    private String phone;
    private String password;
    private String address;

    public User() {
        this.fullName = new String();
        this.phone = new String();
        this.address = new String();
        this.password = new String();
    }

    public User(String fullName, String phone, String address, String password) {
        this.fullName = new String(fullName);
        this.phone = new String(phone);
        this.address = new String(address);
        this.password = new String(password);

    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String toString() {
        return "[fullName: " + fullName + ", phone: " + phone + ", address: " + address + ", password" + password
                + " ]";
    }

}
