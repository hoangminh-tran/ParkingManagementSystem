package com.demo.service;

import com.demo.utils.response.PresentSlotResponseDto;

import java.util.List;

public interface PresentSlotOfEachBuilding {
    List<PresentSlotResponseDto> findAll(String id_Building);
}
