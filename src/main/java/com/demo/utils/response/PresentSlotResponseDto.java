package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresentSlotResponseDto {
    String id_slot;
    String id_Building;
    boolean status_Slots;
}
