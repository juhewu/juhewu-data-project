package org.juhewu.data.springboot.simple.all.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.juhewu.data.springboot.simple.all.pojo.entity.Student;
import org.juhewu.data.springboot.simple.all.pojo.vo.StudentScoreVO;

/**
 *
 * 学生mapper
 *
 * @author duanjw
 */
@Mapper
public interface StudentMapper {

    /**
     * 新增学生
     * @param student 学生
     * @return true、false
     */
    boolean insert(Student student);

    /**
     * 批量新增学生
     * @param studentList 学生list
     * @return true、false
     */
    boolean insertBatch(List<Student> studentList);


    /**
     * 分页查询学生
     * @param map 查询条件、分页参数
     * @return
     */
    List<Student> selectPage(Map map);

    /**
     * 根据id查看学生
     * @param id 学生id
     * @return 学生
     */
    Student findById(Integer id);

    /**
     * 根据id修改学生
     * @param student 学生
     * @return true、false
     */
    boolean updateById(Student student);

    /**
     * 根据id批量修改学生
     * @param studentList
     * @return true、false
     */
    boolean updateBatchById(@Param("studentList") List<Student> studentList);

    /**
     * 根据id删除学生
     * @param id
     * @return true、false
     */
    boolean deleteById(Integer id);

    /**
     * 根据id批量删除学生
     * @param ids
     * @return true、false
     */
    boolean deleteBatchById(@Param("ids") Integer[] ids);

    /**
     * 分页查询学生总分数
     * @param map
     * @return
     */
    List<StudentScoreVO> selectStudentScorePage(Map map);


    List<Student> list(Map map);
}
