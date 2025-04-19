package com.example.dgu_semi_erp_back.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationInfo {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
