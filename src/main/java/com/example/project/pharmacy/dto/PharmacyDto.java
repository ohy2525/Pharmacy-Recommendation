package com.example.project.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDto { // redis 에서 사용하기 위한 dto

  private Long id;
  private String pharmacyName;
  private String pharmacyAddress;
  private double latitude;
  private double longitude;
}
