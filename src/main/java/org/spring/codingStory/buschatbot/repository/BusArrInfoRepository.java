package org.spring.codingStory.buschatbot.repository;

import org.spring.codingStory.buschatbot.entity.BusArrInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusArrInfoRepository extends JpaRepository<BusArrInfoEntity,Long> {

  Optional<BusArrInfoEntity> findByBusRouteId(String busRouteId);

  Optional<BusArrInfoEntity> findAllByStNm(String stNm);

//  List<BusArrInfoEntity> findAllByStNm(String stNm);

  Optional<BusArrInfoEntity> findByStNm(String name);
}
