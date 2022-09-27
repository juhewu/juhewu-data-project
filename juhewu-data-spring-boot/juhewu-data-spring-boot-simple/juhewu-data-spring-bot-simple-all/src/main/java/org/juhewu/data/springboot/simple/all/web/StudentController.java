package org.juhewu.data.springboot.simple.all.web;

import java.util.List;

import org.juhewu.data.context.RequestDataContextHolder;
import org.juhewu.data.springboot.simple.all.pojo.entity.Student;
import org.juhewu.data.springboot.simple.all.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * 学生 controller
 *
 * @author duanjw
 * @since 2022/09/27
 */
@RestController
@RequestMapping("student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * 查询学生列表
     * curl localhost:8080/student/list
     *
     * @return
     */
    @GetMapping("list")
    public List<Student> list() {
        //新增学生返回结果是否为 true
        Student student = new Student("", "张三三", "130406199901012222", "0310-5251111", "13312341234", "北京市朝阳区和平里1233号", "123123@126.com", "1234567891234567",
                "qazxsw", "123qwe", "test123test");
        studentService.insert(student);
        return studentService.list(null);
    }

    /**
     * 查看单个学生
     * curl localhost:8080/student/1
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Student findById(@PathVariable Integer id) {
        Student student = new Student("", "张三三", "130406199901012222", "0310-5251111", "13312341234", "北京市朝阳区和平里1233号", "123123@126.com", "1234567891234567",
                "qazxsw", "123qwe", "test123test");
        studentService.insert(student);
        return studentService.findById(id);
    }

    /**
     * 不脱敏
     * curl localhost:8080/student/skip-sensitive
     *
     * @return
     */
    @GetMapping("skip-sensitive")
    public List<Student> testSkip() {
        //新增学生返回结果是否为 true
        RequestDataContextHolder.skipSensitive();
        Student student = new Student("", "张三三", "130406199901012222", "0310-5251111", "13312341234", "北京市朝阳区和平里1233号", "123123@126.com", "1234567891234567",
                "qazxsw", "123qwe", "test123test");
        studentService.insert(student);
        return studentService.list(null);
    }
}
