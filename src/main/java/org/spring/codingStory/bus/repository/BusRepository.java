package org.spring.codingStory.bus.repository;

import org.spring.codingStory.bus.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<BusEntity,Long> {
  Optional<BusEntity> findByBusRouteId(String busRouteId);
  Optional<BusEntity> findByBusRouteNm(String busRouteNm);

  Optional<BusEntity> findByBusRouteNmContains(String name);

//  Optional<BusEntity>  findByStationNm(int busStName);

  Optional<BusEntity> findByStStationNm(String buStname);
}
