package org.spring.codingStory.buschatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_intention")
@Entity
public class BusIntention {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String name;
	
	@JoinColumn
	@ManyToOne
	private BusAnswer answer;

	@JoinColumn
	@ManyToOne
	private BusIntention upper;
}
