package com.example.learnCrud1.student_info.Service;

import com.example.learnCrud1.student_info.CustomException.StudentDoesNotExist;
import com.example.learnCrud1.student_info.CustomException.StudentNotSaved;
import com.example.learnCrud1.student_info.CustomException.UserDoesNotExist;
import com.example.learnCrud1.student_info.Repository.StudentRepository;
import com.example.learnCrud1.student_info.dto.StudentRequest;
import com.example.learnCrud1.student_info.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {


    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent() {
        return studentRepository.findAll();
    }


    public Student addStudent(StudentRequest request) {

        Student save=new Student();
        save.setName(request.getName());
        save.setAddress(request.getAddress());
        try {
            save = studentRepository.save(save);
        } catch (Exception e) {
            throw new StudentNotSaved("student doesn't exist");
        }
        return save;

    }

    public void updateStudent(Long id, Student student) {
        if(!studentRepository.existsById(id)) {
            throw new StudentDoesNotExist("Student doesn't exist");
        }
        Student saved = studentRepository.findById(id).get();

            if (student.getName() != null && !student.getName().isEmpty()) {
                saved.setName(student.getName());

            }
            if (student.getAddress() != null && !student.getAddress().isEmpty()) {
                saved.setAddress(student.getAddress());

            }
            studentRepository.save(saved);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new UserDoesNotExist("Id does not exist in db " + id);
        }
        studentRepository.deleteById(id);

    }


}
