package com.guidopierri.pantrybe.dtos;

import java.util.ArrayList;
import java.util.List;

public class PantryDto {
    private long id;
    private long userId;
    private List<ItemDto> items = new ArrayList<>();

    public PantryDto(long id, long userId, List<ItemDto> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public PantryDto() {
    }

    public static PantryDtoBuilder builder() {
        return new PantryDtoBuilder();
    }

    public long getId() {
        return this.id;
    }

    public long getUserId() {
        return this.userId;
    }

    public List<ItemDto> getItems() {
        return this.items;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PantryDto)) return false;
        final PantryDto other = (PantryDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getUserId() != other.getUserId()) return false;
        final Object this$items = this.getItems();
        final Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PantryDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final long $userId = this.getUserId();
        result = result * PRIME + (int) ($userId >>> 32 ^ $userId);
        final Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "PantryDto(id=" + this.getId() + ", userId=" + this.getUserId() + ", items=" + this.getItems() + ")";
    }

    public static class PantryDtoBuilder {
        private long id;
        private long userId;
        private List<ItemDto> items;

        PantryDtoBuilder() {
        }

        public PantryDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public PantryDtoBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public PantryDtoBuilder items(List<ItemDto> items) {
            this.items = items;
            return this;
        }

        public PantryDto build() {
            return new PantryDto(this.id, this.userId, this.items);
        }

        public String toString() {
            return "PantryDto.PantryDtoBuilder(id=" + this.id + ", userId=" + this.userId + ", items=" + this.items + ")";
        }
    }
}
