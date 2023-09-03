package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() { // it deletes all records from the database to provide a clean slate for the next test.
        underTest.deleteAll();
    }

    @Test
    void ItShouldCheckIfStudentExistsEmail() {
        //given
        String email="jamila@gmail.com";
        Student student = new Student("jamila",email,Gender.FEMALE);
        underTest.save(student);
        //when
        boolean exists = underTest.selectExistsEmail(email);
        //then
        assertThat(exists).isTrue();

    }

    @Test
    void ItShouldCheckIfStudentDoesNotExistsEmail() {
        //given
        String email="jamila@gmail.com";
        //when
        boolean exists = underTest.selectExistsEmail(email);
        //then
        assertThat(exists).isTrue();

    }
}