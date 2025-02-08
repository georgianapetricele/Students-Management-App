package org.example.mpp_backend.service;

import lombok.AllArgsConstructor;
import org.example.mpp_backend.model.Student;
import org.example.mpp_backend.repository.StudentRepository;
import org.example.mpp_backend.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentRepository studentRepository;
    private UserRepository userRepository;

//    @PostConstruct
//    public void init() {
//        Faker faker = new Faker();
//
//        int cores = Runtime.getRuntime().availableProcessors();
//        System.out.println("Number of processor cores: " + cores);
//        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(cores);
//        for (int i = 1; i <= 100000; i++) {
//            executorService.execute(() -> {
//                User user = new User();
//                user.setUsername(faker.name().username());
//                user.setEmail(faker.internet().emailAddress());
//                user.setStudents(new ArrayList<>());
//
//                // Create students
//                for (int j = 1; j <= 10000; j++) {
//                    Student student = new Student();
//                    student.setName(faker.name(a).fullName());
//                    student.setAge(faker.number().numberBetween(18, 25));
//                    student.setMajor(faker.educator().course());
//                    student.setUser(user);
//                    user.add(student);
//                }
//                user.setNrStudents(10000);
//                // Save user and associated students
//                userRepository.save(user);
//            });
//        }
//    }


//    public List<Student> findAllStudents() {
//        return studentRepository.findAll();
//    }

    public List<Student> getAllStudentsForUserPaginated(Long id,int pageNumber){
        if(userRepository.findById(id).isPresent()){
            int pageSize = 50;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return studentRepository.findStudentByUser_UserId(id,pageable).getContent();
        } else {
            throw new IllegalArgumentException("User not found with ID " + id);
        }
    }

    public Student findStudentById(Long id) {
        if(studentRepository.findById(id).isPresent()) {
            return studentRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException("Student not found with ID " + id);
        }
    }

    public Student addStudent(Long userId,Student student) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found with ID " + userId);
        }
            student.setUser(userRepository.findById(userId).get());
            studentRepository.save(student);
            return student;
    }

    public Student updateStudent(Long id, Student student) {
        if(studentRepository.findById(id).isPresent()) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student not found with ID " + id);
        }
    }

    public void deleteStudent(Long id) {
        if(studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Student not found with ID " + id);
        }
    }
}
