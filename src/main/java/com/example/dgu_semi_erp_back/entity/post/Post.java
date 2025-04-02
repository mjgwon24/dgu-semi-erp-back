package com.example.dgu_semi_erp_back.entity.post;

import com.example.dgu_semi_erp_back.common.support.BaseEntity;
import com.example.dgu_semi_erp_back.entity.post.type.PostStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post extends BaseEntity {
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private int likeCount;

    private int dislikeCount;

    private Instant createdAt;

    private Instant updatedAt;
}
