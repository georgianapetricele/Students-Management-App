package org.example.mpp_backend.service;//package org.example.a2.service;
//
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.github.javafaker.Faker;
//import org.example.a2.model.Student;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class SocketIOService {
//    private SocketIOServer server;
//    private final StudentService studentService;
//
//    public SocketIOService(StudentService studService) {
//        this.studentService = studService;
//    }
//
//    @PostConstruct
//    public void init() {
//        Configuration config = new Configuration();
//        config.setHostname("localhost");
//        config.setPort(9092); // port for socket.io
//
//        server = new SocketIOServer(config);
//        server.start();
//
//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(this::sendNewStudent, 0, 5, TimeUnit.SECONDS);
//    }
//
//    @PreDestroy
//    public void onDestroy() {
//        server.stop();
//    }
//
//    private void sendNewStudent() {
//        Faker faker = new Faker();
//        String name = faker.name().name();
//        String age = faker.number().numberBetween(18, 25) + "";
//        String major = faker.educator().course();
//
//        Student student = new Student();
//        student.setName(name);
//        student.setAge(age);
//        student.setMajor(major);
//        studentService.save(student);
//        server.getBroadcastOperations().sendEvent("newStudent", student);
//    }
//}