/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-05-11 18:25:45
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2024-11-20 11:17:49
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license.
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.kbase.split;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.bytedesk.core.base.BaseRestService;
import com.bytedesk.core.uid.UidUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SplitRestService extends BaseRestService<SplitEntity, SplitRequest, SplitResponse> {

    private final SplitRepository splitRepository;

    private final ModelMapper modelMapper;

    private final UidUtils uidUtils;

    @Override
    public Page<SplitResponse> queryByOrg(SplitRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.Direction.ASC,
                "updatedAt");
        Specification<SplitEntity> spec = SplitSpecification.search(request);
        Page<SplitEntity> page = splitRepository.findAll(spec, pageable);
        return page.map(this::convertToResponse);
    }

    @Override
    public Page<SplitResponse> queryByUser(SplitRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'queryByUser'");
    }

    @Cacheable(value = "split", key = "#uid", unless="#result==null")
    @Override
    public Optional<SplitEntity> findByUid(String uid) {
        return splitRepository.findByUid(uid);
    }

    @Override
    public SplitResponse create(SplitRequest request) {
        
        SplitEntity entity = modelMapper.map(request, SplitEntity.class);
        entity.setUid(uidUtils.getUid());

        SplitEntity savedEntity = save(entity);
        if (savedEntity == null) {
            throw new RuntimeException("Create split failed");
        }
        return convertToResponse(savedEntity);
    }

    @Override
    public SplitResponse update(SplitRequest request) {
        Optional<SplitEntity> optional = splitRepository.findByUid(request.getUid());
        if (optional.isPresent()) {
            SplitEntity entity = optional.get();
            modelMapper.map(request, entity);
            //
            SplitEntity savedEntity = save(entity);
            if (savedEntity == null) {
                throw new RuntimeException("Update split failed");
            }
            return convertToResponse(savedEntity);
        }
        else {
            throw new RuntimeException("Split not found");
        }
    }

    @Override
    public SplitEntity save(SplitEntity entity) {
        try {
            return splitRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteByUid(String uid) {
        Optional<SplitEntity> optional = splitRepository.findByUid(uid);
        if (optional.isPresent()) {
            optional.get().setDeleted(true);
            save(optional.get());
            // splitRepository.delete(optional.get());
        }
        else {
            throw new RuntimeException("Split not found");
        }
    }

    @Override
    public void delete(SplitRequest request) {
        deleteByUid(request.getUid());
    }

    @Override
    public void handleOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e, SplitEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleOptimisticLockingFailureException'");
    }

    @Override
    public SplitResponse convertToResponse(SplitEntity entity) {
        return modelMapper.map(entity, SplitResponse.class);
    }
    
}
