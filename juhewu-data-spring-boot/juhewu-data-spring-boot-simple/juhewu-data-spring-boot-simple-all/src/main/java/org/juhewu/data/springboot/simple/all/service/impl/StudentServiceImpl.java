package org.juhewu.data.springboot.simple.all.service.impl;

import java.util.List;
import java.util.Map;

import org.juhewu.data.encrypt.IEncryptor;
import org.juhewu.data.springboot.simple.all.mapper.StudentMapper;
import org.juhewu.data.springboot.simple.all.pojo.entity.Student;
import org.juhewu.data.springboot.simple.all.pojo.vo.StudentScoreVO;
import org.juhewu.data.springboot.simple.all.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 学生service实现
 *
 * @author duanjw
 */
@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final IEncryptor encryptor;

    /**
     * 新增学生
     *
     * @param student 学生
     * @return true、false
     */
    @Override
    public boolean insert(Student student) {
        return studentMapper.insert(student);
    }

    /**
     * 批量新增学生
     *
     * @param studentList 学生list
     * @return true、false
     */
    @Override
    public boolean insertBatch(List<Student> studentList) {
        return studentMapper.insertBatch(studentList);
    }

    /**
     * 分页查询学生
     *
     * @param map 查询条件、分页参数
     * @return
     */
    @Override
    public PageInfo<Student> selectPage(Map map) {
        return new PageInfo<>(studentMapper.selectPage(map));
    }

    /**
     * 查询学生
     *
     * @param map 查询条件
     * @return 学生列表
     */
    @Override
    public List<Student> list(Map map) {
        log.debug("13312341234 加密后：{}，加密方法：{}",encryptor.encrypt("13312341234"), "encryptor.encrypt(\"13312341234\")");
        return studentMapper.list(map);
    }

    /**
     * 根据id查看学生
     *
     * @param id 学生id
     * @return 学生
     */
    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }

    /**
     * 根据id修改学生
     *
     * @param student 学生
     * @return true、false
     */
    @Override
    public boolean updateById(Student student) {
        return studentMapper.updateById(student);
    }

    /**
     * 根据id批量修改学生
     *
     * @param studentList
     * @return true、false
     */
    @Override
    public boolean updateBatchById(List<Student> studentList) {
        return studentMapper.updateBatchById(studentList);
    }

    /**
     * 根据id删除学生
     *
     * @param id
     * @return true、false
     */
    @Override
    public boolean deleteById(Integer id) {
        return studentMapper.deleteById(id);
    }

    /**
     * 根据id批量删除学生
     *
     * @param ids
     * @return true、false
     */
    @Override
    public boolean deleteBatchById(Integer[] ids) {
        return studentMapper.deleteBatchById(ids);
    }

    /**
     * 分页查询学生总分数
     *
     * @param map
     * @return
     */
    @Override
    public PageInfo<StudentScoreVO> selectStudentScorePage(Map map) {
        return new PageInfo<>(studentMapper.selectStudentScorePage(map));
    }

}
