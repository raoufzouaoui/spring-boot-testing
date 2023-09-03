package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository; // This mock will be used to simulate interactions with the real repository.
    // private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        // autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        //autoCloseable.close();
    }

    @Test
    void CangetAllStudents() {
        // When
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }

    @Test
        // @Disabled disable this test
    void CanAddStudent() {
        //given
        String email="jamila@gmail.com";
        Student student = new Student("jamila",email,Gender.FEMALE);

        //when
        underTest.addStudent(student);

        //then
        // ArgumentCaptor is used to capture the argument passed to the save method of the mocked studentRepository.
        // This allows you to inspect what was actually passed to the repository.
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture()); // ensures that the save method of studentRepository is called with the captured Student argument.

        Student captureStudent = studentArgumentCaptor.getValue(); // retrieves the captured Student object.

        assertThat(captureStudent).isEqualTo(student);
    }

    @Test
    // @Disabled disable this test
    void WillThrowWhenEmailIsTaken() {
        //given
        String email="jamila@gmail.com";
        Student student = new Student("jamila",email,Gender.FEMALE);
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);

        //when

        //then
        assertThatThrownBy(() -> underTest.addStudent(student))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining("Email " + student.getEmail() + " taken" );

        verify(studentRepository,never()).save(any());
    }

    @Test
    //  @Disabled disable this test
    void CanDeleteStudent() {
        //given
        Long studentId = 1L;

        //when
        when(studentRepository.existsById(studentId)).thenReturn(true);

        //then
        underTest.deleteStudent(studentId);

        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void WillThrowWhenDeleteNonExistingStudent() {
        //given
        Long studentId = 2L;

        //when
        when(studentRepository.existsById(studentId)).thenReturn(false);


        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exists" );

        verify(studentRepository, never()).deleteById(anyLong());
    }
}