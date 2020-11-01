package com.flipkart.rest;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.User;
import com.flipkart.service.AdminService;
import com.flipkart.service.AdminServiceImpl;
import com.flipkart.service.CourseService;
import com.flipkart.service.CourseServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
public class AdminController {

    AdminService adminService = new AdminServiceImpl();

    // view courses
    @GET
    @Path("/view-courses")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> viewCourses()
    {
        CourseService courseService = new CourseServiceImpl();
        return courseService.listCourses();
    }

    //view all the users
    @GET
    @Path("/view-users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> viewUsers()
    {
        return adminService.viewUsers();
    }

    // assign a professor to the course
    @PUT
    @Path("/assign-professor/{courseId}/{professorId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void assignProfessor(@PathParam("professorId") int professorId, @PathParam("courseId") int courseId)
    {
        Professor professor = new Professor();
        professor.setProfessorId(professorId);
        adminService.assignProfessor(professor, courseId);
    }

    // add a course
    @POST
    @Path("/add-course")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(Course course) {
        System.out.println("Adding new course");
        System.out.println(course.getCourseId());
        System.out.println(course.getCourseName());
        adminService.addCourse(course);
        String result = "Added "  + course;
        return Response.status(201).entity(result).build();
    }

    // delete a course
    @DELETE
    @Path("/delete-course/{courseId}")
    public Response deleteCourse(@PathParam("courseId") int courseId) {
        Course course = new Course();
        course.setCourseId(courseId);
        adminService.deleteCourse(course);
        return Response.status(200).entity("Course " + " with course id " + courseId + " successfully deleted").build();
    }

    // delete a user
    @DELETE
    @Path("/delete-user/{userId}")
    public Response deleteUser(@PathParam("userId") int userId)
    {
        adminService.deleteUser(userId);
        return Response.status(200).entity("User with userId " + userId + "deleted successfully!").build();
    }
}
