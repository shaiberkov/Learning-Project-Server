package org.example.learningprojectserver.entities;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class StudentProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // קשר OneToOne עם UserEntity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id") // השם בעמודת הדאטהבייס
    private StudentEntity student;


    // מאפיינים לשמירת התקדמות המשתמש
//    @ElementCollection
//    private Map<String, Integer> questionsAnsweredBySubTopic;

    // שמירת התקדמות לפי תתי נושא
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sub_topic_success_streak", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "success_streak")
    private Map<String, Integer> subTopicSuccessStreak;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sub_topic_incorrect_streak", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "incorrect_streak")
    private Map<String, Integer> subTopicIncorrectStreak;

    // שמירת רמות המיומנות לפי תתי נושא
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "skill_levels_by_sub_topic", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "skill_level")
    private Map<String, Integer> skillLevelsBySubTopic;

    public StudentProgressEntity(){
        this.subTopicSuccessStreak = new HashMap<>();
        this.skillLevelsBySubTopic = new HashMap<>();

    }
//    public UserProgressEntitiy(){
//
//    }
    // Getters and Setters

    public Map<String, Integer> getSubTopicIncorrectStreak() {
        return subTopicIncorrectStreak;
    }

    public void setSubTopicIncorrectStreak(Map<String, Integer> subTopicIncorrectStreak) {
        this.subTopicIncorrectStreak = subTopicIncorrectStreak;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }





//    public Map<String, Integer> getQuestionsAnsweredBySubTopic() {
//        return questionsAnsweredBySubTopic;
//    }

//    public void setQuestionsAnsweredBySubTopic(Map<String, Integer> questionsAnsweredBySubTopic) {
//        this.questionsAnsweredBySubTopic = questionsAnsweredBySubTopic;
//    }

    public Map<String, Integer> getSubTopicSuccessStreak() {
        return subTopicSuccessStreak;
    }

    public void setSubTopicSuccessStreak(Map<String, Integer> subTopicSuccessStreak) {
        this.subTopicSuccessStreak = subTopicSuccessStreak;
    }

    public Map<String, Integer> getSkillLevelsBySubTopic() {
        return skillLevelsBySubTopic;
    }

    public void setSkillLevelsBySubTopic(Map<String, Integer> skillLevelsBySubTopic) {
        this.skillLevelsBySubTopic = skillLevelsBySubTopic;
    }

    //@Override
//    public String toString() {
//        return "UserProgressEntitiy{" +
//                "id=" + id +
//
//                ", subTopicSuccessStreak=" + subTopicSuccessStreak +
//                ", skillLevelsBySubTopic=" + skillLevelsBySubTopic +
//                '}';
//    }

    @Override
    public String toString() {
        return "UserProgressEntity{" +
                "id=" + id +
                ", subTopicSuccessStreak=" + subTopicSuccessStreak +
                ", subTopicIncorrectStreak=" + subTopicIncorrectStreak +
                ", skillLevelsBySubTopic=" + skillLevelsBySubTopic +
                '}';
    }
}
