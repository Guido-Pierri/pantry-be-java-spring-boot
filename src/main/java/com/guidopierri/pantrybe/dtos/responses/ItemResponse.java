package com.guidopierri.pantrybe.dtos.responses;

import lombok.Data;
@Data

public class ItemResponse {
        public String name;
        public String gtin;
        public String brand;
        public String image;
        public String category;
}
