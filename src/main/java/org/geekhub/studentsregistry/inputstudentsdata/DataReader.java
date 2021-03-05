package org.geekhub.studentsregistry.inputstudentsdata;

import org.geekhub.studentsregistry.anotations.Dependency;

import java.util.List;

@Dependency
public interface DataReader {
    List<List<String>> createOriginalStudentsList(int studentsNumber);
}
