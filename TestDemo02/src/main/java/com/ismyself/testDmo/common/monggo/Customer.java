package com.ismyself.testDmo.common.monggo;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_customer")
public class Customer {
    private String _id;
    private String carNumber;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "_id='" + _id + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
