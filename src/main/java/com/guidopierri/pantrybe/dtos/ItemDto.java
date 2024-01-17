package com.guidopierri.pantrybe.dtos;

public class ItemDto {
    public long id;
    public String name;
    public long quantity;
    public String expirationDate;
    public String gtin;
    public String brand;
    public String image;
    public String category;
    public long pantryId;

    public ItemDto() {
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getGtin() {
        return this.gtin;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getImage() {
        return this.image;
    }

    public String getCategory() {
        return this.category;
    }

    public long getPantryId() {
        return this.pantryId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPantryId(long pantryId) {
        this.pantryId = pantryId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemDto)) return false;
        final ItemDto other = (ItemDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        final Object this$expirationDate = this.getExpirationDate();
        final Object other$expirationDate = other.getExpirationDate();
        if (this$expirationDate == null ? other$expirationDate != null : !this$expirationDate.equals(other$expirationDate))
            return false;
        final Object this$gtin = this.getGtin();
        final Object other$gtin = other.getGtin();
        if (this$gtin == null ? other$gtin != null : !this$gtin.equals(other$gtin)) return false;
        final Object this$brand = this.getBrand();
        final Object other$brand = other.getBrand();
        if (this$brand == null ? other$brand != null : !this$brand.equals(other$brand)) return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        if (this.getPantryId() != other.getPantryId()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ItemDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final long $quantity = this.getQuantity();
        result = result * PRIME + (int) ($quantity >>> 32 ^ $quantity);
        final Object $expirationDate = this.getExpirationDate();
        result = result * PRIME + ($expirationDate == null ? 43 : $expirationDate.hashCode());
        final Object $gtin = this.getGtin();
        result = result * PRIME + ($gtin == null ? 43 : $gtin.hashCode());
        final Object $brand = this.getBrand();
        result = result * PRIME + ($brand == null ? 43 : $brand.hashCode());
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final long $pantryId = this.getPantryId();
        result = result * PRIME + (int) ($pantryId >>> 32 ^ $pantryId);
        return result;
    }

    public String toString() {
        return "ItemDto(id=" + this.getId() + ", name=" + this.getName() + ", quantity=" + this.getQuantity() + ", expirationDate=" + this.getExpirationDate() + ", gtin=" + this.getGtin() + ", brand=" + this.getBrand() + ", image=" + this.getImage() + ", category=" + this.getCategory() + ", pantryId=" + this.getPantryId() + ")";
    }
}
