package me.gwma.java.basic.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * 参考文档：
 * <p>
 * http://stackoverflow.com/questions/1894377/understanding-the-workings-of-equals-and-hashcode-in-a-hashmap
 */
public class HashCodeAndEqualsTest {

    public static void main(String[] args) {
        Map<Student, String> students = new HashMap<>();
        Student s1 = new Student("Ma", 33);
        Student s2 = new Student("Ma", 33);
        Student s3 = new Student("Li", 32);
        students.put(s1, "1");
        students.put(s2, "w");
        students.put(s3, "3");
        System.out.println(students.size());
    }

}

class Student {

    String name;
    int    age;

    public Student(String name, int age){
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        // final int prime = 31;
        // int result = 1;
        // result = prime * result + age;
        // result = prime * result + ((name == null) ? 0 : name.hashCode());
        // return result;
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        if (age != other.age) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

}
