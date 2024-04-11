package com.example.project.pharmacy.service

import com.example.project.pharmacy.cache.PharmacyRedisTemplateService
import com.example.project.pharmacy.dto.PharmacyDto
import com.example.project.pharmacy.entity.Pharmacy
import org.assertj.core.util.Lists
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService

    private PharmacyRepositoryService pharmacyRepositoryService = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<Pharmacy> pharmacyList

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService, pharmacyRedisTemplateService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("약국1")
                        .pharmacyAddress("주소1")
                        .latitude(37.595)
                        .longitude(127.053)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("약국2")
                        .pharmacyAddress("주소2")
                        .latitude(37.5968)
                        .longitude(127.0399)
                        .build()
        )
    }

    def "레디스 장애시 DB를 이용하여 약국 데이터 조회"() {
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepositoryService.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
    }
}
