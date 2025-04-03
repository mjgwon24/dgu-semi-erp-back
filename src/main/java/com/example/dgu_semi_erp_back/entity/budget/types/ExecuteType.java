package com.example.dgu_semi_erp_back.entity.budget.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExecuteType {
    OFFICE_SUPPLIES("사무용품 구매"),
    IT_EQUIPMENT("IT기기 구입"),
    MARKETING("마케팅 비용"),
    TRAVEL("출장비"),
    TRAINING("교육비"),
    MAINTENANCE("시설 유지보수"),
    ENTERTAINMENT("접대비"),
    RESEARCH("연구개발비"),
    OTHER("기타");

    private final String description;
}
