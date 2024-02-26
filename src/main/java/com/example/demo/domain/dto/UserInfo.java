package com.example.demo.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Milad Tehrany
 * Date: 2/24/2024
 * Time: 11:04 AM
 */

@Data
@Builder
public class UserInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
}
