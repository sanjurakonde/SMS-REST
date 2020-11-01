package com.flipkart.rest;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.service.CourseService;
import com.flipkart.service.CourseServiceImpl;
import com.flipkart.service.ProfessorService;
import com.flipkart.service.ProfessorServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/professor")
public class ProfessorController {

    ProfessorService professorService = new ProfessorServiceImpl();

    // view all the courses
    @GET
    @Path("/view-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> viewCourses()
    {
        CourseService courseService = new CourseServiceImpl();
        return courseService.listCourses();
    }

    // view student the professor teaches to
    @GET
    @Path("/view-students-taught/{professorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> viewStudents(@PathParam("professorId") int professorId)
    {
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        return professorService.viewStudents(professor);
    }

    // grade the students
    @POST
    @Path("grade-student/{professorId}/{studentId}/{courseId}/{grade}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gradeStudent(@PathParam("professorId") int professorId, @PathParam("studentId") int studentId,
                                 @PathParam("courseId") int courseId, @PathParam("grade") String grade) {
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        professorService.gradeStudent(professor, courseId, studentId, grade);
        String result = "Grades for student Id " + studentId + " with course " + courseId + "updated with grade " + grade;
        return Response.status(201).entity(result).build();
    }


}
