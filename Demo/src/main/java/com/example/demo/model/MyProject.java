package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 项目实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProject implements Serializable {
    // 项目名称
    private String projectName;
    // 项目状态 0：未办结,1：已办结
    private Integer projectState;
    // 数据更新时间 数据按照更新时间倒排
    private String updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyProject myProject = (MyProject) o;
        return Objects.equals(projectName, myProject.projectName) && Objects.equals(projectState, myProject.projectState) && Objects.equals(updateTime, myProject.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, projectState, updateTime);
    }
}
