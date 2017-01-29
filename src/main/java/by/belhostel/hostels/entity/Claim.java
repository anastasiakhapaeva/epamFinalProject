package by.belhostel.hostels.entity;

import java.time.LocalDate;

/**
 * Created by Roman on 01.01.2017.
 */
public class Claim extends Entity {
    private int claimId;
    private int hostelId;
    private int userId;
    private ClaimType claimType;
    private int requiredPlaces;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private boolean isConfirmed = false;
    private boolean isDeleted = false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Claim claim = (Claim) o;

        return claimId == claim.claimId;

    }

    @Override
    public int hashCode() {
        return claimId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Claim(int hostelId, int userId, ClaimType claimType, int requiredPlaces, LocalDate dateIn, LocalDate dateOut) {
        this.hostelId = hostelId;
        this.userId = userId;
        this.claimType = claimType;
        this.requiredPlaces = requiredPlaces;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public Claim(int hostelId, int userId, ClaimType claimType, int requiredPlaces, LocalDate dateIn, LocalDate dateOut, boolean isConfirmed) {
        this.hostelId = hostelId;
        this.userId = userId;
        this.claimType = claimType;
        this.requiredPlaces = requiredPlaces;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.isConfirmed = isConfirmed;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Claim() {
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public int getRequiredPlaces() {
        return requiredPlaces;
    }

    public void setRequiredPlaces(int requiredPlaces) {
        this.requiredPlaces = requiredPlaces;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }
}
