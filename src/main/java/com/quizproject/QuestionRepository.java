package com.quizproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<BaseQuestion, Long> {
    // You can add custom finders here if needed, e.g.:
    // List<BaseQuestion> findByPromptContaining(String text);
}