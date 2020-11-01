package com.flipkart.rest;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.service.CourseService;
import com.flipkart.service.CourseServiceImpl;
import com.flipkart.service.StudentService;
import com.flipkart.service.StudentServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/student")
public class StudentController {

    StudentService studentService = new StudentServiceImpl();

    //view all the courses
    @GET
    @Path("/view-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> viewCourses()
    {
        CourseService courseService = new CourseServiceImpl();
        return courseService.listCourses();
    }

    //view registered courses
    @GET
    @Path("/view-registered-courses/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Course> getRegisteredCourses(@PathParam("studentId") int studentId) {
        Student student = new Student();
        student.setStudentId(studentId);
        return studentService.viewRegisteredCourses(student);
    }

    // Select a course to register
    @POST
    @Path("/select-course/{studentId}/{courseId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourses(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) throws CourseNotFoundException {
        Student student = new Student();
        student.setStudentId(studentId);
        studentService.selectCourse(student, courseId);
        String result = "Saved "  + courseId + studentId;
        return Response.status(201).entity(result).build();
    }

    // make payment
    @POST
    @Path("/make-payment/{studentId}/{paymentMethod}/{fees}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(@PathParam("studentId") int studentId, @PathParam("paymentMethod") int paymentMethod, @PathParam("fees") int fees) {
        Student student = new Student();
        student.setStudentId(studentId);
        studentService.makePayment(student, paymentMethod, fees);
        String result = "Made Payment for student with student Id " + studentId;
        return Response.status(201).entity(result).build();
    }

    // delete course from registered courses
    @DELETE
    @Path("/delete-course/{studentId}/{courseId}")
    public Response deleteCustomer(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) throws CourseNotFoundException {
        studentService.dropCourse(courseId, studentId);
        return Response.status(200).entity("The course " + courseId + " for student " + studentId + "deleted" ).build();
    }
}
