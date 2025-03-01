/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-03-22 23:01:07
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2025-01-01 15:23:24
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license.
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.service.rating;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.bytedesk.core.base.BaseRestService;
import com.bytedesk.core.thread.ThreadEntity;
import com.bytedesk.core.thread.ThreadRestService;
import com.bytedesk.core.uid.UidUtils;
import com.bytedesk.core.utils.ConvertUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingService extends BaseRestService<RatingEntity, RatingRequest, RatingResponse> {

    private final RatingRepository rateRepository;

    private final ModelMapper modelMapper;

    private final UidUtils uidUtils;

    private final ThreadRestService threadRestService;

    @Override
    public Page<RatingResponse> queryByOrg(RatingRequest request) {
        
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Specification<RatingEntity> specifications = RatingSpecification.search(request);
        Page<RatingEntity> page = rateRepository.findAll(specifications, pageable);

        return page.map(this::convertToResponse);
    }

    @Override
    public Page<RatingResponse> queryByUser(RatingRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'queryByUser'");
    }

    @Override
    public Optional<RatingEntity> findByUid(String uid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUid'");
    }

    @Override
    public RatingResponse create(RatingRequest request) {
        
        RatingEntity rate = modelMapper.map(request, RatingEntity.class);
        rate.setUid(uidUtils.getUid());
        // 
        Optional<ThreadEntity> threadOptional = threadRestService.findFirstByTopic(request.getThreadTopic());
        if (threadOptional.isPresent()) {
            rate.setThread(threadOptional.get());
        } else {
            throw new RuntimeException("找不到对应的主题");
        }

        RatingEntity savedRating = save(rate);
        if (savedRating == null) {
            throw new RuntimeException("save rate failed");
        }

        return convertToResponse(savedRating);

    }

    @Override
    public RatingResponse update(RatingRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public RatingEntity save(RatingEntity entity) {
        try {
            return rateRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteByUid(String uid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByUid'");
    }

    @Override
    public void delete(RatingRequest entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void handleOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e, RatingEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleOptimisticLockingFailureException'");
    }

    @Override
    public RatingResponse convertToResponse(RatingEntity entity) {
        RatingResponse ratingResponse = modelMapper.map(entity, RatingResponse.class);
        ratingResponse.setThread(ConvertUtils.convertToThreadResponse(entity.getThread()));
        return ratingResponse;
    }

}
