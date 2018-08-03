package com.hu.myspring.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PathInfo 存储HTTP相关信息
 *
 * @author hujunfei
 * @date 2018-08-03 14:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathInfo {

    private String httpMethod;

    private String httpPath;
}
