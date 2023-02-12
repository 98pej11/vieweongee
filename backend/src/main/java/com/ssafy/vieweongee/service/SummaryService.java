package com.ssafy.vieweongee.service;

import com.ssafy.vieweongee.dto.user.request.UserCreateRequest;

public interface SummaryService {
    void createSummary(UserCreateRequest user);
}
