package com.guidopierri.pantrybe.dtos.responses;

public class ItemResponse {
        public String name;
        public String gtin;
        public String brand;
        public String image;
        public String category;

        public ItemResponse() {
        }

        public String getName() {
                return this.name;
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

        public void setName(String name) {
                this.name = name;
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

        public boolean equals(final Object o) {
                if (o == this) return true;
                if (!(o instanceof ItemResponse)) return false;
                final ItemResponse other = (ItemResponse) o;
                if (!other.canEqual((Object) this)) return false;
                final Object this$name = this.getName();
                final Object other$name = other.getName();
                if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
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
                if (this$category == null ? other$category != null : !this$category.equals(other$category))
                        return false;
                return true;
        }

        protected boolean canEqual(final Object other) {
                return other instanceof ItemResponse;
        }

        public int hashCode() {
                final int PRIME = 59;
                int result = 1;
                final Object $name = this.getName();
                result = result * PRIME + ($name == null ? 43 : $name.hashCode());
                final Object $gtin = this.getGtin();
                result = result * PRIME + ($gtin == null ? 43 : $gtin.hashCode());
                final Object $brand = this.getBrand();
                result = result * PRIME + ($brand == null ? 43 : $brand.hashCode());
                final Object $image = this.getImage();
                result = result * PRIME + ($image == null ? 43 : $image.hashCode());
                final Object $category = this.getCategory();
                result = result * PRIME + ($category == null ? 43 : $category.hashCode());
                return result;
        }

        public String toString() {
                return "ItemResponse(name=" + this.getName() + ", gtin=" + this.getGtin() + ", brand=" + this.getBrand() + ", image=" + this.getImage() + ", category=" + this.getCategory() + ")";
        }
}
