package org.geekhub.studentsregistry.students;

import java.util.List;

public class StudentsDataForWeb {

    private List<String> inputData;

    public StudentsDataForWeb() {
    }

    public StudentsDataForWeb(List<String> inputData) {
        this.inputData = inputData;
    }

    public List<String> getInputData() {
        return inputData;
    }

    public void setInputData(List<String> inputData) {
        this.inputData = inputData;
    }

    public void addData(String data) {
        this.inputData.add(data);
    }
}


