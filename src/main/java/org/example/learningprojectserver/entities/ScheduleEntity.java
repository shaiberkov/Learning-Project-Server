package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "class_room_id", nullable = false)
    private ClassRoomEntity classRoom;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<LessonEntity> lessons=new ArrayList<>();

}
