package ru.skypro.homework.model;

import java.util.Objects;

public class purchaseReview {
    String textReview;

    public purchaseReview(String textReview) {
        this.textReview = textReview;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        purchaseReview that = (purchaseReview) o;
        return Objects.equals(textReview, that.textReview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textReview);
    }
}
