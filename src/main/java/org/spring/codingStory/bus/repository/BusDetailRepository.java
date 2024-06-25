package org.spring.codingStory.bus.repository;

import org.spring.codingStory.bus.entity.BusDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusDetailRepository extends JpaRepository<BusDetailEntity,Long> {
  Optional<BusDetailEntity> findByBusRouteId(String busRouteId);

  Optional<BusDetailEntity> findByStationNo(String stationNo);

    Optional<BusDetailEntity> findAllByStationNm(String name);

    BusDetailEntity findByStationNm(String busStName);
}
