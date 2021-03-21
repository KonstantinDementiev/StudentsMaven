package org.geekhub.studentsregistry.interfaces;

import java.io.Serializable;

public interface Grade extends Serializable {

    String asPrintVersion();

    Class<?> getGrade();

    Integer getValue();
}



