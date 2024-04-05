package com.example.project.direction.service

import com.example.project.api.dto.DocumentDto
import com.example.project.direction.entity.Direction
import com.example.project.pharmacy.dto.PharmacyDto
import com.example.project.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService = Mock()

    private DirectionService directionService = new DirectionService(pharmacySearchService)

    private List<PharmacyDto> pharmacyList

    def setup() {
        pharmacyList = new ArrayList()
        pharmacyList.addAll(
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName("약국1")
                        .pharmacyAddress("주소1")
                        .latitude(37.595)
                        .longitude(127.053)
                        .build(),
                PharmacyDto.builder()
                        .id(2L)
                        .pharmacyName("약국2")
                        .pharmacyAddress("주소2")
                        .latitude(37.5968)
                        .longitude(127.0399)
                        .build()
        )
    }
    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        def inputLatitude = 37.596
        def inputLongitude = 127.039

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList // stubbing

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetPharmacyName == "약국2"
    }

    def "buildDirectionList - 정해진 반경 10km 내에 검색이 되는지 확인"() {
        given:
        pharmacyList.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("약국3")
                        .pharmacyAddress("주소3")
                        .latitude(36.5968)
                        .longitude(127.0399)
                        .build()
        )

        def addressName = "서울 성북구 종암로10길"
        def inputLatitude = 37.596
        def inputLongitude = 127.039

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList // stubbing

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).distance < 10
    }
}
