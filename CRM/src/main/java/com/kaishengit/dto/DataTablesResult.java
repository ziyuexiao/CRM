package com.kaishengit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by lenovo on 2017/3/17.
 */
@Data
@AllArgsConstructor
public class DataTablesResult<T> {
    private String draw;
    private List<T> data;
    private Long recordsTotal;
    private Long recordsFiltered;



}
