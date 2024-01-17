package com.guidopierri.pantrybe.dtos.requests;

public class CreatePantryRequest {
    public long id;
    public long userId;

    public CreatePantryRequest(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public CreatePantryRequest() {
    }

    public long getId() {
        return this.id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreatePantryRequest)) return false;
        final CreatePantryRequest other = (CreatePantryRequest) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getUserId() != other.getUserId()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreatePantryRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final long $userId = this.getUserId();
        result = result * PRIME + (int) ($userId >>> 32 ^ $userId);
        return result;
    }

    public String toString() {
        return "CreatePantryRequest(id=" + this.getId() + ", userId=" + this.getUserId() + ")";
    }
}
