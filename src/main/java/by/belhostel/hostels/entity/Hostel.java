package by.belhostel.hostels.entity;

/**
 * Created by Roman on 27.12.2016.
 */
public class Hostel extends Entity {
    private int hostelId;
    private String name;
    private int freePlaces;
    private double price;
    private String phone;
    private String city;
    private String description;
    private String address;
    private boolean isDeleted = false;

    public Hostel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hostel hostel = (Hostel) o;

        return hostelId == hostel.hostelId;

    }

    @Override
    public int hashCode() {
        return hostelId;
    }

    public Hostel(String name, int freePlaces, double price, String phone, String city, String description, String address) {
        this.name = name;
        this.freePlaces = freePlaces;
        this.price = price;
        this.phone = phone;
        this.city = city;
        this.description = description;
        this.address = address;
    }

    public Hostel(int hostelId, String name, int freePlaces, double price, String phone, String city, String description, String address) {
        this.hostelId = hostelId;
        this.name = name;
        this.freePlaces = freePlaces;
        this.price = price;
        this.phone = phone;
        this.city = city;
        this.description = description;
        this.address = address;
    }

    public boolean isDeleted() {

        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }
}
