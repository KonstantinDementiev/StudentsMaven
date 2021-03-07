package org.geekhub.studentsregistry.domain.inputdata;

import org.geekhub.studentsregistry.domain.anotations.Dependency;

import java.util.List;

@Dependency
public interface DataReader {
    List<List<String>> createOriginalStudentsList(int studentsNumber);
}
