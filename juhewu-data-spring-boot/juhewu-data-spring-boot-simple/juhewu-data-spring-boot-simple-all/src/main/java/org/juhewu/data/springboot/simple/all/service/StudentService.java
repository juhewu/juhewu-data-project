package org.juhewu.data.springboot.simple.all.service;

import java.util.List;
import java.util.Map;

import org.juhewu.data.springboot.simple.all.pojo.entity.Student;
import org.juhewu.data.springboot.simple.all.pojo.vo.StudentScoreVO;

import com.github.pagehelper.PageInfo;

/**
 * 学生service接口
 *
 * @author duanjw
 */
public interface StudentService {

    /**
     * 新增学生
     *
     * @param student 学生
     * @return true、false
     */
    boolean insert(Student student);

    /**
     * 批量新增学生
     *
     * @param studentList 学生list
     * @return
     */
    boolean insertBatch(List<Student> studentList);

    /**
     * 分页查询学生
     *
     * @param map 查询条件、分页参数
     * @return
     */
    PageInfo<Student> selectPage(Map map);

    /**
     * 查询学生
     *
     * @param map 查询条件
     * @return 学生列表
     */
    List<Student> list(Map map);

    /**
     * 根据id查看学生
     *
     * @param id
     * @return Student
     */
    Student findById(Integer id);

    /**
     * 根据id修改学生
     *
     * @param student
     * @return true、false
     */
    boolean updateById(Student student);

    /**
     * 根据id批量修改学生
     *
     * @param studentList
     * @return true、false
     */
    boolean updateBatchById(List<Student> studentList);

    /**
     * 根据id删除学生
     *
     * @param id
     * @return true、false
     */
    boolean deleteById(Integer id);

    /**
     * 根据id批量删除学生
     *
     * @param ids
     * @return true、false
     */
    boolean deleteBatchById(Integer[] ids);

    /**
     * 分页查询学生总分数
     *
     * @param map
     * @return
     */
    PageInfo<StudentScoreVO> selectStudentScorePage(Map map);
}
