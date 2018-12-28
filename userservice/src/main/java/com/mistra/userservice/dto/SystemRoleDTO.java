package com.mistra.userservice.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: WangRui
 * @Date: 2018/12/28
 * Time: 16:35
 * Description:
 */
@Data
public class SystemRoleDTO {

    private String roleName;

    private List<SystemPermissionDTO> systemPermissionDTOList;
}
