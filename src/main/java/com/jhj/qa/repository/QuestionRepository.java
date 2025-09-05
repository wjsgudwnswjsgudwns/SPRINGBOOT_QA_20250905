package com.jhj.qa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhj.qa.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
