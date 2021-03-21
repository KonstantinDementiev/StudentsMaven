package org.geekhub.studentsregistry.web.repository;

import org.geekhub.studentsregistry.students.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends CrudRepository<Student, Integer> {
}
