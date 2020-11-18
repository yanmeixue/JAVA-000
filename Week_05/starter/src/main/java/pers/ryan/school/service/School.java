package pers.ryan.school.service;

import lombok.Data;
import pers.ryan.school.bean.Klass;
import pers.ryan.school.bean.Student;

@Data
public class School implements ISchool {

    private Student student;
    private Klass class1;

    @Override
    public void ding() {
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student);
    }

}
