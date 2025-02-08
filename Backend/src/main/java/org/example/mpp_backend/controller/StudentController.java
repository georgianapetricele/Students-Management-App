package org.example.mpp_backend.controller;

import lombok.AllArgsConstructor;
import org.example.mpp_backend.model.Student;
import org.example.mpp_backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private StudentService studentService;

//    @GetMapping
//    public Collection<Student> getAllStudents() {
//        return studentService.findAllStudents();
//    }

    @GetMapping("/page/{id}/{currentPage}")
    public ResponseEntity<List<Student>> getAllStudentsForUserPaginated(@PathVariable Long id, @PathVariable int currentPage) {
        return ResponseEntity.ok(studentService.getAllStudentsForUserPaginated(id,currentPage));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.findStudentById(id);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addStudent(@PathVariable Long userId,@RequestBody Student student) {
        try {
            studentService.addStudent(userId,student);
            return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            studentService.updateStudent(id, student);
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
